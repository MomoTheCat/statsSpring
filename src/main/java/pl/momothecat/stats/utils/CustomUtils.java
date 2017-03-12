package pl.momothecat.stats.utils;

import pl.momothecat.stats.utils.exceptions.InvalidListException;

import java.util.List;

/**
 * Created by szymon on 12.03.2017.
 */
public class CustomUtils {

    public static <T> void checkIfValueNotNull(T element) {
        if (element == null)
            throw new NullPointerException("Element must not be null");
    }

    public static <T> void checkIfListNotNull(List<T> elements) throws  InvalidListException{
        if (elements == null || elements.isEmpty())
            throw new InvalidListException(elements,"Wrong list");
    }
}
