package pl.momothecat.stats.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.TestUtils;

import java.util.Arrays;
import java.util.List;


/**
 * Created by szymon on 05.03.2017.
 */
public class SimpleStationTest {

    SimpleStation station;
    List<SimpleExtra> extras;

    @Before
    public void setUp() {

        extras = Arrays.asList(SimpleExtra.newBuilder().build());

        station = SimpleStation.newBuilder()
                .setLongitude(19.54)
                .setLatitude(51.2)
                .setExtras(extras)
                .setIdNetwork("1234")
                .setMongoId("MongoId1234")
                .setName("name")
                .build();
    }

    @Test
    public void setVariables() {

        TestUtils.assertNotEmptyValue(station);

        Assert.assertNotNull(station);
        Assert.assertEquals(19.54, station.getLongitude(), 0);
        Assert.assertEquals(51.2, station.getLatitude(), 0);
        Assert.assertEquals(extras, station.getExtras());
        Assert.assertEquals("1234", station.getIdNetwork());
        Assert.assertEquals("MongoId1234", station.getMongoId());
        Assert.assertEquals("name", station.getName());
    }

    @Test
    public void copyObjects() {
        SimpleStation copy = SimpleStation.newBuilder()
                .copy(station)
                .build();

        Assert.assertEquals(station, copy);
    }

}