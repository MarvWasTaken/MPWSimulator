package model;

import annotations.Invisible;
import exceptions.ActorOutOfCollectiblesException;
import exceptions.OutOfFieldException;
import exceptions.TileEmptyException;

import java.util.Observable;


public class Actor extends Observable {
    private int numberOfCollectibles = 0;


    private Territory territory;

    private int yPos;
    private int xPos;

    @Invisible
    public int getDirection() {
        return direction;
    }

    @Invisible
    public void setDirection(int direction) {
        this.direction = direction;
    }

    private int direction;

    final int NORTH = 0;
    final int WEST = 1;
    final int SOUTH = 2;
    final int EAST = 3;

    public Actor(int yPos, int xPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        direction = EAST;
    }

    public void linksUm() {
        this.direction += 1;
        this.direction %= 4;
    }

    public boolean vornFrei(Territory territory) throws OutOfFieldException {
        switch (this.direction) {
            case NORTH:
                if (yPos >= 1) {
                    return (territory.getTiles()[yPos - 1][xPos]) >= 0;
                } else {
                    System.out.println("Norden ist blockiert!");
                    throw new OutOfFieldException();
                }
            case EAST:
                if (xPos < (territory.getTiles()[yPos].length - 1)) {
                    return (territory.getTiles()[yPos][xPos + 1]) >= 0;
                } else {
                    System.out.println("Osten ist blockiert!");
                    throw new OutOfFieldException();
                }
            case SOUTH:
                if (yPos < (territory.getTiles().length - 1)) {
                    return (territory.getTiles()[yPos + 1][xPos]) >= 0;
                } else {
                    System.out.println("Süden ist blockiert!");
                    throw new OutOfFieldException();
                }
            case WEST:
                if (xPos >= 1) {
                    return (territory.getTiles()[yPos][xPos - 1]) >= 0;
                } else {
                    System.out.println("Westen ist blockiert!");
                    throw new OutOfFieldException();
                }
            default:
                return false;
        }
    }

    public boolean kornDa(){
        return this.territory.getTiles()[yPos][xPos]>0;
    }

    public void vor() {
        try{
            if (vornFrei(this.territory)) {
                switch (this.direction) {
                    case NORTH:
                        this.yPos--;
                        break;
                    case EAST:
                        this.xPos++;
                        break;
                    case SOUTH:
                        this.yPos++;
                        break;
                    case WEST:
                        this.xPos--;
                        break;
                }
            }
        } catch (OutOfFieldException ofe){
            throw new OutOfFieldException();
        }
    }

    public void nimm() {
        if (this.territory.getTiles()[this.yPos][this.xPos] > 0) {
            this.numberOfCollectibles++;
            this.territory.getTiles()[this.yPos][this.xPos]--;
            System.out.println("es sind noch "+this.territory.getTiles()[this.yPos][this.xPos]+" Körner auf dem Feld.");
        } else {
            throw new TileEmptyException();
        }
    }

    public void gib() {
        if (this.getNumberOfCollectibles() <= 0) {
            throw new ActorOutOfCollectiblesException();
        } else if (this.territory.getTiles()[this.yPos][this.xPos] < 12) {
            this.territory.getTiles()[this.yPos][this.xPos]++;
            this.numberOfCollectibles--;
            System.out.println("Noch "+this.numberOfCollectibles+" Körner im Maul.");
        }
    }

    @Invisible
    public void printPos() {
        System.out.println("Y:" + yPos + " X:" + xPos);
    }

    @Invisible
    public int getxPos() {
        return xPos;
    }

    @Invisible
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    @Invisible
    public int getyPos() {
        return yPos;
    }

    @Invisible
    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    @Invisible
    public Territory getTerritory() {
        return territory;
    }

    @Invisible
    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    @Invisible
    public int getNumberOfCollectibles() {
        return numberOfCollectibles;
    }

    @Invisible
    public void setNumberOfCollectibles(int numberOfCollectibles) {
        this.numberOfCollectibles = numberOfCollectibles;
    }

    @Invisible
    public String getDirectedSymbol() {
        switch (this.direction) {
            case NORTH:
                return "^";
            case WEST:
                return "<";
            case EAST:
                return ">";
            case SOUTH:
                return "v";
            default:
                return "?";
        }
    }

    public void main() {
    }
}
