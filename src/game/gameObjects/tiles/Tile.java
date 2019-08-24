package game.gameObjects.tiles;

import game.ID;
import game.gameObjects.GameObject;
import game.gameObjects.ObjectManager;

public abstract class Tile extends GameObject {
    public Tile(int x, int y, ID id, ObjectManager objectManager) {
        super(x, y, id,objectManager);
    }

    /**
     * Returns true if Tile is solid
     * @return
     */
    public abstract boolean isSolid();

    /**
     * Returns true if tile is breakable
     * @return
     */
    public abstract boolean isBreakable();
}
