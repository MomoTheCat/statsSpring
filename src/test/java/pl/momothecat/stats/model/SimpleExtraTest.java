package pl.momothecat.stats.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.TestUtils;

import java.util.Date;

/**
 * Created by szymon on 05.03.2017.
 */
public class SimpleExtraTest {

    SimpleExtra extras;
    int[] bike_uids = {23, 43};
    Date date;

    @Before
    public void setUp() throws Exception {

        date = new Date();

        extras = SimpleExtra.newBuilder()
                .setNumber(123)
                .setUid(1234)
                .setBike_uids(bike_uids)
                .setDate(date)
                .setSlots(20)
                .setEmpty_slots(3)
                .setFree_bikes(12)
                .build();
    }


    @Test
    public void setVariables() {
        TestUtils.assertNotEmptyValue(extras);

        Assert.assertNotNull(extras);
        Assert.assertEquals(123, extras.getNumber());
        Assert.assertEquals(1234, extras.getUid());
        Assert.assertEquals(bike_uids, extras.getBike_uids());
        Assert.assertEquals(date, extras.getDate());
        Assert.assertEquals(20, extras.getSlots());
        Assert.assertEquals(3, extras.getEmpty_slots());
        Assert.assertEquals(12, extras.getFree_bikes());
    }

    @Test
    public void copyObjects() {
        SimpleExtra copy = SimpleExtra.newBuilder()
                .copy(extras)
                .build();

        Assert.assertEquals(extras, copy);
    }

}