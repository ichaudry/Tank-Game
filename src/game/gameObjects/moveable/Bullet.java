package game.gameObjects.moveable;

import game.ID;
import game.audio.MusicPlayer;
import game.gameObjects.GameObject;
import game.gameObjects.ObjectManager;
import game.graphics.Resources;

import java.awt.*;


public class Bullet extends GameObject {
    private static final int DEFAULT_DAMAGE=5;

    private float angle;
    private final int R = 10;
    private int damage;

    //To Track which player bullet belongs to
    private int player;

    //Counter to destroy bullet after certain amount of ticks
    private int counter;


    /**
     * Constructor used with default value for damage integer
     * @param x
     * @param y
     * @param id
     * @param angle
     * @param player
     * @param objectManager
     */
    public Bullet(int x, int y, ID id,float angle,int player,ObjectManager objectManager) {
        super(x, y, id,objectManager);
        this.angle=angle;
        this.damage=DEFAULT_DAMAGE;
        this.player=player;
        counter=0;
    }

    /**
     * Constructor used to create bullets with custom value for damage
     * @param x
     * @param y
     * @param damage
     * @param id
     * @param angle
     * @param player
     * @param objectManager
     */
    public Bullet(int x, int y,int damage, ID id,float angle,int player,ObjectManager objectManager) {
        super(x, y, id,objectManager);
        this.angle=angle;
        this.damage=damage;
        this.player=player;
        counter=0;
    }


    @Override
    public void tick() {
        //This block manages destruction of bullet after certain number of ticks
        counter++;
        if(counter>=45){
            objectManager.removeObject(this);
        }

        velX = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        velY = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += velX;
        y += velY;

        collision();
    }


    /**
     * Handling collisions
     */
    public void collision(){
        for(int i=0;i<objectManager.getGameObjects().size();i++){
            GameObject tempObject=objectManager.getGameObjects().get(i);
            //If the tank being updated is player 1
            if (player == 1) {
                if (tempObject.getId() == ID.SolidTile || tempObject.getId() == ID.BoundaryTile ) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        objectManager.removeObject(this);
                    }
                }
                if (tempObject.getId() == ID.BreakableTile) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        if(damage==20) {
                            objectManager.removeObject(tempObject);
                            tick();
                        }
                        else{
                            objectManager.removeObject(tempObject);
                            objectManager.removeObject(this);
                        }
                    }
                }
                if (tempObject.getId() == ID.Player2) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        new MusicPlayer("/music/Explosion_large.wav").start();
                        Tank tempTank=(Tank)tempObject;
                        tempTank.damage(damage);
                        objectManager.removeObject(this);
                    }
                }
            }
            //If the tank being updated is player 2
            else if (player==2) {
                if (tempObject.getId() == ID.SolidTile || tempObject.getId() == ID.BoundaryTile ) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        objectManager.removeObject(this);
                    }
                }
                if (tempObject.getId() == ID.BreakableTile) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        if(damage==20) {
                            objectManager.removeObject(tempObject);
                            tick();
                        }
                        else{
                            objectManager.removeObject(tempObject);
                            objectManager.removeObject(this);
                        }
                    }
                }
                if (tempObject.getId() == ID.Player) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        new MusicPlayer("/music/Explosion_large.wav").start();
                        Tank tempTank=(Tank)tempObject;
                        tempTank.damage(damage);
                        objectManager.removeObject(this);
                    }
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Resources.bullet,x,y,null);
        g.setColor(Color.black);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+25,y+25,15,15);
    }
}
