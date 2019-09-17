package compiled;

import model.Territory;

public class DefaultTerritory extends Territory {
    public DefaultTerritory(){
        super(12,12,0,0);

        //Placing Demo Obstacles
        this.getTiles()[1][0] = this.OBSTACLE;
        this.getTiles()[1][1] = this.OBSTACLE;
        this.getTiles()[1][2] = this.OBSTACLE;
        this.getTiles()[1][3] = this.OBSTACLE;
        this.getTiles()[6][9] = this.OBSTACLE;
        this.getTiles()[7][9] = this.OBSTACLE;
        this.getTiles()[8][9] = this.OBSTACLE;
        this.getTiles()[9][9] = this.OBSTACLE;

        this.getTiles()[5][5] = 9;

    }
}
