package game;


import player.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //Tile size variables
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48x48 tile

    //Screen size and dimensions
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;  //768 pixels
    final int screenHeight = tileSize * maxScreenRow;  //576 pixels

    //KeyHandler and game thread variables
    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player(this, keyHandler);
    Thread gameThread = null;


    ///Frames Per Second
    int FPS = 60;

    public GamePanel(){
        //Set panel properties
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.addKeyListener(keyHandler);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        double drawInterval = 1000000000/FPS;  //0.0166 seconds
        double nextDrawTime = System.nanoTime();

        while(gameThread != null){

            //UPDATE: update information such as character positions
            update();
            //DRAW: draw the screen with the updated information
            repaint();


            try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;  //Converting nano time to milliseconds

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        player.draw(g2d);
    }
}
