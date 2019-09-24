package exceptions;

public class TileEmptyException extends RuntimeException {
    public TileEmptyException(){
        super("The Tile is Empty!");
    }
}
