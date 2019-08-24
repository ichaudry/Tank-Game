/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;


import game.audio.MusicPlayer;
import game.control.TankControl;
import game.gameObjects.ObjectManager;
import game.gameObjects.moveable.Tank;
import game.graphics.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


/**
 *
 * @author anthony-pc
 */
public class Game extends JPanel implements Runnable {

    private BufferedImage world;

    private Launcher lf;

    //Game World
    private World gameWorld;


    //Players
    private Tank player1;
    private Tank player2;

    //Players Spawn Loccation
    private int player1SpawnX;
    private int player1SpawnY;
    private int player2SpawnX;
    private int player2SpawnY;


    //Camera
    private Camera camera;
    private Camera camera2;

    //Object Manager for updating and drawing
    private ObjectManager objectManager;

    //Tank Controller
    private TankControl tc1;
    private TankControl tc2;


    public Game(Launcher lf){
        this.lf = lf;
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.GAME_WORLD_WIDTH,
                GameConstants.GAME_WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Resources.init();
        initObjects();
        this.setBackground(Color.BLACK);
    }

    /**
     * Initializes all objects and adds them to object manager
     */
    public void initObjects(){
        objectManager=new ObjectManager();
        camera=new Camera(0,0);
        camera2=new Camera(GameConstants.GAME_WORLD_WIDTH-GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_WORLD_HEIGHT-GameConstants.GAME_SCREEN_HEIGHT);
        gameWorld=new World(Resources.world3,objectManager,this);

        player1 = new Tank(player1SpawnX,player1SpawnY, 100, 0, 90, Resources.player1,ID.Player,objectManager);
        tc1 = new TankControl(player1,KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SHIFT);
        objectManager.addObject(player1);

        player2 = new Tank(player2SpawnX,player2SpawnY, 100, 0, -120, Resources.player2,ID.Player2,objectManager);
        tc2 = new TankControl(player2,  KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_J);
        objectManager.addObject(player2);

        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }

    /**
     * Game loop
     */
    @Override
    public void run(){
        MusicPlayer player=new MusicPlayer("/music/Game_Music.wav");
        player.start();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while (true) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            this.repaint();
            if(System.currentTimeMillis()-timer>1000){
                timer+=1000;
            }

            if(this.player1.isDead()||this.player2.isDead() ){

                this.lf.setFrame("end");
                return;
            }
        }
    }

    public void tick(){
        for(int i = 0; i<objectManager.getGameObjects().size(); i++){
            if(objectManager.getGameObjects().get(i).getId()==ID.Player){
                camera.tick(objectManager.getGameObjects().get(i));
            }
            if(objectManager.getGameObjects().get(i).getId()==ID.Player2){
                camera2.tick(objectManager.getGameObjects().get(i));
            }
        }
        objectManager.tick();
    }

    public void render(Graphics g){
        objectManager.render(g);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();

        //Drawing Background
        for(int y=0;y<11;y++) {
            for (int x = 0; x < 10; x++) {
                buffer.drawImage(Resources.background, x*Resources.background.getWidth(), y*Resources.background.getHeight(), Resources.background.getWidth(), Resources.background.getHeight(), null);
            }
        }

        this.render(buffer);

        BufferedImage world1=world.getSubimage((int)camera.getX(),(int)camera.getY(),640,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage world2=world.getSubimage((int)camera2.getX(),(int)camera2.getY(),640,GameConstants.GAME_SCREEN_HEIGHT);

        g2.drawImage(world1,0,0,null);
        g2.drawImage(world2,640,0,null);

        g2.setColor(Color.white);
        g2.fillRect(637,0,6,GameConstants.GAME_SCREEN_HEIGHT);

        g2.drawImage(world,540, 530,200,200,null);

    }
    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    public void setPlayer1SpawnX(int player1SpawnX) {
        this.player1SpawnX = player1SpawnX;
    }

    public void setPlayer1SpawnY(int player1SpawnY) {
        this.player1SpawnY = player1SpawnY;
    }

    public void setPlayer2SpawnX(int player2SpawnX) {
        this.player2SpawnX = player2SpawnX;
    }

    public void setPlayer2SpawnY(int player2SpawnY) {
        this.player2SpawnY = player2SpawnY;
    }
}

