package exceptions;

public class ActorOutOfCollectiblesException extends RuntimeException {
    public ActorOutOfCollectiblesException(){
        super("The model.Actor may not give out any more Collectibles, as he ran out of Collectibles!");
    }
}
