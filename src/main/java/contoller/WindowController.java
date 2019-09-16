package contoller;

import com.sun.tools.javac.jvm.Code;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Actor;
import model.Territory;
import view.CodeArea;
import view.CustomMenuBar;
import view.CustomToolBar;
import view.TerritoryPanel;

import java.util.Optional;

public class WindowController {

    private CodeArea codeArea;

    public WindowController() {
        codeArea = new CodeArea();
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
        CustomMenuBar customMenuBar = this.buildMenuBar(territoryPanel);
        root.setTop(customMenuBar);
        root.setCenter(subRootPane);
        //Filling the subroot with the toolbar and the splitpane
        CustomToolBar customToolBar = this.buildToolbar(customMenuBar);
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

        return scene;
    }



    public CustomMenuBar buildMenuBar(TerritoryPanel territoryPanel) { return new CustomMenuBar(territoryPanel); }

    public CustomToolBar buildToolbar(CustomMenuBar customMenuBar) {
        return new CustomToolBar(customMenuBar);
    }

    public void prepareStage(Stage stage, Territory territory, String fileName) {
        stage.setTitle("MPW Simulator - "+fileName);
        Scene scene = this.buildScene(territory);

        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../main/resources/Hamster24.png")));
        stage.setMinWidth(950);
        stage.setMinHeight(650);
        stage.show();
    }

    public CodeArea getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(CodeArea codeArea) {
        this.codeArea = codeArea;
    }
}
