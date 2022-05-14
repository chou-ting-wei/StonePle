package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.ImageIcon;

/*
    會用到的東西

    素材列表：{"coin", "herb", "iron", "wood"}

    取得素材數量：
    int XXXAmount = BackpackPanel.getMaterialAmount("XXX");

    更改素材數量：
    BackpackPanel.addMaterialAmount("XXX", 數量);
        % 減少數量直接加負的
*/

public class UpgradePanel extends JPanel implements KeyListener{
    JFrame mainFrame, upgradeScreen;
    
    Icon icon1;
    Icon equip;
    Map map;
    Description description;
    MaterIcon matericon1, matericon2, matericon3, matericon4, matericon5, matericon6;
    MaterIcon matericonw1, matericonw2, matericonw3, matericonw4, matericonw5, matericonw6;
    JButton weaponbutton;
    JButton upgrade;
    String buttontype[] = {"make_locked", "make", "upgrade_locked", "upgrade", "maxlevel"};
    int nowbuttontype = 0;
    
    //{等級, 素材(1-5, coin), 持有(1-5, coin), 武器ATK}，共14項
    Font data[];
    int dataCount = 14;

    String material[] = {"coin", "herb", "iron", "wood"};
    String materialw[] = {"ironw", "woodw"};

    //0: sword1, 1: sword2

    String name[] = {"sword1", "sword2"};
    int weaponlevel[] = {1, 0};
    String nowweapon = "select";
    Weapon nowWeapon;
    Weapon weapon[];
    int weaponCount = 2;
    int weaponAtk[][] = {
        {1, 2, 3, 4, 5},
        {3, 4, 6, 8, 10}
    };

    //[武器名(idx)][升級時的等級(0-4)][素材序][名稱及數量]
    String require[][][][] = {
        //sword1:
        {

            //lv0
            {
            {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "0"}
            },

            //lv1
            {
            {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "4"}
            },

            //lv2
            {
            {"wood", "2"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "10"}
            },
            
            //lv3
            {
            {"wood", "10"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "20"}
            },

            //lv4
            {
            {"wood", "20"}, {"iron", "5"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "80"}
            },

            //lv5
            {
            {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}
            }

        },

        //sword2:
        {

            //lv0
            {
            {"wood", "12"}, {"iron", "10"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "6"}
            },

            //lv1
            {
            {"wood", "15"}, {"iron", "15"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "6"}
            },

            //lv2
            {
            {"wood", "20"}, {"iron", "20"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "12"}
            },
            
            //lv3
            {
            {"wood", "30"}, {"iron", "25"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "24"}
            },

            //lv4
            {
            {"wood", "50"}, {"iron", "30"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "96"}
            },

            //lv5
            {
            {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}
            }
        }

    };

    //位置相關變數
    int i = 36;
    int j = 77;
    int mx = 797;
    int my = 155;

    static boolean Start;


    int findIndex(String s){
        int idx;
        for(idx = 0; idx < weaponCount; idx ++){
            if(name[idx] == s) break;
        }
        return idx;
    }

    void addWeapon(int x, int y, int atk, int level, String s){
        int idx = findIndex(s);
        Weapon nowWeapon = new Weapon(x * 80 + 10, y * 80 + 10, 60, 60, atk, level, s + ".png");
        weapon[idx] = nowWeapon;
        JButton weaponbutton = new JButton(new ImageIcon(Main.class.getResource("Image/weapon/" + s + ".png")));
        weaponbutton.setBounds(x * 80, y * 80, 80, 80);
        weaponbutton.setFocusPainted(false);
        weaponbutton.setBorderPainted(false);
        weaponbutton.setBorder(null);
        weaponbutton.setLayout(null);
        weaponbutton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                    nowweapon = s;
                    description = new Description(650, 115, 500, 480, s + ".png");
                    if(weapon[idx].level > 0){
                        equip = new Icon(80 + 12 + 80 * idx, 160 + 10, 56, 20, "equip.png");
                    }
                    matericon1 = new MaterIcon(mx, my + i, 40, 40, require[idx][weapon[idx].level][0][0] + ".png");
                    matericon2 = new MaterIcon(mx, my + 2 * i, 40, 40, require[idx][weapon[idx].level][1][0] + ".png");
                    matericon3 = new MaterIcon(mx, my + 3 * i, 40, 40, require[idx][weapon[idx].level][2][0] + ".png");
                    matericon4 = new MaterIcon(mx, my + 4 * i, 40, 40, require[idx][weapon[idx].level][3][0] + ".png");
                    matericon5 = new MaterIcon(mx, my + 5 * i, 40, 40, require[idx][weapon[idx].level][4][0] + ".png");
                    matericonw1 = new MaterIcon(mx + j, my + i + 6, 50, 25, require[idx][weapon[idx].level][0][0] + "w" + ".png");
                    matericonw2 = new MaterIcon(mx + j, my + 2 * i + 6, 50, 25, require[idx][weapon[idx].level][1][0] + "w" + ".png");
                    matericonw3 = new MaterIcon(mx + j, my + 3 * i + 6, 50, 25, require[idx][weapon[idx].level][2][0] + "w" + ".png");
                    matericonw4 = new MaterIcon(mx + j, my + 4 * i + 6, 50, 25, require[idx][weapon[idx].level][3][0] + "w" + ".png");
                    matericonw5 = new MaterIcon(mx + j, my + 5 * i + 6, 50, 25, require[idx][weapon[idx].level][4][0] + "w" + ".png");
                    
                    Font font1 = new Font(mx + 182, my - i - 2, 160, 40, weapon[idx].level + ".png");
                    data[0] = font1;

                    for(int a = 1; a < 7; a++){
                        Font font2 = new Font(mx + 68, my + a * i, 160, 40, require[idx][weapon[idx].level][a-1][1] + ".png");
                        data[a] = font2;
                    }

                    for(int a = 7; a < 13; a++){
                        try{
                            Font font3 = new Font(mx + 165, my + (a - 6) * i, 160, 40, BackpackPanel.getMaterialAmount(require[idx][weapon[idx].level][a - 7][0]) + ".png");
                            data[a] = font3;
                        }
                        catch(Exception e1){
                            Font font3 = new Font(mx + 165, my + (a - 6) * i, 160, 40, "null.png");
                            data[a] = font3;
                            e1.printStackTrace();};
                    }

                    Font font4 = new Font(mx - 3 * j + 18, my + 7 * i + 4, 160, 40, weapon[idx].atk + ".png");
                    data[13] = font4;
                    
                    repaint();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        });
        add(weaponbutton);
    }

    void init(){
        icon1 = new Icon(10, 10, 60, 60, "arrow_up_white.png");
        equip = new Icon(80 + 12, 160 + 10, 56, 20, "equip.png");
        description = new Description(650, 115, 500, 480, nowweapon + ".png");
        map = new Map(0, 0, 1280, 720, "upgrade.png");
        matericon1 = new MaterIcon(6000, 400, 40, 40, "wood.png");
        matericon2 = new MaterIcon(6000, 400, 40, 40, "wood.png");
        matericon3 = new MaterIcon(6000, 400, 40, 40, "wood.png");
        matericon4 = new MaterIcon(6000, 400, 40, 40, "wood.png");
        matericon5 = new MaterIcon(6000, 400, 40, 40, "wood.png");
        matericonw1 = new MaterIcon(6000, 400, 40, 40, "wood.png");
        matericonw2 = new MaterIcon(6000, 400, 40, 40, "wood.png");
        matericonw3 = new MaterIcon(6000, 400, 40, 40, "wood.png");
        matericonw4 = new MaterIcon(6000, 400, 40, 40, "wood.png");
        matericonw5 = new MaterIcon(6000, 400, 40, 40, "wood.png");
        data = new Font[dataCount];
        for(int a = 0;a < 14; a++){
            Font font = new Font(mx + 6000, my + i, 160, 40, "2.png");
            data[a] = font;
        }

        weapon = new Weapon[weaponCount];

        addWeapon(1, 1, 1, 1, "sword1");
        addWeapon(2, 1, 0, 0, "sword2");
        
        JButton upgrade = new JButton(new ImageIcon(Main.class.getResource("Image/upgrade/" + buttontype[0] + ".png")));
        upgrade.setBounds(808, 525, 183, 62);
        upgrade.setFocusPainted(false);
        upgrade.setBorderPainted(false);
        upgrade.setBorder(null);
        upgrade.setLayout(null);
        upgrade.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce();
                    repaint();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        });
        add(upgrade);
    }

    UpgradePanel(JFrame mainFrame, JFrame upgradeScreen){
        this.mainFrame = mainFrame;
        this.upgradeScreen = upgradeScreen;
        nowweapon = "select";

        addKeyListener(this);
        setFocusable(true);

        init();
        
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        icon1.draw(g, this);
        equip.draw(g, this);
        description.draw(g, this);
        matericon1.draw(g, this);
        matericon2.draw(g, this);
        matericon3.draw(g, this);
        matericon4.draw(g, this);
        matericon5.draw(g, this);
        matericonw1.draw(g, this);
        matericonw2.draw(g, this);
        matericonw3.draw(g, this);
        matericonw4.draw(g, this);
        matericonw5.draw(g, this);
        for(int i = 0; i < dataCount; i ++){
            data[i].draw(g, this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT){
            System.out.println("-");
            System.out.println("Game State:");
            System.out.println(GamePanel.lastState + " " + GamePanel.nowState);

            System.out.println("Music State:");
            for(int i = 0; i < GamePanel.frameSize - 1; i ++){
                System.out.print(GamePanel.musicSwitched[i] + " ");
            }
            System.out.println(GamePanel.musicSwitched[GamePanel.frameSize - 1]);
            
            System.out.println("Start State:");
            System.out.println(StartPanel.Start + " " + GamePanel.Start + " " + PausePanel.Start + " " + FieldPanel.Start + " " + CavePanel.Start + " " + InstructionPanel.Start + " " + UpgradePanel.Start);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
