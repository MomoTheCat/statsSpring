package pl.momothecat.stats.utils.exceptions;

import java.util.Objects;

/**
 * Created by Kacper on 2017-03-12.
 */
public class InvalidElementException extends  Exception{

    public InvalidElementException(){
    }

    public InvalidElementException(Object element, String message){
        super(message+element.toString());
    }
}
