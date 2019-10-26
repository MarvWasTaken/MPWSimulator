import contoller.ProgrammController;
import contoller.WindowController;
import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import model.DefaultTerritory;
import model.Territory;
import util.MediaProvider;
import view.TerritoryPanel;

import javax.print.attribute.standard.Media;


public class MainFx extends Application {

    @Override
    public void start(Stage stage) {
        WindowController windowController = new WindowController();
        Territory defaultTerritory = new DefaultTerritory();
        windowController.prepareStage(stage, defaultTerritory, "DefaultHamster");
        ProgrammController pg = new ProgrammController(windowController.getCodeArea());
    }

    public static void main(String[] args) {
        launch();
        ProgrammController.loadDefaultTerritory();
        //TestAufgabe2.startTest();
    }
}