package game.menus;


import game.GameConstants;
import game.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private Launcher lf;

    public StartMenuPanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("textures/title.png"));
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }

        this.setLayout(null);

        start = new JButton("Start");
        start.setFont(new Font("Courier New", Font.BOLD ,24));
        start.setBounds(GameConstants.START_MENU_SCREEN_WIDTH/2-(75),400,150,50);
        start.addActionListener((actionEvent -> {
            this.lf.setFrame("game");
        }));


        exit = new JButton("Exit");
        exit.setSize(new Dimension(200,100));
        exit.setFont(new Font("Courier New", Font.BOLD ,24));
        exit.setBounds(GameConstants.START_MENU_SCREEN_WIDTH/2-(75),500,150,50);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));


        this.add(start);
        this.add(exit);

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, GameConstants.START_MENU_SCREEN_WIDTH,GameConstants.START_MENU_SCREEN_HEIGHT);
        g2.drawImage(this.menuBackground,GameConstants.START_MENU_SCREEN_WIDTH/2-this.menuBackground.getWidth()/2,0,null);

        int fontSize = 16;

        g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));

        g2.setColor(Color.WHITE);
        g2.drawString("Player 1: UP -> W , DOWN-> S , LEFT -> A , RIGHT -> D , SHOOT -> SHIFT",100,320);
        g2.drawString("Player 2: UP -> ArrowUp , DOWN-> ArrowDown , LEFT -> ArrowLeft , RIGHT -> ArrowRight , SHOOT -> J",100,360);
    }
}
