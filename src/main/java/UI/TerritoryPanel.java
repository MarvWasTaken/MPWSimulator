package UI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import model.Territory;

import java.util.Optional;

public class TerritoryPanel extends Region {

    public static final int SETTING_ACTOR = 0;
    public static final int SETTING_OBSTACLE = 1;
    public static final int SETTING_CORN = 2;
    public static final int DELETING_STUFF = 3;

    private boolean draggingActor = false;

    private int territoryMode = -1;

    private Canvas canvas;

    public final static int CELLSIZE = 32;

    private Territory territory;

    private ScrollPane parent;

    GraphicsContext gc;

    public TerritoryPanel(Territory territory, ScrollPane parent) {
        this.parent = parent;
        canvas = new Canvas(territory.getTiles().length * CELLSIZE + 2, territory.getTiles()[0].length * CELLSIZE + 2);
        //canvas = new Canvas(2000, 2000);

        //parent.setPrefSize(this.getWidth(), this.getHeight());
        parent.setPrefViewportHeight(canvas.getHeight());
        parent.setPrefViewportWidth(canvas.getWidth());

        parent.viewportBoundsProperty().addListener((observable) -> this.zentrieren(parent, canvas));

        this.territory = territory;

        //Placing Demo Obstacles
        territory.getTiles()[1][0] = territory.OBSTACLE;
        territory.getTiles()[1][1] = territory.OBSTACLE;
        territory.getTiles()[1][2] = territory.OBSTACLE;
        territory.getTiles()[1][3] = territory.OBSTACLE;
        territory.getTiles()[6][9] = territory.OBSTACLE;
        territory.getTiles()[7][9] = territory.OBSTACLE;
        territory.getTiles()[8][9] = territory.OBSTACLE;
        territory.getTiles()[9][9] = territory.OBSTACLE;
        territory.getActor().setxPos(5);
        this.gc = canvas.getGraphicsContext2D();
        draw();

        canvas.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                this.territory.getActor().move();
                draw();
            }
            System.out.println(e.getCode().toString());
        });

        this.getChildren().add(canvas);
    }

    /**
     * Methode entstand in zusammenarbeit mit Lukas Monert.
     * Sie dient dem zentrieren des Canvas innerhalb der Scrollpane
     *
     * @param parent
     * @param canvas
     */
    private void zentrieren(ScrollPane parent, Canvas canvas) {
        if (parent.getViewportBounds().getWidth() > canvas.getWidth()) {
            canvas.setTranslateX((parent.getViewportBounds().getWidth() - canvas.getWidth()) / 2);
        } else {
            canvas.setTranslateX(0);
        }
        if (parent.getViewportBounds().getHeight() > canvas.getHeight()) {
            canvas.setTranslateY((parent.getViewportBounds().getHeight() - canvas.getHeight()) / 2);
        } else {
            canvas.setTranslateY(0);
        }
    }

    public void startResizeDialog() {
        /**
         * Using code from https://code.makery.ch/blog/javafx-dialogs-official/
         */
        TextInputDialog dialog = new TextInputDialog(territory.getTiles()[0].length+","+territory.getTiles().length);
        dialog.setTitle("Dialog Territorium anpassen ...");
        dialog.setHeaderText("Größe Ändern");
        dialog.setContentText("Bitte gebe die neue Feldgröße ein! \b (x dimension, y dimension. Kommasepariert!) \b Beispiel: \"10,10\"");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newDimensions -> {
            int x = Integer.valueOf(newDimensions.substring(0, newDimensions.indexOf(',')));
            int y = Integer.valueOf(newDimensions.substring(newDimensions.indexOf(',')+1));
            resize(x,y);
            return;
        });
        System.out.println("nothing happened.");
    }


    public void resize(int x, int y) {
        this.territory.resize(x, y);
        draw();
    }
    public void linksUmEventTriggered() {
        this.territory.getActor().linksUm();
        draw();
    }
    public void moveEventTriggered(){
        this.territory.getActor().move();
        draw();
    }

    public void draw() {
        //canvas = new Canvas(territory.getTiles().length * CELLSIZE + 2, territory.getTiles()[0].length * CELLSIZE + 2);
        canvas.setHeight(territory.getTiles().length * CELLSIZE + 2);
        canvas.setWidth(territory.getTiles()[0].length * CELLSIZE + 2);
        gc.clearRect(0, 0, 10000, 10000);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 2000, 2000);

        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
//        int row = 0;

        double row = (canvas.getHeight() - territory.getTiles().length * CELLSIZE) / 2;
        double column = (canvas.getWidth() - territory.getTiles()[0].length * CELLSIZE) / 2;

        gc.fillRect(row, column, territory.getTiles()[0].length * CELLSIZE, territory.getTiles().length * CELLSIZE);

        for (int i = 0; i < territory.getTiles().length; i++) {
            for (int j = 0; j < territory.getTiles()[0].length; j++) {
                if (territory.getTiles()[i][j] == territory.OBSTACLE) {
                    gc.setFill(Color.RED);
                    //gc.fillRect(row, (j) * CELLSIZE + column, CELLSIZE, CELLSIZE);
                    Image image = new Image(getClass().getResource("../main/resources/Wall32.png").toString());
                    gc.drawImage(image, j * CELLSIZE + column, row, CELLSIZE, CELLSIZE);
                } else if (territory.getActor().getxPos() == j && territory.getActor().getyPos() == i) {
                    gc.setFill(Color.BLUE);
                    Image image = new Image(getClass().getResource("../main/resources/" + this.getTerritory().getActor().getDirection() + "Hamster32.png").toString());
                    gc.drawImage(image,  j * CELLSIZE + column, row, CELLSIZE, CELLSIZE);
                }
                gc.strokeRect( (j) * CELLSIZE + column, row, CELLSIZE, CELLSIZE);
            }
            row += CELLSIZE;
        }
        territory.print();
    }


    //Getters and Setters

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public int getTerritoryMode() {
        return territoryMode;
    }

    public void setTerritoryMode(int territoryMode) {
        this.territoryMode = this.territoryMode == territoryMode ? -1 : territoryMode;
        System.out.println("Territory Mode is " + this.territoryMode);
    }


    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public boolean isDraggingActor() {
        return draggingActor;
    }

    public void setDraggingActor(boolean draggingActor) {
        this.draggingActor = draggingActor;
    }
}
