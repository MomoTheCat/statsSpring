package pl.momothecat.stats.utils.exceptions;

import java.util.List;
import java.util.Objects;

/**
 * Created by Kacper on 2017-03-12.
 * Custom exception
 */
public class InvalidListException extends Exception {

    private List<Objects> list;
    public InvalidListException(){

    }

    public InvalidListException(List list, String messge){
        super(messge);
        this.list = list;
    }

    private boolean isListNull(){
        if (list == null){
            return true;
        }
        return false;
    }

    private boolean isEmpty() {
        if (!isListNull()) {
            return list.isEmpty();
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        if (isListNull()){
            sb.append("\nList is null");
        }else if (isEmpty()){
            sb.append("\nList is Empty");
        }else {
            sb.append("\nSomething here is wrong, check that list: ");
            for (Objects object: list ) {
                sb.append("\n").append(object.toString());
            }
        }
        return sb.toString();
    }
}
