package com.userwei;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Main{
    static boolean loading;
    public static void main(String[] args){
        // 0 Start Panel; 1 Main Panel; 2 Pause Panel; 3 Field Panel; 4 CavePanel; 5 Instruction Panel; 6 Upgrade Panel
        JFrame blankFrame = new JFrame("StonePle version 0.2(beta)");

        JFrame mainFrame = new JFrame("StonePle version 0.2(beta)");
        JFrame startScreen = new JFrame("StonePle version 0.2(beta)");
        JFrame pauseScreen = new JFrame("StonePle version 0.2(beta)");
        JFrame fieldScreen = new JFrame("StonePle version 0.2(beta)");
        JFrame caveScreen = new JFrame("StonePle version 0.2(beta)");
        JFrame instructionScreen = new JFrame("StonePle version 0.2(beta)");
        JFrame upgradeScreen = new JFrame("StonePle version 0.2(beta)");

        GamePanel gamePanel = new GamePanel(startScreen, mainFrame, pauseScreen, fieldScreen, caveScreen, instructionScreen, upgradeScreen);
        StartPanel startPanel = new StartPanel(mainFrame, startScreen);
        PausePanel pausePanel = new PausePanel(pauseScreen);
        FieldPanel fieldPanel = new FieldPanel(mainFrame, pauseScreen, fieldScreen, caveScreen);
        CavePanel cavePanel = new CavePanel(mainFrame, pauseScreen, fieldScreen, caveScreen);
        InstructionPanel instructionPanel = new InstructionPanel(mainFrame, instructionScreen);
        UpgradePanel upgradePanel = new UpgradePanel(mainFrame, upgradeScreen);

        int size_x = 1280, size_y = 748;
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int loc_x = (int) screensize.getWidth() / 2 - size_x / 2;
        int loc_y = (int) screensize.getHeight() / 2 - size_y / 2;
        
        mainFrame.getContentPane().add(gamePanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(size_x, size_y);
        mainFrame.setResizable(false);
        mainFrame.setLocation(loc_x, loc_y);
        mainFrame.setVisible(false);
        mainFrame.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                mainFrame.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        JButton pauseButton1 = new JButton(new ImageIcon(Main.class.getResource("Image/icon/undo.png")));
        pauseButton1.setBounds(0, 0, 80, 80);
        pausePanel.setLayout(null);
        pauseButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                PausePanel.Start = false;
                if(GamePanel.lastState == 1){
                    GamePanel.Start = true;
                }
                else if(GamePanel.lastState == 3){
                    FieldPanel.Start = true;
                }
                else if(GamePanel.lastState == 4){
                    CavePanel.Start = true;
                }
                JFrame nowFrame = GamePanel.frame[GamePanel.lastState];
                GamePanel.switchState(GamePanel.lastState);
                nowFrame.setVisible(true);
                pauseScreen.setVisible(false);
            }
        });
        pausePanel.add(pauseButton1);

        pauseScreen.getContentPane().add(pausePanel);
        pauseScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pauseScreen.setSize(size_x, size_y);
        pauseScreen.setResizable(false);
        pauseScreen.setLocation(loc_x, loc_y); 
        pauseScreen.setVisible(false);
        pauseScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                pauseScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        fieldScreen.getContentPane().add(fieldPanel);
        fieldScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fieldScreen.setResizable(false);
        fieldScreen.setLocation(loc_x, loc_y);
        fieldScreen.setSize(size_x, size_y);
        fieldScreen.setVisible(false);
        fieldScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                fieldScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        caveScreen.getContentPane().add(cavePanel);
        caveScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        caveScreen.setResizable(false);
        caveScreen.setLocation(loc_x, loc_y);
        caveScreen.setSize(size_x, size_y);
        caveScreen.setVisible(false);
        caveScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                caveScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        JButton instructionButton1 = new JButton(new ImageIcon(Main.class.getResource("Image/icon/cross.png")));
        instructionButton1.setBounds(1200, 0, 80, 80);
        instructionPanel.setLayout(null);
        instructionButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                InstructionPanel.Start = false;
                GamePanel.Start = true;
                GamePanel.switchState(1);
                mainFrame.setVisible(true);
                instructionScreen.setVisible(false);
            }
        });
        instructionPanel.add(instructionButton1);
       
        instructionScreen.getContentPane().add(instructionPanel);
        instructionScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructionScreen.setResizable(false);
        instructionScreen.setLocation(loc_x, loc_y);
        instructionScreen.setSize(size_x, size_y);
        instructionScreen.setVisible(false);
        instructionScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                instructionScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        JButton upgradeButton1 = new JButton(new ImageIcon(Main.class.getResource("Image/icon/cross.png")));
        upgradeButton1.setBounds(1200, 0, 80, 80);
        upgradePanel.setLayout(null);
        upgradeButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Music music = new Music("select.wav");
                    music.playOnce();
                }catch(Exception e1){
                    e1.printStackTrace();
                }
                UpgradePanel.Start = false;
                GamePanel.Start = true;
                GamePanel.switchState(1);
                mainFrame.setVisible(true);
                upgradeScreen.setVisible(false);
            }
        });
        upgradePanel.add(upgradeButton1);
       
        upgradeScreen.getContentPane().add(upgradePanel);
        upgradeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        upgradeScreen.setResizable(false);
        upgradeScreen.setLocation(loc_x, loc_y);
        upgradeScreen.setSize(size_x, size_y);
        upgradeScreen.setVisible(false);
        upgradeScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                upgradeScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        // For Cover Loaded Panel
        blankFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        blankFrame.setSize(size_x, size_y);
        blankFrame.setResizable(false);
        blankFrame.setLocation(loc_x, loc_y);
        blankFrame.setVisible(true);
        blankFrame.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                blankFrame.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        // Load Panel
        loading = true;
        pauseScreen.setVisible(true);
        instructionScreen.setVisible(true);
        upgradeScreen.setVisible(true);

        startScreen.getContentPane().add(startPanel);
        startScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startScreen.setSize(size_x, size_y);
        startScreen.setResizable(false);
        startScreen.setLocation(loc_x, loc_y);
        startScreen.setVisible(true);
        startScreen.addComponentListener(new ComponentListener(){
            @Override
            public void componentHidden(ComponentEvent e) {
                
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                startScreen.setLocation(loc_x, loc_y);
            }
            @Override
            public void componentResized(ComponentEvent e) {
                
            }
            @Override
            public void componentShown(ComponentEvent e) {
                
            }
        });

        // Close Loaded Panel
        try{
            Thread.sleep(100);
            pauseScreen.setVisible(false);
            instructionScreen.setVisible(false);
            upgradeScreen.setVisible(false);
            blankFrame.setVisible(false);
            loading = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
