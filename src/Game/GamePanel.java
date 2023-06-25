package Game;


import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //Tile size variables
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;
    final int tileSize = originalTileSize * scale; //48x48 tile

    //Screen size and dimensions
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;  //768 pixels
    final int screenHeight = tileSize * maxScreenRow;  //576 pixels

    //KeyHandler and game thread variables
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread = null;

    //Player variables
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

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

        //Update player position based on the key input
        if(keyHandler.upPressed){
            playerY = playerY - playerSpeed;
        }

        if(keyHandler.downPressed){
            playerY = playerY + playerSpeed;
        }

        if(keyHandler.rightPressed){
            playerX = playerX + playerSpeed;
        }

        if(keyHandler.leftPressed){
            playerX = playerX - playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        //Fill a rectangle to represent the player
        g2d.fillRect(playerX, playerY, tileSize, tileSize);
        g2d.dispose();
    }
}
