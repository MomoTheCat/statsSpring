package pl.momothecat.stats;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;
import pl.momothecat.stats.dao.StationsRepository;
import pl.momothecat.stats.model.SimpleExtra;
import pl.momothecat.stats.model.SimpleStation;
import utils.TestUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


/**
 * Created by Kacper on 2017-03-13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class,EmbeddedMongoAutoConfiguration.class})
@WebAppConfiguration
@TestPropertySource(locations="classpath:test.properties")
public class ApplicationControllerTestIT {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StationsRepository stationsRepository;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.initMocks(this);
        stationsRepository.deleteAll();
        stationsRepository.save(TestUtils.prepareSimpleStation("1",15,25,10));
        stationsRepository.save(TestUtils.prepareSimpleStation("2",10,35,25));
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
    public void getAll() throws Exception {
        mockMvc.perform(get("/get"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Test Station 1")))
                .andExpect(content().string(containsString("Test Station 2")));
    }

    @Test
    public void getSingle() throws Exception {
        mockMvc.perform(get("/get/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Name: Test Station 1")))
                .andExpect(content().string(containsString("Free bikes: 10")))
                .andExpect(content().string(containsString("Free slots: 15")));
    }

    @Test
    public void getStationWithBiggestNumberOfBikes() throws Exception {
        mockMvc.perform(get("/getMaxBike"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Test Station 2")));
    }

}