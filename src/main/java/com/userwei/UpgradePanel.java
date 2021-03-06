package com.userwei;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class UpgradePanel extends JPanel implements KeyListener{
    JFrame mainFrame, upgradeScreen;
    
    Icon icon1, icon2;
    Map map;
    Description description;
    Material matericon1, matericon2, matericon3, matericon4, matericon5, matericon6;
    Material matericonw1, matericonw2, matericonw3, matericonw4, matericonw5, matericonw6;
    JButton weaponButton;

    static boolean materialChanged, Start;
    Thread thread;
    
    // {等級, 素材(1-5, coin), 持有(1-5, coin), 武器ATK}，共14項
    Font data[];
    int dataCount = 14;

    JButton button[];
    int buttonCount = 5;

    String material[] = {"coin", "herb", "iron", "wood"};
    String materialName[] = {"ironw", "woodw"};

    //Notation: 0: sword1, 1: sword2
    String name[] = {"sword1", "sword2"};
    String selectedWeapon;
    static Weapon weapon[];

    //正在查看的武器
    int weaponNumber;

    //正在使用的武器
    static int weaponSelecting = 0;
    int weaponCount = 2;
    int weaponAtk[][] = {
        {1, 2, 3, 4, 5},
        {3, 4, 6, 8, 10}
    };

    // 按鈕
    String buttonType[] = {"make_locked", "make", "upgrade_locked", "upgrade", "maxlevel"};

    // 位置相關變數
    int i = 36;
    int j = 77;
    int mx = 797;
    int my = 155;

    // [武器名(idx)][升級時的等級(0-4)][素材序][名稱及數量]
    String require[][][][] = {
        // sword1:
        {
            // lv0
            {
                {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"coin", "0"}
            },
            // lv1
            {
                {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"coin", "4"}
            },
            // lv2
            {
                {"wood", "2"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"coin", "10"}
            },
            // lv3
            {
                {"wood", "10"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"coin", "20"}
            },
            // lv4
            {
                {"wood", "20"}, {"iron", "5"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"coin", "80"}
            },
            // lv5
            {
                {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}
            }
        },
        // sword2:
        {
            // lv0
            {
                {"wood", "12"}, {"iron", "10"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"coin", "6"}
            },
            // lv1
            {
                {"wood", "15"}, {"iron", "15"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"coin", "6"}
            },
            // lv2
            {
                {"wood", "20"}, {"iron", "20"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"coin", "12"}
            },
            // lv3
            {
                {"wood", "30"}, {"iron", "25"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"coin", "24"}
            },
            // lv4
            {
                {"wood", "50"}, {"iron", "30"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"coin", "96"}
            },
            // lv5
            {
                {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}, {"null", "null"}
            }
        }
    };

    public int findIndex(String s){
        int idx;
        for(idx = 0; idx < weaponCount; idx ++){
            if(name[idx] == s) break;
        }
        return idx;
    }

    public void upgrade(){    
        BackpackPanel.addMaterialAmount("coin", -Integer.parseInt(require[weaponNumber][weapon[weaponNumber].level][5][1]));
        for(int i = 0; i < 5; i++){
            if(require[weaponNumber][weapon[weaponNumber].level][i][0] == "null"){
                break;
            }
            else{
                BackpackPanel.addMaterialAmount(require[weaponNumber][weapon[weaponNumber].level][i][0], - Integer.parseInt(require[weaponNumber][weapon[weaponNumber].level][i][1]));
            }
        }
        weapon[weaponNumber].level ++;
        weapon[weaponNumber].atk = weaponAtk[weaponNumber][weapon[weaponNumber].level - 1];
    }

    // 0 : "make_locked", 1 : "make", 2 : "upgrade_locked", 3 : "upgrade", 4 : "maxlevel"
    public int judge(){
        if(weapon[weaponNumber].level == 5){
            return 4;
        }
        else{
            if(BackpackPanel.getMaterialAmount("coin") < Integer.parseInt(require[weaponNumber][weapon[weaponNumber].level][5][1])){    
                if(weapon[weaponNumber].level == 0){
                    return 0;
                }
                else{
                    return 2;
                }
            }
            else{
                for(int i = 0; i < 5; i++){
                    if(require[weaponNumber][weapon[weaponNumber].level][i][0] == "null"){
                        if(weapon[weaponNumber].level == 0){
                            return 1;
                        }
                        else{
                            return 3;
                        }
                    }
                    else if(BackpackPanel.getMaterialAmount(require[weaponNumber][weapon[weaponNumber].level][i][0]) < Integer.parseInt(require[weaponNumber][weapon[weaponNumber].level][i][1])){
                        if(weapon[weaponNumber].level == 0){
                            return 0;
                        }
                        else{
                            return 2;
                        }
                    }
                }
            }
            if(weapon[weaponNumber].level == 0){
                return 1;
            }
            else{
                return 3;
            }
        }
    }

    public void change(int idx){
        description = new Description(650, 115, 500, 480, name[idx] + ".png");
        if(weapon[idx].level > 0){
            icon2 = new Icon(80 * (idx + 2) - 20, 160 - 20, 20, 20, "check.png");
            weaponSelecting = idx;
        }
        weaponNumber = idx;

        matericon1 = new Material(mx, my + i, 40, 40, 0, require[idx][weapon[idx].level][0][0] + ".png");
        matericon2 = new Material(mx, my + 2 * i, 40, 40, 0, require[idx][weapon[idx].level][1][0] + ".png");
        matericon3 = new Material(mx, my + 3 * i, 40, 40, 0, require[idx][weapon[idx].level][2][0] + ".png");
        matericon4 = new Material(mx, my + 4 * i, 40, 40, 0, require[idx][weapon[idx].level][3][0] + ".png");
        matericon5 = new Material(mx, my + 5 * i, 40, 40, 0, require[idx][weapon[idx].level][4][0] + ".png");
        matericonw1 = new Material(mx + j, my + i + 6, 50, 25, 0, require[idx][weapon[idx].level][0][0] + "w.png");
        matericonw2 = new Material(mx + j, my + 2 * i + 6, 50, 25, 0, require[idx][weapon[idx].level][1][0] + "w.png");
        matericonw3 = new Material(mx + j, my + 3 * i + 6, 50, 25, 0, require[idx][weapon[idx].level][2][0] + "w.png");
        matericonw4 = new Material(mx + j, my + 4 * i + 6, 50, 25, 0, require[idx][weapon[idx].level][3][0] + "w.png");
        matericonw5 = new Material(mx + j, my + 5 * i + 6, 50, 25, 0, require[idx][weapon[idx].level][4][0] + "w.png");
        
        Font font1 = new Font(mx + 182, my - i - 2, 160, 40, weapon[idx].level + ".png");
        data[0] = font1;

        for(int a = 1; a < 7; a++){
            Font font2 = new Font(mx + 68, my + a * i, 160, 40, require[idx][weapon[idx].level][a-1][1] + ".png");
            data[a] = font2;
        }

        for(int a = 7; a < 13; a++){
            Font font3;
            if(BackpackPanel.getMaterialAmount(require[weaponNumber][weapon[weaponNumber].level][a - 7][0]) >= 100){
                font3 = new Font(mx + 165, my + (a - 6) * i, 160, 40, "99+.png");
            }
            else if(BackpackPanel.getMaterialAmount(require[weaponNumber][weapon[weaponNumber].level][a - 7][0]) >= 0){
                font3 = new Font(mx + 165, my + (a - 6) * i, 160, 40, BackpackPanel.getMaterialAmount(require[weaponNumber][weapon[weaponNumber].level][a - 7][0]) + ".png");
            }
            else{
                font3 = new Font(mx + 165, my + (a - 6) * i, 160, 40, "null.png");
            }
            data[a] = font3;
        }

        Font font4 = new Font(mx - 3 * j + 23, my + 7 * i + 3, 160, 40, weapon[idx].atk + ".png");
        data[13] = font4;

        for(int a = 0; a < 5; a++){
            button[a].setVisible(false);
        }
        button[judge()].setVisible(true);  
    }

    public void addWeapon(int x, int y, int atk, int atkAddition, int level, String s){
        int idx = findIndex(s);
        Weapon nowWeapon = new Weapon(x * 80 + 10, y * 80 + 10, 60, 60, atk, atkAddition, level, s + ".png");
        weapon[idx] = nowWeapon;
        weaponButton = new JButton(new ImageIcon(UpgradePanel.class.getResource("Image/weapon/" + s + ".png")));
        weaponButton.setBounds(x * 80, y * 80, 80, 80);
        weaponButton.setFocusPainted(false);
        weaponButton.setBorderPainted(false);
        weaponButton.setBorder(null);
        weaponButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("Select.wav");
                    music.playOnce(350);
                    change(idx);      
                    selectedWeapon = s;
                    description = new Description(650, 115, 500, 480, s + ".png");
                    if(weapon[idx].level > 0){
                        icon2 = new Icon(80 * (idx + 2) - 20, 160 - 20, 20, 20, "check.png");
                        weaponSelecting = idx;
                    }
                    repaint();

                }catch(Exception e1){
                    e1.printStackTrace();
                }
            }
        });
        add(weaponButton);
    }

    public void init(){
        weapon = new Weapon[weaponCount];
        selectedWeapon = "select";

        icon1 = new Icon(10, 10, 60, 60, "arrow_up_white.png");
        icon2 = new Icon(160 - 20 , 160 - 20, 20, 20, "check.png");
        description = new Description(650, 115, 500, 480, selectedWeapon + ".png");
        map = new Map(0, 0, 1280, 720, "upgrade.png");
        
        matericon1 = new Material(0, 0, 40, 40, 0, "null.png");
        matericon2 = new Material(0, 0, 40, 40, 0, "null.png");
        matericon3 = new Material(0, 0, 40, 40, 0, "null.png");
        matericon4 = new Material(0, 0, 40, 40, 0, "null.png");
        matericon5 = new Material(0, 0, 40, 40, 0, "null.png");
        matericonw1 = new Material(0, 0, 40, 40, 0, "null.png");
        matericonw2 = new Material(0, 0, 40, 40, 0, "null.png");
        matericonw3 = new Material(0, 0, 40, 40, 0, "null.png");
        matericonw4 = new Material(0, 0, 40, 40, 0, "null.png");
        matericonw5 = new Material(0, 0, 40, 40, 0, "null.png");
        
        data = new Font[dataCount];
        for(int a = 0; a < 14; a++){
            Font font = new Font(mx + 6000, my + i, 160, 40, "0.png");
            data[a] = font;
        }
        
        button = new JButton[buttonCount];
        for(int i = 0; i < 5; i ++){
            JButton nowButton = new JButton(new ImageIcon(UpgradePanel.class.getResource("Image/upgrade/" + buttonType[i] + ".png")));
            nowButton.setBounds(808, 525, 183, 62);
            nowButton.setFocusPainted(false);
            nowButton.setBorderPainted(false);
            nowButton.setBorder(null);
            nowButton.setVisible(false);
            nowButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    try{
                        upgrade();
                        change(weaponNumber); 
                        Music music = new Music("Select.wav");
                        music.playOnce(350);
                        repaint();
                    }catch(Exception e1){
                        e1.printStackTrace();
                    }
                }
            });

            if(i == 0 || i == 2 || i == 4){
                nowButton.setEnabled(false);
            }
            button[i] = nowButton;
            add(nowButton);
        }

        addWeapon(1, 1, 1, 1, 1, "sword1");
        addWeapon(2, 1, 0, 3, 0, "sword2");
        
    }

    UpgradePanel(JFrame mainFrame, JFrame upgradeScreen){
        this.mainFrame = mainFrame;
        this.upgradeScreen = upgradeScreen;

        init();

        addKeyListener(this);
        setFocusable(true);

        thread = new Thread(() -> {
            while(true){
                update();

                try{
                    Thread.sleep(10);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    
    public void update(){
        if(materialChanged){
            materialChanged = false;
            change(weaponNumber);
            repaint();
        }
    }

    public void paintComponent(Graphics g){
        map.draw(g, this);
        icon1.draw(g, this);
        icon2.draw(g, this);
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
        /*
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
        */
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
