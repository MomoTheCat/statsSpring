package pl.momothecat.stats;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by szymon on 04.03.2017.
 */

@RestController
public class ApplicationController {

    private static final String template = "Hello, %s for the %d%n time!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return  String.format(template, name, counter.incrementAndGet());
    }

    /*
    TODO: Statistics
    /statistics/mostpopularstation
    /statistics/mostdrivenbike
     */
}
