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
//        String javaVersion = System.getProperty("java.version");
//        String javafxVersion = System.getProperty("javafx.version");
//        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//        Scene scene = new Scene(new StackPane(l), 640, 480);
//        stage.setScene(scene);
//        stage.show();
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("Nummer 1");
        choiceBox.getItems().add("Nummer 2");
        choiceBox.getItems().add("Nummer 3");
        choiceBox.getItems().add("Nummer 4");
        choiceBox.getItems().add("Nummer 5");
        choiceBox.getItems().add("Nummer 6");
        choiceBox.setOnAction(event -> {
            System.out.println(choiceBox.getValue());
        });
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