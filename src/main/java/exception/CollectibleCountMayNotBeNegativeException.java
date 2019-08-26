package exception;

public class CollectibleCountMayNotBeNegativeException extends Exception {
    public CollectibleCountMayNotBeNegativeException(){
        super("The Collectible count may not be negative.");
    }
}
