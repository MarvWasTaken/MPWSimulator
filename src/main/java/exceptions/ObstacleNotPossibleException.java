package exceptions;

public class ObstacleNotPossibleException extends Exception {
    public ObstacleNotPossibleException(){
        super("No Obstacle may be placed here!");
    }
}
