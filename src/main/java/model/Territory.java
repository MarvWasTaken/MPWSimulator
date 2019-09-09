package model;

import exceptions.CollectibleCountMayNotBeNegativeException;
import exceptions.ObstacleNotPossibleException;

public class Territory {

    public final int OBSTACLE = -1;
    private Actor actor;
    private int [][] tiles;

    public void setCollectiblesCount(int yPos, int xPos, int count) throws CollectibleCountMayNotBeNegativeException {
        if(count < 0){
            throw new CollectibleCountMayNotBeNegativeException();
        }
        tiles[yPos][xPos] = count;
    }

    public Territory(int height, int width){
        this.tiles = new int[height][width];
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                tiles[i][j]=0;
            }
        }
        actor = addActor(0,0);
        actor.setTerritory(this);

    }

    public Actor addActor(int yPos, int xPos){
        this.actor = new Actor(yPos, xPos);
        return actor;
    }

    public void addObstacle(int yPos, int xPos) {
        if(yPos == this.actor.getyPos() && xPos == this.actor.getxPos()){
            new ObstacleNotPossibleException().printStackTrace();
        } else {
            this.tiles[yPos][xPos] = OBSTACLE;
        }
    }

    public void print(){
        //iteration über die zeilen
        for(int i = 0; i < tiles.length; i++){
            //iteration über spalten
            for(int j = 0; j < tiles[i].length; j++){
                if(this.actor.getxPos() == j && this.actor.getyPos() == i){
                    System.out.print(actor.getDirectedSymbol());
                }else if(tiles[i][j]== OBSTACLE){
                    System.out.print("X");
                }else{
                    System.out.print(tiles[i][j]);
                }
            }
            System.out.println();
        }
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor hamster) {
        this.actor = hamster;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }
}
