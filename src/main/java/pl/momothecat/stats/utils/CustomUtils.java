package pl.momothecat.stats.utils;

import edu.emory.mathcs.backport.java.util.Collections;
import pl.momothecat.stats.model.SimpleStation;
import pl.momothecat.stats.utils.exceptions.InvalidElementException;
import pl.momothecat.stats.utils.exceptions.InvalidListException;

import java.util.Comparator;
import java.util.List;

/**
 * Created by szymon on 12.03.2017.
 */
public class CustomUtils {

    public static <T> void checkIfValueNotNull(T element) throws InvalidElementException {
        if (element == null)
            throw new InvalidElementException(element,"Element must not be null");
    }

    public static <T> void checkIfListNotNull(List<T> elements) throws  InvalidListException{
        if (elements == null || elements.isEmpty())
            throw new InvalidListException(elements,"Wrong list");
    }

    public static void sortByFreeBikes(List<SimpleStation> simpleStation) throws InvalidListException {
        checkIfListNotNull(simpleStation);

        Collections.sort(simpleStation, new Comparator<SimpleStation>() {
            @Override
            public int compare(SimpleStation o1, SimpleStation o2) {
                return o1.getExtras().get(0).getFree_bikes() - o2.getExtras().get(0).getFree_bikes();
            }
        });
        Collections.reverse(simpleStation);
    }
}
