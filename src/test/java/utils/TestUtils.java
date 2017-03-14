package utils;

import pl.momothecat.stats.model.SimpleExtra;
import pl.momothecat.stats.model.SimpleStation;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;

/**
 * Created by szymon on 05.03.2017.
 */
public class TestUtils {
    private static Random generator = new Random();

    public static SimpleStation prepareSimpleStation(String networkId,int freeSlots,int allSlots, int bikesCount){
        int tab[] = new int[bikesCount];
        for (int i = 0; i<bikesCount; i++) {
            tab[i]=generator.nextInt();
        }

        SimpleExtra se = SimpleExtra.newBuilder()
                .setDate(new Date())
                .setSlots(allSlots)
                .setFree_bikes(bikesCount)
                .setEmpty_slots(freeSlots)
                .setUid(generator.nextInt())
                .setBike_uids(tab)
                .setNumber(generator.nextInt())
                .build();

        SimpleStation st = SimpleStation.newBuilder()
                .setIdNetwork(networkId)
                .setName("Test Station " + networkId)
                .setExtras(Arrays.asList(se))
                .setLatitude(50.0)
                .setLongitude(40.0)
                .build();
        return st;
    }

    public static void assertNotEmptyValue(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                assertNotNull(value);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(TestUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
