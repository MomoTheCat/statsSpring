package pl.momothecat.stats;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import pl.momothecat.stats.model.SimpleExtra;
import pl.momothecat.stats.model.SimpleStation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by szymon on 12.03.2017.
 */
public class ApplicationControllerTest {

    @InjectMocks
    ApplicationController applicationController;

    SimpleStation ss1;
    List<SimpleStation> simpleStations;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

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
    public void sortByFreeBikes() {
        assertTrue (simpleStations.get(0).getExtras().get(0).getFree_bikes() == 10);

        applicationController.sortByFreeBikes(simpleStations);
        assertTrue(simpleStations.get(0).getExtras().get(0).getFree_bikes() == 11);
        assertTrue(simpleStations.get(1).getExtras().get(0).getFree_bikes() == 10);
    }

    @Test(expected = NullPointerException.class)
    public void sortByFreeBikes_EmptyList() {
        List<SimpleStation> simpleStations2 =  new ArrayList<>();
        applicationController.sortByFreeBikes(simpleStations2);
    }

    @Test(expected = NullPointerException.class)
    public void sortByFreeBikes_Null() {
        List<SimpleStation> simpleStations2 =  null;
        applicationController.sortByFreeBikes(simpleStations2);
    }

}