package game.gameObjects;

import game.Game;
import game.GameConstants;
import game.ID;

import java.awt.*;

public  abstract class GameObject {

    protected static final int DEFAULT_WIDTH = GameConstants.TILE_WIDTH;
    protected static final int DEFAULT_HEIGHT= GameConstants.TILE_WIDTH;
    protected int x,y;
    protected float velX=0,velY=0;
    protected ID id;
    protected ObjectManager objectManager;


    public GameObject (int x,int y,ID id,ObjectManager objectManager){
        this.x=x;
        this.y=y;
        this.id=id;
        this.objectManager=objectManager;
    }


    /**
     * Method to update gameObjects every loop
     */
    public abstract void tick();


    /**
     * Method to draw objects every loop
     */
    public abstract void render(Graphics g);

    /**
     * Generate collision bounds for object
     */
    public abstract Rectangle getBounds();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
