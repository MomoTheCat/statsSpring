package pl.momothecat.stats;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.momothecat.stats.dao.StationsRepository;
import pl.momothecat.stats.dao.StationsRepositoryTemplate;
import pl.momothecat.stats.model.Company;
import pl.momothecat.stats.model.SimpleExtra;
import pl.momothecat.stats.model.SimpleStation;
import pl.momothecat.stats.utils.exceptions.InvalidElementException;
import pl.momothecat.stats.utils.exceptions.InvalidListException;

import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static pl.momothecat.stats.utils.CustomUtils.checkIfListNotNull;
import static pl.momothecat.stats.utils.CustomUtils.checkIfValueNotNull;

/**
 * Created by szymon on 04.03.2017.
 */

@Component
@Singleton
public class CollectData {

    private static final Logger logger = LoggerFactory.getLogger(CollectData.class);
    private final AtomicLong counter = new AtomicLong();
    private static ObjectMapper mapper;

    @Value("${bike.uri}")
    private String bikeUri;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    @Autowired
    private StationsRepository repository;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private StationsRepositoryTemplate repositoryTemplate;

    public void schedule(int minutes) {
        saveToDatabase(getRequest(bikeUri));

        Executors.newScheduledThreadPool(2).scheduleAtFixedRate(() -> {
            updateStationsData(getRequest(bikeUri));
        }, 0, minutes, TimeUnit.MINUTES);
    }

    public List<Company.StationsBean> getRequest(String urlToRead) {
        RestTemplate restTemplate = setupCustomMapperToRestTemplate();
        ResponseEntity<Company> response = restTemplate.getForEntity(urlToRead, Company.class);
        logger.info("Request status: " + response.getStatusCodeValue());

        return getStationsBeen(response);
    }

    private RestTemplate setupCustomMapperToRestTemplate() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setPrettyPrint(false);
        messageConverter.setObjectMapper(mapper);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName()
                .equals(MappingJackson2HttpMessageConverter.class.getName()));
        restTemplate.getMessageConverters().add(messageConverter);
        return restTemplate;
    }

    private List<Company.StationsBean> getStationsBeen(ResponseEntity<Company> response) {
        Company company = response.getBody();
        List<Company.StationsBean> stations = company.getStations();

        try {
            checkIfValueNotNull(company);
            checkIfListNotNull(stations);
        } catch (Exception e) {
            logger.info(e.toString());
            e.printStackTrace();
            logger.error("Process interupted, exception occured. Returning empty list");
            return  Collections.emptyList();
        }

        return stations;
    }

    private void saveToDatabase(List<Company.StationsBean> stations) {
        stations.stream()
                .filter(Objects::nonNull)
                .filter(station -> !repositoryTemplate.ifExistsNetworkId(station.getId()))
                .forEach(station -> {
                    SimpleStation st = SimpleStation.newBuilder()
                            .setIdNetwork(station.getId())
                            .setName(station.getName())
                            .setExtras(Arrays.asList(createExtras(station)))
                            .setLatitude(station.getLatitude())
                            .setLongitude(station.getLongitude())
                            .build();

                    repository.save(st);
                });

        logger.info("Data saved");
    }

    private void updateStationsData(List<Company.StationsBean> stations) {
        logger.info("At: " + new java.util.Date()
                + ", updating DB for the " + counter.incrementAndGet() + " times.");

        for (Company.StationsBean station : stations)
            repositoryTemplate.pushMethod(station.getId(), createExtras(station));
    }


    private SimpleExtra createExtras(Company.StationsBean station) {
        return SimpleExtra.newBuilder()
                .setDate(new Date())
                .setSlots(station.getExtra().getSlots())
                .setFree_bikes(station.getFree_bikes())
                .setEmpty_slots(station.getEmpty_slots())
                .setUid(station.getExtra().getUid())
                .setBike_uids(station.getExtra().getBike_uids())
                .setNumber(station.getExtra().getNumber())
                .build();
    }

}
