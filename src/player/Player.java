package player;

import game.GamePanel;
import game.KeyHandler;

import java.awt.*;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setDefaultValues();
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update(){
        //Update player position based on the key input
        if(keyHandler.upPressed){
            y = y - speed;
        }

        if(keyHandler.downPressed){
            y = y + speed;
        }

        if(keyHandler.rightPressed){
            x = x + speed;
        }

        if(keyHandler.leftPressed){
            x = x - speed;
        }
    }

    public void draw(Graphics2D g2d){
        g2d.fillRect(x,y,gamePanel.tileSize,gamePanel.tileSize);
        g2d.dispose();
    }
}
