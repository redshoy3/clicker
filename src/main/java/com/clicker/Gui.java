package com.clicker;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

class Gui{
    static JPanel panel;
    static JPanel clicksPanel;
    static Map<String, JButton> buttonMap;
    private static final Player PLAYER = App.getPlayer(); 
    private static final BuildingMatrix B_MATRIX = App.getBMatrix();
    public static void main(String args[]){
       JFrame frame = new JFrame("Harold Pants: Pro Skater");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(300,300);

       panel = new JPanel(new GridLayout(10, 1));
       clicksPanel = new JPanel(new GridLayout(1,2));

       buttonMap = new HashMap<>();
       Map<String, JLabel> labelMap = new HashMap<>();

       JLabel playerClicksLabel = new JLabel(Long.toString(PLAYER.getClicks()));
       JButton clickButton = new JButton("Jam on 'em");

       clickButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            PLAYER.addClicks(10);
            playerClicksLabel.setText(Long.toString(PLAYER.getClicks()));
        }});

        clicksPanel.add(clickButton);

       for (String buildingName : B_MATRIX.getMatrix().keySet()) {
            labelMap.put(buildingName, new JLabel(String.valueOf(B_MATRIX.getQuantity(buildingName))));
            buttonMap.put(buildingName, new JButton(String.format("Purchase %s: %s", buildingName, B_MATRIX.getNextPurchaseCost(buildingName))));
            JButton button = buttonMap.get(buildingName);
            JPanel innerPanel = new JPanel(new GridLayout(1,2));
            button.setSize(80, 80);
            button.setActionCommand(buildingName);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //clean this up, make the gui update into a method.
                    String buttonName = e.getActionCommand();
                    PLAYER.purchase(buttonName);

                }
            });

            innerPanel.add(labelMap.get(buildingName));
            innerPanel.add(button);
            panel.add(innerPanel);
            

        }
       
       JPanel outerPanel = new JPanel(new GridLayout(2,1));
       clicksPanel.add(playerClicksLabel);
       outerPanel.add(clicksPanel);
       outerPanel.add(panel);
       frame.add(outerPanel);
       frame.pack();
       frame.setVisible(true);

       Timer appTimer = new Timer();
       appTimer.scheduleAtFixedRate(new TimerTask() {
           @Override
           public void run() {
               App.update();
               
           };     
       }, 0, 1000);

       //appThread.start();

       Timer guiTimer = new Timer();
       guiTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                playerClicksLabel.setText(Long.toString(PLAYER.getClicks()));
                updateClickableUpgrades();
                for (String buildingName : B_MATRIX.getMatrix().keySet()) {
                labelMap.get(buildingName).setText(Integer.toString(B_MATRIX.getQuantity(buildingName)));
                buttonMap.get(buildingName).setText(String.format("Purchase %s: %s",
                                             buildingName, B_MATRIX.getNextPurchaseCost(buildingName)));
                playerClicksLabel.setText(Long.toString(PLAYER.getClicks()));
                }
            }
        
    }, 0, 10);

        
    }


    public static void update() {
        App.update();
    }

    private static void updateClickableUpgrades() {
        for (String s : buttonMap.keySet()) {
            if (PLAYER.getClicks() >= B_MATRIX.getNextPurchaseCost(s)) {
                buttonMap.get(s).setEnabled(true);
            }
            else {buttonMap.get(s).setEnabled(false);}
        }
    }
}