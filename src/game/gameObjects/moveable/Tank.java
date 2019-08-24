package game.gameObjects.moveable;



import game.ID;
import game.audio.MusicPlayer;
import game.gameObjects.GameObject;
import game.gameObjects.ObjectManager;
import game.graphics.Resources;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject {

    private float angle;
    private boolean dead=false;
    private int movement;

    private int R;
    private final float ROTATIONSPEED = 3f;

    private int health;
    private int lives;

    //Power Ups
    private boolean isSpeedPowerUp;
    private int speedCounter;
    private boolean isBulletPowerUp;
    private int bulletCounter;



    private BufferedImage img;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;



    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, ID id,ObjectManager objectManager) {
        super(x,y,id,objectManager);
        velX = vx;
        velY = vy;
        this.img = img;
        this.R=4;
        this.angle = angle;
        this.health=100;
        this.lives=3;
        this.speedCounter=0;
        this.bulletCounter=0;

    }

    public void setX(int x){ this.x = x; }

    public void setY(int y) { this. y = y;}

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void toggleShootPressed() {
        this.shootPressed = true;
    }

    public void unToggleShootPressed() {
        this.shootPressed = false;
    }
    @Override
    public void tick() {
        if(isBulletPowerUp){
            if(bulletCounter==10){
                isBulletPowerUp=false;
                bulletCounter=0;
            }

        }
        if(isSpeedPowerUp){
            speedCounter++;
            if(speedCounter>=600){
                this.R=5;
                isSpeedPowerUp=false;
                speedCounter=0;
            }
        }


        if(health<=0){
            lives--;
            if(lives==0) {
                dead = true;
                objectManager.setGameOver(true);
                objectManager.removeObject(this);
            }
            health=100;
        }
        update();
        collision();
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.shootPressed){
            this.shoot();
        }
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        movement=0;
        velX = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        velY = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= velX;
        y -= velY;
    }

    private void moveForwards() {
        movement=1;
        velX = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        velY = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += velX;
        y += velY;
    }

    private void shoot() {

        new MusicPlayer("/music/Explosion_small.wav").start();

        if(isBulletPowerUp){
            objectManager.addObject(new Bullet(x,y,20,ID.Bullet,angle,objectManager.getCurrentTank(),objectManager));
            bulletCounter++;
        }
        else{
            objectManager.addObject(new Bullet(x,y,ID.Bullet,angle,objectManager.getCurrentTank(),objectManager));
        }
        unToggleShootPressed();
    }


    /**
     * Check and handle collisions
     */
    private void collision() {
        for (int i = 0; i < ObjectManager.getGameObjects().size(); i++) {
            GameObject tempObject = ObjectManager.getGameObjects().get(i);


            //If the tank being updated is player 1
            if (ObjectManager.getCurrentTank() == 1) {
                if (tempObject.getId() == ID.SolidTile || tempObject.getId() == ID.BoundaryTile || tempObject.getId() == ID.BreakableTile || tempObject.getId() == ID.Player2) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        if (movement == 1) {
                            x += velX * -1;
                            y += velY * -1;
                        } else if (movement == 0) {
                            x += velX * 1;
                            y += velY * 1;
                        }
                    }
                }
            }
            //If the tank being updated is player 2
            else if (ObjectManager.getCurrentTank() == 2) {
                if (tempObject.getId() == ID.SolidTile || tempObject.getId() == ID.BoundaryTile || tempObject.getId() == ID.BreakableTile || tempObject.getId() == ID.Player) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        if (movement == 1) {
                            x += velX * -1;
                            y += velY * -1;
                        } else if (movement == 0) {
                            x += velX * 1;
                            y += velY * 1;
                        }
                    }
                }
            }
            if(tempObject.getId()==ID.ShieldPowerUp){
                if(getBounds().intersects(tempObject.getBounds())){
                    health=200;
                    objectManager.removeObject(tempObject);
                }
            }

            if(tempObject.getId()==ID.BulletPowerUp) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    isBulletPowerUp = true;
                    objectManager.removeObject(tempObject);
                }
            }

            if(tempObject.getId()==ID.HealthPowerUp) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    lives++;
                    objectManager.removeObject(tempObject);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void render(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        renderHealth(g2d);
    }

    public void renderHealth(Graphics g){
        if(health>100){
            g.drawImage(Resources.barB100,x+64,y,40,15,null);
        }

        if(health<=100 && health>80){
            g.drawImage(Resources.barA100,x+64,y,40,15,null);
        }
        if(health<=80 && health>50){
            g.drawImage(Resources.barA80,x+64,y,40,15,null);
        }
        if(health<=50 && health>20){
            g.drawImage(Resources.barA50,x+64,y,40,15,null);
        }
        if(health<=20 && health>0){
            g.drawImage(Resources.barA20,x+64,y,40,15,null);
        }
        if(health<=0){
            g.drawImage(Resources.barA,x+64,y,40,15,null);
        }

        int temp=4;
        for(int i=1;i<=lives;i++){
            g.drawImage(Resources.healthPU,x+64+temp,y+15,15,15,null);
            temp+=15;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+2,y+4,60,54);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void damage(int damage){
        health-=damage;
    }

}
