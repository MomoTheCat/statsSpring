package pl.momothecat.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.momothecat.stats.dao.StationsRepository;
import pl.momothecat.stats.model.SimpleStation;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

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

}
