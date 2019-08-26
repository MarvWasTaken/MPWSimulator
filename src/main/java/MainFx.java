import exception.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainFx extends Application {

    @Override
    public void start(Stage stage) {
        MenuBuilder mb = new MenuBuilder();
        stage.setTitle("MPW Simulator");
        Scene scene = mb.buildScene();
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("main/resources/Hamster24.png")));
        stage.setMinWidth(950);
        stage.setMinHeight(650);
        stage.show();
    }

    public static void main(String[] args) throws TileEmptyException, OutOfFieldException, ObstacleNotPossibleException, ActorOutOfCollectiblesException, CollectibleCountMayNotBeNegativeException {
        //launch();
        TestAufgabe2.startTest();
    }
}