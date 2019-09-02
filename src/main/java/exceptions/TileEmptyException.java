package exceptions;

public class TileEmptyException extends Exception {
    public TileEmptyException(){
        super("The Tile is Empty!");
    }
}
