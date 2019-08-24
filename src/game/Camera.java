package game;

import game.gameObjects.GameObject;

public class Camera {
    private float  x,y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject object){
        x+=((object.getX()-x)-GameConstants.GAME_SCREEN_WIDTH/4)*0.05f;
        y+=((object.getY()-y)-GameConstants.GAME_SCREEN_HEIGHT/2)*0.05f;

        if(x<=0) x=0;
        if(x>=GameConstants.GAME_WORLD_WIDTH-GameConstants.GAME_SCREEN_WIDTH/2)x=GameConstants.GAME_WORLD_WIDTH-GameConstants.GAME_SCREEN_WIDTH/2;
        if(y<=0)y=0;
        if(y>=GameConstants.GAME_WORLD_HEIGHT-GameConstants.GAME_SCREEN_HEIGHT)y=GameConstants.GAME_WORLD_HEIGHT-GameConstants.GAME_SCREEN_HEIGHT;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
