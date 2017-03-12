package pl.momothecat.stats;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.momothecat.stats.dao.StationsRepository;

import static org.junit.Assert.*;

/**
 * Created by Kacper on 2017-03-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
public class CollectDataTest {

    @Autowired
    CollectData collectData;

    @Before
    public void setUp(){

    }

    @Test
    public void schedule() {
        collectData.schedule(1);

    }

    @Test
    public void shouldGetRequest() {
        //given

        //when
        //then
    }

    @Autowired
    private StationsRepository stationsRepository;




}