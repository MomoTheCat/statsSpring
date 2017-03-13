package pl.momothecat.stats;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatsSpringApplicationTestsIT {

    @Value("${minutesto.schedule}")
    private int minutesToSchedule = 10;


    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    CollectData collectData;

    @Test
	public void contextLoads() {

	}
}
