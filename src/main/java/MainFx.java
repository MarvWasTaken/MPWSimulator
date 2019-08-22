import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class MainFx extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        BorderPane subRootPane = new BorderPane();
        SplitPane splitPane = new SplitPane();
        TextArea codeEditor = new TextArea();
        Pane territory = new Pane();
        root.setTop(new MenuBuilder().buildMenuBar());
        root.setCenter(subRootPane);
        subRootPane.setTop(new MenuBuilder().buildToolbar());
        subRootPane.setCenter(splitPane);

        splitPane.getItems().addAll(codeEditor, territory);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}