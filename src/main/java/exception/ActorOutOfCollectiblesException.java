package exception;

public class ActorOutOfCollectiblesException extends Exception {
    public ActorOutOfCollectiblesException(){
        super("The Actor may not give out any more Collectibles, as he ran out of Collectibles!");
    }
}
