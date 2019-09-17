package contoller;

import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Actor;
import model.Territory;
import view.CodeArea;
import view.CustomMenuBar;
import view.CustomToolBar;
import view.TerritoryPanel;

import java.io.File;
import java.util.Optional;

public class WindowController {

    private Stage stage;

    private CodeArea codeArea;

    public WindowController() {
        codeArea = new CodeArea();
    }

    public void startNewFileDialog() {
        /**
         * Using code from https://code.makery.ch/blog/javafx-dialogs-official/
         */
        String placeholder = "Bitte neuen Dateinamen angeben.";
        TextInputDialog dialog = new TextInputDialog(placeholder);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        dialog.setTitle("Neues Programm Titel");
        dialog.setHeaderText("Bitte Dateinamen eingeben.");
        dialog.setContentText("Führender Buchstabe muss großgeschrieben sein (Java Codeconvention)!");
        //Listener added to dialog text property. this ensures that only valid
        dialog.getEditor().textProperty().addListener((event, oldValue, newValue) -> {
            if (oldValue.equals(placeholder)) {
                if (newValue.length() > oldValue.length()) {
                    String substring = newValue.substring(oldValue.length());
                    if (substring.isEmpty()) {
                        dialog.getEditor().setText("");
                    } else {
                        dialog.getEditor().setText(substring);
                    }
                } else {
                    dialog.getEditor().setText("");
                }
            }
            if (newValue.matches("^[A-Z]([a-z]|[A-Z]|[0-9])*$") || newValue.equals("")) {
                dialog.getEditor().setText(newValue);
            } else {
                dialog.getEditor().setText(oldValue);
            }
            dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(!dialog.getEditor().getText().matches("^[A-Z]([a-z]|[A-Z]|[0-9])*$"));
        });


        Optional<String> result = dialog.showAndWait();

        System.out.println("new Stage created!");


        result.ifPresent(fileName -> {
            ProgrammController.makeProgrammFile(fileName, codeArea);
            new WindowController()
                    .prepareStage(
                            new Stage(),
                            new Territory(10, 10, 5, 5),
                            fileName);

        });
    }

    public Scene buildScene(Territory territory) {
        //Initializing the central window components
        BorderPane root = new BorderPane();
        BorderPane subRootPane = new BorderPane();
        SplitPane splitPane = new SplitPane();

        ScrollPane scrollPane = new ScrollPane();
        TerritoryPanel territoryPanel = new TerritoryPanel(territory, scrollPane);

        scrollPane.setContent(territoryPanel);

        //Filling root with the MenuBar and the subroot.
        CustomMenuBar customMenuBar = this.buildMenuBar(territoryPanel, this);
        root.setTop(customMenuBar);
        root.setCenter(subRootPane);
        //Filling the subroot with the toolbar and the splitpane
        CustomToolBar customToolBar = this.buildToolbar(customMenuBar, this);
        customMenuBar.setCustomToolBar(customToolBar);
        subRootPane.setTop(customToolBar);
        subRootPane.setCenter(splitPane);
        Label statusLabel = new Label("Hallo Dibo");
        subRootPane.setBottom(statusLabel);

        splitPane.getItems().addAll(codeArea, scrollPane);
        Scene scene = new Scene(root, 950, 650);

        territoryPanel.getCanvas().addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            int x = (int) (Math.floor(mouseEvent.getX() / TerritoryPanel.CELLSIZE));
            int y = (int) (Math.floor(mouseEvent.getY() / TerritoryPanel.CELLSIZE));
            if (x == territoryPanel.getTerritory().getActor().getxPos() && y == territoryPanel.getTerritory().getActor().getyPos()) {
                territoryPanel.setDraggingActor(true);
                System.out.println("Hamster wird gezogen!");
            } else {

            }
        });

        territoryPanel.getCanvas().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if (territoryPanel.isDraggingActor()) {
                int x = (int) (Math.floor(mouseEvent.getX() / TerritoryPanel.CELLSIZE));
                int y = (int) (Math.floor(mouseEvent.getY() / TerritoryPanel.CELLSIZE));
                if (y < territoryPanel.getTerritory().getTiles().length
                        && x < territoryPanel.getTerritory().getTiles()[0].length
                        && territoryPanel.getTerritory().getTiles()[y][x] != -1) {
                    territoryPanel.getTerritory().getActor().setxPos(x);
                    territoryPanel.getTerritory().getActor().setyPos(y);
                }
                territoryPanel.draw();
            }
            System.out.println("dragged!");
        });

        territoryPanel.getCanvas().addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            territoryPanel.setDraggingActor(false);
            System.out.println("Hamster wird nicht mehr gezogen!");
        });

        territoryPanel.getCanvas().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            int x = (int) (Math.floor(mouseEvent.getX() / TerritoryPanel.CELLSIZE));
            int y = (int) (Math.floor(mouseEvent.getY() / TerritoryPanel.CELLSIZE));
            System.out.println(x);
            System.out.println(y);
            switch (territoryPanel.getTerritoryMode()) {
                case TerritoryPanel.SETTING_ACTOR:
                    territoryPanel.getTerritory().getActor().setxPos(x);
                    territoryPanel.getTerritory().getActor().setyPos(y);
                    break;
                case TerritoryPanel.SETTING_OBSTACLE:
                    territoryPanel.getTerritory().addObstacle(y, x);
                    break;
                case TerritoryPanel.SETTING_CORN:
                    territoryPanel.getTerritory().addCorn(y, x);
                    break;
                case TerritoryPanel.DELETING_STUFF:
                    territoryPanel.getTerritory().getTiles()[y][x] = 0;
                    break;
                default:
                    System.out.println("Hat nicht geklappt du Lurch");

            }
            territoryPanel.draw();
        });

        scrollPane.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            Actor a = territoryPanel.getTerritory().getActor();
            System.out.println(keyEvent.getCode().toString());
            switch (keyEvent.getCode()) {
                case UP:
                    a.move();
                    break;
                case LEFT:
                    a.linksUm();
                    break;
                case G:
                    a.gib();
                    break;
                case N:
                    a.nimm();
                    break;
                default:
                    break;
            }
            territoryPanel.draw();
        });

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                territoryPanel.getTerritory().getActor().move();
                territoryPanel.draw();
                System.out.println("gemacht!");
            }
            System.out.println(e.getCode().toString());
        });

        customToolBar.getOpenFileBtn().setOnMouseReleased(event ->{
            loadProgramm();
        });

        return scene;
    }


    public CustomMenuBar buildMenuBar(TerritoryPanel territoryPanel, WindowController windowController) {
        return new CustomMenuBar(territoryPanel, windowController);
    }

    public CustomToolBar buildToolbar(CustomMenuBar customMenuBar, WindowController windowController) {
        return new CustomToolBar(customMenuBar, windowController);
    }

    public void prepareStage(Stage stage, Territory territory, String fileName) {
        this.stage = stage;
        stage.setTitle("MPW Simulator - " + fileName);
        Scene scene = this.buildScene(territory);

        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../main/resources/Hamster24.png")));
        stage.setMinWidth(950);
        stage.setMinHeight(650);
        stage.show();
    }

    public void loadProgramm(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Programm auswählen!");
        fc.setInitialDirectory(new File(ProgrammController.JAVA_DIRECTORY));
        File file = fc.showOpenDialog(stage);
        if(file != null){
            ProgrammController.loadProgrammFromFile(file);
        } else {
            System.out.println("File konnte nicht geladen werden.");
        }

    }

    public CodeArea getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(CodeArea codeArea) {
        this.codeArea = codeArea;
    }
}
