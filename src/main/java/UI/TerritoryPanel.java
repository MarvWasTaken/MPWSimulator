package UI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import model.Territory;

public class TerritoryPanel extends Region {

    static final int SETTING_ACTOR = 0;
    static final int SETTING_OBSTACLE = 1;
    static final int SETTING_CORN = 2;
    static final int DELETING_STUFF = 3;

    private int territoryMode = -1;

    private

    Canvas canvas;

    final static int CELLSIZE = 32;

    private Territory territory;

    GraphicsContext gc;

    public TerritoryPanel(Territory territory, ScrollPane parent) {
        canvas = new Canvas(territory.getTiles().length * CELLSIZE + 2, territory.getTiles()[0].length * CELLSIZE + 2);
        //canvas = new Canvas(2000, 2000);

        //parent.setPrefSize(this.getWidth(), this.getHeight());
        parent.setPrefViewportHeight(canvas.getHeight());
        parent.setPrefViewportWidth(canvas .getWidth());

        parent.viewportBoundsProperty().addListener((observable)-> this.zentrieren(parent, canvas));

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
        draw(parent);

        canvas.setOnKeyPressed(e->{
            if(e.getCode() == KeyCode.UP){
                this.territory.getActor().move();
                draw(parent);
            }
            System.out.println(e.getCode().toString());
        });

        this.getChildren().add(canvas);
    }

    /**
     * Methode entstand in zusammenarbeit mit Lukas Monert.
     * Sie dient dem zentrieren des Canvas innerhalb der Scrollpane
     * @param parent
     * @param canvas
     */
    private void zentrieren(ScrollPane parent, Canvas canvas) {
        if(parent.getViewportBounds().getWidth() > canvas.getWidth()){
            canvas.setTranslateX((parent.getViewportBounds().getWidth() - canvas.getWidth())/2);
        } else {
            canvas.setTranslateX(0);
        }
        if(parent.getViewportBounds().getHeight() > canvas.getHeight()){
            canvas.setTranslateY((parent.getViewportBounds().getHeight()-canvas.getHeight())/2);
        } else {
            canvas.setTranslateY(0);
        }
    }
    public void draw(ScrollPane sc) {
        gc.clearRect(0,0,10000,10000);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 2000, 2000);

        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
//        int row = 0;

        double row = (canvas.getHeight()-territory.getTiles().length* CELLSIZE)/2;
        double column = (canvas.getWidth()-territory.getTiles()[0].length* CELLSIZE)/2;

        gc.fillRect(row,column, territory.getTiles().length* CELLSIZE, territory.getTiles()[0].length* CELLSIZE);

        for (int i = 0; i < territory.getTiles().length; i++) {
            for (int j = 0; j < territory.getTiles()[i].length; j++) {
                if (territory.getTiles()[j][i] == territory.OBSTACLE) {
                    gc.setFill(Color.RED);
                    //gc.fillRect(row, (j) * CELLSIZE + column, CELLSIZE, CELLSIZE);
                    Image image = new Image(getClass().getResource("../main/resources/Wall32.png").toString());
                    gc.drawImage(image, row, j * CELLSIZE + column, CELLSIZE, CELLSIZE);
                } else if (territory.getActor().getxPos() == i && territory.getActor().getyPos() == j) {
                    gc.setFill(Color.BLUE);
                    Image image = new Image(getClass().getResource("../main/resources/"+this.getTerritory().getActor().getDirection()+"Hamster32.png").toString());
                    gc.drawImage(image, row, j * CELLSIZE + column, CELLSIZE, CELLSIZE);
                }
                gc.strokeRect(row, (j) * CELLSIZE + column, CELLSIZE, CELLSIZE);
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
        System.out.println("Territory Mode is "+this.territoryMode);
    }


}