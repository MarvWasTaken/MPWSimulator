import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import model.Territory;

public class TerritoryPanel extends Region {

    Canvas canvas;

    final static int cellSize = 32;

    Territory territory;
    GraphicsContext gc;

    public TerritoryPanel(Territory territory, ScrollPane parent) {
        canvas = new Canvas(territory.getTiles().length * cellSize * 1.1, territory.getTiles()[0].length * cellSize * 1.1);
        //canvas = new Canvas(2000, 2000);

        //parent.setPrefSize(this.getWidth(), this.getHeight());
        parent.setPrefViewportHeight(canvas.getHeight());
        parent.setPrefViewportWidth(canvas .getWidth());

        parent.viewportBoundsProperty().addListener((observable)-> this.zentrieren(parent, canvas));

        this.territory = territory;
        territory.getTiles()[1][0] = territory.WALL;
        territory.getTiles()[1][1] = territory.WALL;
        territory.getTiles()[1][2] = territory.WALL;
        territory.getTiles()[1][3] = territory.WALL;
        territory.getTiles()[6][9] = territory.WALL;
        territory.getTiles()[7][9] = territory.WALL;
        territory.getTiles()[8][9] = territory.WALL;
        territory.getTiles()[9][9] = territory.WALL;
        territory.getActor().setxPos(5);
        this.gc = canvas.getGraphicsContext2D();
        draw(parent);


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
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 2000, 2000);

        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
//        int row = 0;

        double row = (canvas.getHeight()-territory.getTiles().length*cellSize)/2;
        double column = (canvas.getWidth()-territory.getTiles()[0].length*cellSize)/2;

        gc.fillRect(row,column, territory.getTiles().length*cellSize, territory.getTiles()[0].length*cellSize);

        for (int i = 0; i < territory.getTiles().length; i++) {
            for (int j = 0; j < territory.getTiles()[i].length; j++) {
                if (territory.getTiles()[j][i] == territory.WALL) {
                    gc.setFill(Color.RED);
                    //gc.fillRect(row, (j) * cellSize + column, cellSize, cellSize);
                    Image image = new Image(getClass().getResource("main/resources/Wall32.png").toString());
                    gc.drawImage(image, row, j * cellSize + column, cellSize, cellSize);
                } else if (territory.getActor().getxPos() == i && territory.getActor().getyPos() == j) {
                    gc.setFill(Color.BLUE);
                    Image image = new Image(getClass().getResource("main/resources/3Hamster32.png").toString());
                    gc.drawImage(image, row, j * cellSize + column, cellSize, cellSize);
                }
                gc.strokeRect(row, (j) * cellSize + column, cellSize, cellSize);
            }
            row += cellSize;
        }
        territory.print();
    }
}
