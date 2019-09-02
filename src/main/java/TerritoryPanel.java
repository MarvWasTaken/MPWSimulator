import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import model.Territory;

public class TerritoryPanel extends Canvas {

    final static int cellSize = 24;

    Territory territory;
    GraphicsContext gc;

    public TerritoryPanel(Territory territory, ScrollPane parent){
        super(territory.getTiles().length * cellSize * 1.1,territory.getTiles()[0].length * cellSize * 1.1);

        //parent.setPrefSize(this.getWidth(), this.getHeight());
        parent.setPrefViewportHeight(this.getHeight());
        parent.setPrefViewportWidth(this.getWidth());
        this.territory=territory;
        territory.getTiles()[1][0] = territory.WALL;
        territory.getTiles()[1][1] = territory.WALL;
        territory.getTiles()[1][2] = territory.WALL;
        territory.getTiles()[1][3] = territory.WALL;
        territory.getTiles()[11][10] = territory.WALL;
        territory.getTiles()[12][10] = territory.WALL;
        territory.getTiles()[13][10] = territory.WALL;
        territory.getTiles()[14][10] = territory.WALL;
        territory.getActor().setxPos(5);
        this.gc = this.getGraphicsContext2D();
        draw();
    }

    public void draw(){
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        //int row = cellSize/2;
        int row = 0;
        for(int i = 0; i < territory.getTiles().length;i++){
            for(int j = 0; j < territory.getTiles()[i].length;j++){
                if(territory.getTiles()[j][i]==territory.WALL){
                    gc.setFill(Color.RED);
                } else if(territory.getActor().getxPos() == i && territory.getActor().getyPos() == j){
                    gc.setFill(Color.BLUE);
                }
                gc.fillRect(row, (j+1)*cellSize, cellSize, cellSize);
                gc.strokeRect(row, (j+1)*cellSize, cellSize, cellSize);
                gc.setFill(Color.GREEN);
            }
            row+=cellSize;
        }
        territory.print();
    }
}
