package game.gameObjects;

import game.ID;
import game.gameObjects.moveable.Tank;


import java.awt.*;
import java.util.LinkedList;

public class ObjectManager {
    private static LinkedList<GameObject> gameObjects =new LinkedList<GameObject>();
    private static int currentTank;
    private boolean gameOver=false;

    public void tick(){
        for(int i = 0; i< gameObjects.size(); i++){
            GameObject tempObject= gameObjects.get(i);
            if(tempObject.getId()== ID.Player)
                currentTank=1;
            if(tempObject.getId()==ID.Player2)
                currentTank=2;
            tempObject.tick();
        }
    }

    public void render(Graphics g){

        for(int i = 0; i< gameObjects.size(); i++){
            GameObject tempObject= gameObjects.get(i);
            tempObject.render(g);
        }
    }

    public void addObject(GameObject object){
        gameObjects.add(object);
    }

    public void removeObject(GameObject object){
        gameObjects.remove(object);
    }

    public static LinkedList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public static int getCurrentTank() {
        return currentTank;
    }

    public void setCurrentTank(int currentTank) {
        this.currentTank = currentTank;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
