package model;

import exceptions.ActorOutOfCollectiblesException;
import exceptions.OutOfFieldException;
import exceptions.TileEmptyException;

public class Actor {
    private int numberOfCollectibles = 0;


    private Territory territory;

    private int yPos;
    private int xPos;

    public int getDirection() {
        return direction;
    }

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
                    throw new OutOfFieldException();
                }
            case EAST:
                if (xPos < (territory.getTiles()[yPos].length - 1)) {
                    return (territory.getTiles()[yPos][xPos + 1]) >= 0;
                } else {
                    throw new OutOfFieldException();
                }
            case SOUTH:
                if (yPos < (territory.getTiles().length - 1)) {
                    return (territory.getTiles()[yPos + 1][xPos]) >= 0;
                } else {
                    throw new OutOfFieldException();
                }
            case WEST:
                if (xPos >= 1) {
                    return (territory.getTiles()[yPos][xPos - 1]) >= 0;
                } else {
                    throw new OutOfFieldException();
                }
            default:
                return false;
        }
    }

    public void move() {
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
            ofe.printStackTrace();
        }
    }

    public void nimm() {
        if (this.territory.getTiles()[this.yPos][this.xPos] > 0) {
            this.numberOfCollectibles++;
            this.territory.getTiles()[this.yPos][this.xPos]--;
        } else {
            new TileEmptyException().printStackTrace();
        }
    }

    public void gib() {
        if (this.getNumberOfCollectibles() <= 0) {
            new ActorOutOfCollectiblesException().printStackTrace();
        }
        if (this.territory.getTiles()[this.yPos][this.xPos] < 9) {
            this.territory.getTiles()[this.yPos][this.xPos]++;
            this.numberOfCollectibles--;
        }
    }

    public void printPos() {
        System.out.println("Y:" + yPos + " X:" + xPos);
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }


    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public int getNumberOfCollectibles() {
        return numberOfCollectibles;
    }

    public void setNumberOfCollectibles(int numberOfCollectibles) {
        this.numberOfCollectibles = numberOfCollectibles;
    }

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
}
