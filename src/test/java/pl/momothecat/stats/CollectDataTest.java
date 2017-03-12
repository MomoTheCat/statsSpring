package pl.momothecat.stats;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.momothecat.stats.model.Company;
import pl.momothecat.stats.model.SimpleExtra;
import pl.momothecat.stats.model.SimpleStation;

import java.util.List;

/**
 * Created by szymon on 12.03.2017.
 */
public class CollectDataTest {

    @InjectMocks
    CollectData collectData;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void setupCustomMapperToRestTemplate() throws Exception {
        RestTemplate restTemplate = collectData.setupCustomMapperToRestTemplate();

        List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();

        for (HttpMessageConverter<?> httpMC : httpMessageConverters){
            System.out.println(httpMC.toString());
            if(MappingJackson2HttpMessageConverter.class.isInstance(httpMC)   ) {
                MappingJackson2HttpMessageConverter mp = (MappingJackson2HttpMessageConverter) httpMC;
                Assert.assertTrue(mp.getObjectMapper().getDeserializationConfig().isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY));
                Assert.assertFalse(mp.getObjectMapper().getDeserializationConfig().isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
                Assert.assertTrue(mp.getObjectMapper().getDeserializationConfig().isEnabled(DeserializationFeature.UNWRAP_ROOT_VALUE));
            }
        }
    }

    @Test
    public void createExtrasTest(){

        //Create constructor or setters for Company.StationsBean
        //Mock Date

       // collectData.createExtras()
    }


}