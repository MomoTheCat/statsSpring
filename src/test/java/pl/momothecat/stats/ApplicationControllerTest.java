package pl.momothecat.stats;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRule;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import pl.momothecat.stats.dao.StationsRepository;
import pl.momothecat.stats.model.SimpleExtra;
import pl.momothecat.stats.model.SimpleStation;
import utils.TestUtils;

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


/**
 * Created by Kacper on 2017-03-13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class,EmbeddedMongoAutoConfiguration.class})
@WebAppConfiguration
@TestPropertySource(locations="classpath:test.properties")
public class ApplicationControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private StationsRepository stationsRepositoryMock;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    SimpleStation simpleStation1;
    SimpleStation simpleStation2;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.initMocks(this);
        //Zamockować zwrotke StationsRepository
        simpleStation1= TestUtils.prepareSimpleStation("1",15,25,10);
        simpleStation2=TestUtils.prepareSimpleStation("2",10,35,25);

    }

    @Test
    public void greeting() throws Exception {
        mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Hello, World")))
                .andExpect(content().string(containsString("0 times!")));

        for (int i=1; i<5; i++){
            mockMvc.perform(get("/greeting"))
                    .andExpect(content().string(containsString(i + " times!")));;
        }

    }

    @Test
    @Ignore
    public void getAll() throws Exception {
        when(stationsRepositoryMock.findByIdNetwork("1")).thenReturn(simpleStation1);

        mockMvc.perform(get("/get"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Test Station 1")))
                .andExpect(content().string(containsString("Test Station 2")));
    }

    @Test
    @Ignore
    public void getSingle() throws Exception {
        when(stationsRepositoryMock.findByIdNetwork("1")).thenReturn(simpleStation1);

        mockMvc.perform(get("/get/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Name: Test Station 1")))
                .andExpect(content().string(containsString("Free bikes: 15")))
                .andExpect(content().string(containsString("Free slots: 5")));
    }

    @Test
    @Ignore
    public void getStationWithBiggestNumberOfBikes() throws Exception {
        mockMvc.perform(get("/getMaxBike"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Test Station 2")));
    }

}