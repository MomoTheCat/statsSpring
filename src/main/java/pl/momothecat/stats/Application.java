package pl.momothecat.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Value("${minutesto.schedule}")
    private int minutesToSchedule = 10;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private CollectData collectData;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        collectData.schedule(minutesToSchedule);
    }
}
