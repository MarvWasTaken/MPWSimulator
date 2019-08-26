package exception;

public class OutOfFieldException extends Exception {
    public OutOfFieldException(){
        super("You are about to leave the field!");
    }
}
