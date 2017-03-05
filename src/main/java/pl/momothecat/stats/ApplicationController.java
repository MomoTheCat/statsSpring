package pl.momothecat.stats;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by szymon on 04.03.2017.
 */

@Controller
public class ApplicationController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("times", counter.getAndIncrement());
        return "greeting";
    }

    /*
    TODO: Statistics
    /statistics/mostpopularstation
    /statistics/mostdrivenbike
     */
}
