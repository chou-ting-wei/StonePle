package com.userwei;
import java.awt.*;
import javax.swing.ImageIcon;

public class Background extends Rectangle{
    Image pic;
    boolean destroyed;
    String name;

    Background(int x, int y, int w, int h, String s){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        name = s;

        try{
            // pic = Toolkit.getDefaultToolkit().createImage("StonePle/src/Image/background/" + s);
            java.net.URL imgURL = Background.class.getResource("Image/background/" + s);
            pic = new ImageIcon(imgURL).getImage();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Component c){
        if(!destroyed){
            g.drawImage(pic, x, y, width, height, c);
        }
    }
}
