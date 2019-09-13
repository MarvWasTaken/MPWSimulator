import UI.CustomMenuBar;
import UI.CustomToolBar;
import UI.TerritoryPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.Actor;
import model.Territory;

public class MenuBuilder {

    public MenuBuilder() {

    }

    public Scene buildScene() {
        //Initializing the central window components
        BorderPane root = new BorderPane();
        BorderPane subRootPane = new BorderPane();
        SplitPane splitPane = new SplitPane();
        TextArea codeEditor = new TextArea();

        ScrollPane scrollPane = new ScrollPane();
        TerritoryPanel territoryPanel = new TerritoryPanel(new Territory(12, 12), scrollPane);

        scrollPane.setContent(territoryPanel);

        //Filling root with the MenuBar and the subroot.
        CustomMenuBar menuBar = this.buildMenuBar(territoryPanel);
        root.setTop(menuBar);
        root.setCenter(subRootPane);
        //Filling the subroot with the toolbar and the splitpane
        CustomToolBar customToolBar = this.buildToolbar(menuBar);
        menuBar.setCustomToolBar(customToolBar);
        subRootPane.setTop(customToolBar);
        subRootPane.setCenter(splitPane);
        Label statusLabel = new Label("Hallo Dibo");
        subRootPane.setBottom(statusLabel);

        splitPane.getItems().addAll(codeEditor, scrollPane);
        Scene scene = new Scene(root, 950, 650);

        territoryPanel.getCanvas().addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                int x = (int) (Math.floor(mouseEvent.getX() / TerritoryPanel.CELLSIZE));
                int y = (int) (Math.floor(mouseEvent.getY() / TerritoryPanel.CELLSIZE));
                if(x == territoryPanel.getTerritory().getActor().getxPos() && y == territoryPanel.getTerritory().getActor().getyPos()) {
                    territoryPanel.setDraggingActor(true);
                    System.out.println("Hamster wird gezogen!");
                } else {

                }
        });

        territoryPanel.getCanvas().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            if(territoryPanel.isDraggingActor()){
                int x = (int) (Math.floor(mouseEvent.getX() / TerritoryPanel.CELLSIZE));
                int y = (int) (Math.floor(mouseEvent.getY() / TerritoryPanel.CELLSIZE));
                    if(y<territoryPanel.getTerritory().getTiles().length
                            && x<territoryPanel.getTerritory().getTiles()[0].length
                            && territoryPanel.getTerritory().getTiles()[y][x]!=-1 ){
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
                    territoryPanel.getTerritory().addCorn(y,x);
                    break;
                case TerritoryPanel.DELETING_STUFF:
                    territoryPanel.getTerritory().getTiles()[y][x] = 0;
                    break;
                default:
                    System.out.println("Hat nicht geklappt du Lurch");

            }
            territoryPanel.draw();
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
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

    public CustomMenuBar buildMenuBar(TerritoryPanel territoryPanel) {
        return new CustomMenuBar(territoryPanel);

    }

    public CustomToolBar buildToolbar(CustomMenuBar customMenuBar) {
        return new CustomToolBar(customMenuBar);
    }

    /**
     * helper method to make assigning images easier
     *
     * @param menuItem
     * @param filepath
     */
    private void quickItemIcon(MenuItem menuItem, String filepath) {
        Image iconImage;
        try {
            iconImage = new Image(getClass().getResource(filepath).toString());
        } catch (NullPointerException e) {
            System.out.println("Bild bei " + filepath + " nicht gefunden!");
            return;
        }
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        menuItem.setGraphic(imageView);

    }

    /**
     * helper method to make assigning images to Buttons easier
     *
     * @param button
     * @param filepath
     */
    private void quickButtonIcon(Button button, String filepath) {
        Image iconImage;
        try {
            iconImage = new Image(getClass().getResource(filepath).toString());
        } catch (NullPointerException e) {
            System.out.println("Bild bei " + filepath + " nicht gefunden!");
            return;
        }
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        button.setGraphic(imageView);

    }

    /**
     * helper method to make assigning images to Buttons easier
     *
     * @param toggleButton
     * @param filepath
     */
    private void quickToggleButtonIcon(ToggleButton toggleButton, String filepath) {
        Image iconImage;
        try {
            iconImage = new Image(getClass().getResource(filepath).toString());
        } catch (NullPointerException e) {
            System.out.println("Bild bei " + filepath + " nicht gefunden!");
            return;
        }
        ImageView imageView = new ImageView(iconImage);
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        toggleButton.setGraphic(imageView);

    }

    /**
     * helper method to make assigning accelerators easier
     *
     * @param item
     * @param accelerator
     */
    static void quickItemAcc(MenuItem item, String accelerator) {
        item.setAccelerator(KeyCombination.keyCombination(accelerator));
    }
}
