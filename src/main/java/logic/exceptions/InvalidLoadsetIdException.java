package logic.exceptions;

/**
 * Created by uc198829 on 31/5/2016.
 */
public class InvalidLoadsetIdException extends IllegalArgumentException {
    public InvalidLoadsetIdException() {
        super("provide correct loadset ID (it should contain digits only)");
    }
}
