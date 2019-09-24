package exceptions;

public class OutOfFieldException extends RuntimeException {
    public OutOfFieldException(){
        super("You are about to leave the field!");
    }
}
