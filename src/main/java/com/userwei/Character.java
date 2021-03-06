package com.userwei;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Character extends Rectangle{
    Image pic;
    int movX, movY;

    Thread thread;

    Character(int x, int y, int mx, int my, int w, int h, String s){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.movX = mx;
        this.movY = my;

        try{
            // pic = Toolkit.getDefaultToolkit().createImage("StonePle/src/Image/character/" + s);
            java.net.URL imgURL = Character.class.getResource("Image/character/" + s);
            pic = new ImageIcon(imgURL).getImage();
        }catch(Exception e){
            e.printStackTrace();
        }

        thread = new Thread(() -> {
            while(true){
                update();

                try{
                    Thread.sleep(100);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void update(){
        if(ValueCalculate.characterLife <= 0 && GamePanel.nowState != 1){
            ValueCalculate.characterLife = ValueCalculate.characterValue[2][ValueCalculate.characterLevel] / 3;
            ValueCalculate.characterLifeChange = true;
            
            if(GamePanel.nowState == 3){
                FieldPanel.Start = false;
            }
            else if(GamePanel.nowState == 4){
                CavePanel.Start = false;
            }
            JFrame nowFrame = GamePanel.frame[GamePanel.nowState];
            JFrame preFrame = GamePanel.frame[1];
            GamePanel.Start = true;
            GamePanel.switchState(1);
            preFrame.setVisible(true);
            nowFrame.setVisible(false);

            FieldPanel.monsterReset();
            FieldPanel.mapState_i = 1;
            FieldPanel.mapState_j = 5;
            FieldPanel.characterInitX = 560;
            FieldPanel.characterInitY = 400;
            FieldPanel.reset();

            CavePanel.monsterReset();
            CavePanel.mapState_i = 2;
            CavePanel.mapState_j = 5;
            CavePanel.characterInitX = 640;
            CavePanel.characterInitY = 640;
            CavePanel.reset();
        }
    }

    public void draw(Graphics g, Component c){
        g.drawImage(pic, x, y, width, height, c);
    }
}
