package game.gameObjects.powerUps;

import game.ID;
import game.gameObjects.GameObject;
import game.gameObjects.ObjectManager;
import game.graphics.Resources;

import java.awt.*;

public class HealthPowerUp extends GameObject {
    public HealthPowerUp(int x, int y, ID id, ObjectManager objectManager) {
        super(x, y, id,objectManager);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
g.drawImage(Resources.healthPU,x,y,DEFAULT_WIDTH,DEFAULT_HEIGHT,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
}
