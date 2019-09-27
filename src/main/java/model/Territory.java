package model;

import exceptions.CollectibleCountMayNotBeNegativeException;
import exceptions.ObstacleNotPossibleException;

import java.util.Observable;

public class Territory extends Observable {

    public final int OBSTACLE = -1;
    private Actor actor;
    private int [][] tiles;

    public void setCollectiblesCount(int yPos, int xPos, int count) throws CollectibleCountMayNotBeNegativeException {
        if(count < 0){
            throw new CollectibleCountMayNotBeNegativeException();
        }
        tiles[yPos][xPos] = count;
    }

    public Territory(){
        this.tiles = new int[8][8];
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                tiles[i][j]=0;
            }
        }
        actor = addActor(0,0);
        actor.setTerritory(this);
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

    public Territory(int height, int width, int actorXPos, int actorYPos){
        this.tiles = new int[height][width];
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[i].length; j++){
                tiles[i][j]=0;
            }
        }
        actor = addActor(actorYPos,actorXPos);
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

    public void addCorn(int yPos, int xPos) {
        if(this.tiles[yPos][xPos] != -1 && this.tiles[yPos][xPos] < 12){
            this.tiles[yPos][xPos]++;
        } else {
            new Exception("No Corn may be placed here.").printStackTrace();
        }
    }

    public void resize(int x, int y) {
        int newTiles[][] = new int[y][x];
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles[i].length; j++) {
                if(i<y && j<x){
                    newTiles[i][j] = this.tiles[i][j];
                }
            }
        }
        if(this.actor.getxPos()>=x || this.actor.getyPos() >= y){
            this.actor.setxPos(0);
            this.actor.setyPos(0);
        }
        this.tiles = newTiles;
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

    /**
     * Hamstermethoden
     */
    public void vor(){
        this.actor.vor();
        this.setChanged();
        this.notifyObservers();
    }
    public void linksUm(){
        this.actor.linksUm();
        this.setChanged();
        this.notifyObservers();
    }
    public boolean vornFrei() {
        return this.actor.vornFrei(this);
    }

    public void gib(){
        this.actor.gib();
    }

    public void nimm(){
        this.actor.nimm();
    }

    public boolean kornDa(){
        return this.actor.kornDa();
    }

    public int[][] getTiles() {
        return tiles;
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }

    public void main() {
        this.getActor().main();
        System.out.println("Ich bin die herkömmliche Main!");
    }
}
