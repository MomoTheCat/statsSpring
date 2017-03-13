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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by szymon on 12.03.2017.
 */
public class CustomUtilsTest {

    SimpleExtra simpleExtra;
    SimpleStation simpleStation;

    List<SimpleExtra> simpleExtras;

    SimpleStation ss1;
    List<SimpleStation> simpleStations;


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

        initStations();
    }

    private void initStations() {
        simpleStations = new ArrayList<>();

        ss1 = SimpleStation.newBuilder()
                .setExtras(Arrays.asList(SimpleExtra.newBuilder()
                        .setFree_bikes(10).build()))
                .build();
        simpleStations.add(ss1);

        simpleStations.add(SimpleStation.newBuilder()
                .setExtras(Arrays.asList(SimpleExtra.newBuilder()
                        .setFree_bikes(11).build()))
                .build());

        simpleStations.add(SimpleStation.newBuilder()
                .setExtras(Arrays.asList(SimpleExtra.newBuilder()
                        .setFree_bikes(8).build()))
                .build());

        simpleStations.add(SimpleStation.newBuilder()
                .setExtras(Arrays.asList(SimpleExtra.newBuilder()
                        .setFree_bikes(9).build()))
                .build());
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

    @Test
    public void sortByFreeBikes() throws InvalidListException {
        assertTrue (simpleStations.get(0).getExtras().get(0).getFree_bikes() == 10);

        customUtils.sortByFreeBikes(simpleStations);
        assertTrue(simpleStations.get(0).getExtras().get(0).getFree_bikes() == 11);
        assertTrue(simpleStations.get(1).getExtras().get(0).getFree_bikes() == 10);
    }

    @Test
    public void sortByFreeBikes_EmptyList() {
        List<SimpleStation> simpleStations2 =  new ArrayList<>();
        try {
            customUtils.sortByFreeBikes(simpleStations2);
            fail("Expected an InvalidListException to be thrown");
        }catch (InvalidListException e){
            assertThat(e.toString(),is("\nList is Empty"));
        }

    }

    @Test
    public void sortByFreeBikes_Null() {
        List<SimpleStation> simpleStations2 =  null;
        try {
            customUtils.sortByFreeBikes(simpleStations2);
        }catch (InvalidListException e){
            assertThat(e.toString(), is("\nList is null"));
        }
    }

}