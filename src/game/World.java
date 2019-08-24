package game;

import game.gameObjects.ObjectManager;
import game.gameObjects.powerUps.BulletPowerUp;
import game.gameObjects.powerUps.HealthPowerUp;
import game.gameObjects.powerUps.ShieldPowerUp;
import game.gameObjects.tiles.BoundaryTile;
import game.gameObjects.tiles.BreakableTile;
import game.gameObjects.tiles.SolidTile;

import java.awt.image.BufferedImage;

public class World {
    private BufferedImage image;
    private ObjectManager objectManager;
    private Game game;

    public World(BufferedImage image, ObjectManager objectManager,Game game) {
        this.image = image;
        this.objectManager = objectManager;
        this.game=game;
        loadWorld();
    }

    /**
     * Loading world
     */
    public void loadWorld(){
        int w=image.getWidth();
        int h=image.getHeight();


        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){

                int pixel=image.getRGB(x,y);
                int red=(pixel >> 16 ) & 0xff;
                int green=(pixel >> 8) & 0xff;
                int blue=(pixel) & 0xff;

                //Boundary Tile
                if(red==255 && green==0 && blue==0)
                    objectManager.addObject(new BoundaryTile(x*GameConstants.TILE_WIDTH,y*GameConstants.TILE_HEIGHT,ID.BoundaryTile,objectManager));

                //Breakable Tile
                if(red==255 && green==255 && blue==0) {
                    objectManager.addObject(new BreakableTile(x * GameConstants.TILE_WIDTH, y * GameConstants.TILE_HEIGHT, ID.BreakableTile, objectManager));
                }
                //Solid Tile
                if(red==0 && green==0 && blue==255)
                    objectManager.addObject(new SolidTile(x*GameConstants.TILE_WIDTH,y*GameConstants.TILE_HEIGHT,ID.SolidTile,objectManager));

                //Health PowerUp
                if(red==0 && green==128 && blue==0) {
                    objectManager.addObject(new HealthPowerUp(x * GameConstants.TILE_WIDTH, y * GameConstants.TILE_HEIGHT, ID.HealthPowerUp,objectManager));
                }

                //Shield Powerup
                if(red==255 && green==255 && blue==255) {
                    objectManager.addObject(new ShieldPowerUp(x * GameConstants.TILE_WIDTH, y * GameConstants.TILE_HEIGHT, ID.ShieldPowerUp,objectManager));
                }
                //Bullet Power Up
                if(red==128 && green==0 && blue==128) {
                    objectManager.addObject(new BulletPowerUp(x * GameConstants.TILE_WIDTH, y * GameConstants.TILE_HEIGHT, ID.BulletPowerUp,objectManager));
                }

                //Player 1
                if(red==100 && green==100 && blue==100) {
                    game.setPlayer1SpawnX(x*GameConstants.TILE_WIDTH);
                    game.setPlayer1SpawnY(y*GameConstants.TILE_HEIGHT);
                }

                //Player 2
                if(red==200 && green==200 && blue==200) {
                    game.setPlayer2SpawnX(x*GameConstants.TILE_WIDTH);
                    game.setPlayer2SpawnY(y*GameConstants.TILE_HEIGHT);
                }
            }
        }
    }
}
