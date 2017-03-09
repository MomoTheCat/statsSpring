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

import javax.inject.Singleton;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by szymon on 04.03.2017.
 */

@Component
@Singleton
public class CollectData {

    static final Logger logger = LoggerFactory.getLogger(CollectData.class);

    @Value("${bike.uri}")
    private String bikeUri;

    private static ObjectMapper mapper;

    private final AtomicLong counter = new AtomicLong();

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

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setPrettyPrint(false);
        messageConverter.setObjectMapper(mapper);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName()
                .equals(MappingJackson2HttpMessageConverter.class.getName()));
        restTemplate.getMessageConverters().add(messageConverter);

        ResponseEntity<Company> response = restTemplate.getForEntity(urlToRead, Company.class);
        logger.info("Request status: " + response.getStatusCodeValue());

        List<Company.StationsBean> stations = getStationsBeen(response);

        return stations;
    }

    private List<Company.StationsBean> getStationsBeen(ResponseEntity<Company> response) {
        Company company = response.getBody();
        checkIfValueNotNull(company);

        List<Company.StationsBean> stations = company.getStations();
        checkIfListNotNull(stations);
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

    private <T> void checkIfValueNotNull(T element) {
        if (element == null)
            throw new NullPointerException("Element must not be null");
    }

    private <T> void checkIfListNotNull(List<T> elements) {
        if (elements == null || elements.isEmpty())
            throw new NullPointerException("List must not be null");
    }

}
