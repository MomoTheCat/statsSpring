package pl.momothecat.stats;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.momothecat.stats.model.Company;
import pl.momothecat.stats.model.SimpleExtra;
import pl.momothecat.stats.model.SimpleStation;

import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

        getRequest(bikeUri, true);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        final AtomicLong counter = new AtomicLong();
        scheduledExecutorService.scheduleAtFixedRate(() -> {

            System.out.println("hi there at: " + new java.util.Date()
                    + ", updated : " + counter.incrementAndGet() + " times.");

            getRequest(bikeUri, false);
        }, 0, minutes, TimeUnit.MINUTES);
    }

    public void getRequest(String urlToRead, Boolean firstTime) {
        try {
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(1000);

            parseAndSaveToDatabase(conn, firstTime);

        } catch (MalformedURLException e) {
            logger.debug(e.toString());
        } catch (IOException e) {
            logger.debug(e.toString());
        }
    }

    private void parseAndSaveToDatabase(HttpURLConnection conn, Boolean firstTime) throws IOException {
        Company company = readData(conn);
        List<Company.StationsBean> stations = company.getStations();
        if (firstTime) {
            saveToDatabase(stations);
        } else {
            updateStationsData(stations);
        }
    }

    private Company readData(HttpURLConnection conn) throws IOException {

        StringBuilder result = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        Company company = mapper.readValue(result.toString(), Company.class);
        checkIfValueNotNull(company);

        return mapper.readValue(result.toString(), Company.class);
    }

    private void saveToDatabase(List<Company.StationsBean> stations) {
        checkIfListNotNull(stations);

        for (Company.StationsBean station : stations) {

            SimpleStation st = SimpleStation.newBuilder()
                    .setIdNetwork(station.getId())
                    .setName(station.getName())
                    .setExtras(Arrays.asList(createExtras(station)))
                    .setLatitude(station.getLatitude())
                    .setLongitude(station.getLongitude())
                    .build();

            if (!repositoryTemplate.ifExistsNetworkId(station.getId()))
                repository.save(st);
        }
        logger.info("Data saved");
    }

    private void updateStationsData(List<Company.StationsBean> stations) {
        logger.info("update at: " + new java.util.Date());
        checkIfListNotNull(stations);

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
        if (elements == null || elements.size() == 0)
            throw new NullPointerException("List must not be null");
    }

}
