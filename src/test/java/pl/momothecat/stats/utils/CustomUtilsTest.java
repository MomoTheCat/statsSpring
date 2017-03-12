package pl.momothecat.stats.utils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.momothecat.stats.model.SimpleExtra;
import pl.momothecat.stats.model.SimpleStation;
import pl.momothecat.stats.utils.exceptions.InvalidListException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by szymon on 12.03.2017.
 */
public class CustomUtilsTest {

    SimpleExtra simpleExtra;
    SimpleStation simpleStation;

    List<SimpleExtra> simpleExtras;

    @Autowired
    CustomUtils customUtils;

    @Before
    public void setUp() {
        simpleExtra = SimpleExtra.newBuilder()
                .setFree_bikes(10)
                .build();

        simpleStation = SimpleStation.newBuilder()
                .setExtras(Arrays.asList(simpleExtra))
                .setName("Name123")
                .build();

        simpleExtras = new ArrayList<>();
        simpleExtras.add(simpleExtra);
    }

    @Test
    public void checkIfValueNotNull() throws Exception {
        customUtils.checkIfValueNotNull(simpleStation);
        customUtils.checkIfValueNotNull(simpleExtra);
    }

    @Test(expected = NullPointerException.class)
    public void checkIfValueNotNull_Null() throws Exception {
        SimpleExtra simpleExtra2 = null;
        customUtils.checkIfValueNotNull(simpleExtra2);
    }


    @Test
    public void checkIfListNotNull() throws Exception {
        customUtils.checkIfListNotNull(simpleExtras);
    }

    @Test(expected = InvalidListException.class)
    public void checkIfListNotNull_Empty() throws Exception {
        List<SimpleExtra> simpleExtras2 = new ArrayList<>();
        customUtils.checkIfListNotNull(simpleExtras2);
    }

    @Test(expected = InvalidListException.class)
    public void checkIfListNotNull_Null() throws Exception {
        List<SimpleExtra> simpleExtras2 = null;
        customUtils.checkIfListNotNull(simpleExtras2);
    }

}