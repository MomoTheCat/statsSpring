package pl.momothecat.stats;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatsSpringApplicationTests {

    @Value("${minutesto.schedule}")
    private int minutesToSchedule = 10;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private CollectData collectData;

    @Test
	public void contextLoads() {

	}
}
