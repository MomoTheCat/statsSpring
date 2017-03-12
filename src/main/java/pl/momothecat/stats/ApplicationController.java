package pl.momothecat.stats;

import edu.emory.mathcs.backport.java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.momothecat.stats.dao.StationsRepository;
import pl.momothecat.stats.model.SimpleExtra;
import pl.momothecat.stats.model.SimpleStation;
import pl.momothecat.stats.utils.exceptions.InvalidListException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static pl.momothecat.stats.utils.CustomUtils.checkIfListNotNull;

/**
 * Created by szymon on 04.03.2017.
 */

@Controller
public class ApplicationController {

    static final Logger logger = LoggerFactory.getLogger(CollectData.class);

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private StationsRepository repository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("times", counter.getAndIncrement());
        return "greeting";
    }

    @RequestMapping("/get")
    public String getAll(Model model) {
        List<SimpleStation> stations = repository.findAll();
        logger.info(stations.toString());
        model.addAttribute("stations", stations);
        return "stations";
    }

    @RequestMapping("/get/{idNetwork}")
    public String getSingle(@PathVariable("idNetwork") String idNetwork, Model model) {
        Optional<SimpleStation> station = Optional.of(repository.findByIdNetwork(idNetwork));
        logger.info(station.toString());
        if (station.isPresent())
            model.addAttribute("station", station.get());
        return "station";
    }

    @RequestMapping("/getMaxBike")
    public String getStationWithBiggestNumberOfBikes(Model model) {
        List<SimpleStation> simpleStation = repository.findAll().stream()
                .map(station -> SimpleStation.newBuilder()
                        .copy(station)
                        .setExtras(Arrays.asList(station.getExtras().get(station.getExtras().size() - 1)))
                        .build())
                .collect(Collectors.toList());


        sortByFreeBikes(simpleStation);

        model.addAttribute("stations", simpleStation);
        return "stations";
    }

    protected void sortByFreeBikes(List<SimpleStation> simpleStation) {
        try {
            checkIfListNotNull(simpleStation);
        } catch (InvalidListException e) {
            logger.info(e.toString());
            e.printStackTrace();
            logger.error("Process interupted, exception occured.");
            return;
        }

        Collections.sort(simpleStation, new Comparator<SimpleStation>() {
            @Override
            public int compare(SimpleStation o1, SimpleStation o2) {
                return o1.getExtras().get(0).getFree_bikes() - o2.getExtras().get(0).getFree_bikes();
            }
        });
        Collections.reverse(simpleStation);
    }

}
