package utils;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;

/**
 * Created by szymon on 05.03.2017.
 */
public class TestUtils {
    //Assert means must be.
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
