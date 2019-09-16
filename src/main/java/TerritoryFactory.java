import model.Actor;
import model.Territory;

public class TerritoryFactory {
    public static Territory createDefaultTerritory(){
        Territory territory = new Territory(12,12,0,0);

        //Placing Demo Obstacles
        territory.getTiles()[1][0] = territory.OBSTACLE;
        territory.getTiles()[1][1] = territory.OBSTACLE;
        territory.getTiles()[1][2] = territory.OBSTACLE;
        territory.getTiles()[1][3] = territory.OBSTACLE;
        territory.getTiles()[6][9] = territory.OBSTACLE;
        territory.getTiles()[7][9] = territory.OBSTACLE;
        territory.getTiles()[8][9] = territory.OBSTACLE;
        territory.getTiles()[9][9] = territory.OBSTACLE;

        territory.getTiles()[5][5] = 9;

        return territory;
    }
}
