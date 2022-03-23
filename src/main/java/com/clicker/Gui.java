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

    //THIS IS WHERE THE MAIN BUTTON IS DEFINED
       JLabel playerClicksLabel = new JLabel(
                        String.format("Clicks produced: %s", Long.toString(PLAYER.getClicks()/10)));
       JButton clickButton = new JButton("Jam on 'em");

       clickButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            PLAYER.clickButton();
            playerClicksLabel.setText(String.format("Clicks produced: %s", Long.toString(PLAYER.getClicks()/10)));
            clickButton.setToolTipText(String.format("Times clicked: %s Clicks From Clicking: %s",
                PLAYER.getTimesClicked(), 
                PLAYER.getClicksFromClicking()/10));
        }});

        clicksPanel.add(clickButton);

       for (String buildingName : B_MATRIX.getMatrix().keySet()) {
            labelMap.put(buildingName, new JLabel(String.valueOf(B_MATRIX.getQuantity(buildingName))));
            buttonMap.put(buildingName, new JButton(String.format("Purchase %s: %s", buildingName, B_MATRIX.getNextPurchaseCost(buildingName)/10)));
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
                playerClicksLabel.setText(String.format("Clicks produced: %s", Long.toString(PLAYER.getClicks()/10)));
                updateClickableUpgrades();
                for (String buildingName : B_MATRIX.getMatrix().keySet()) {
                    labelMap.get(buildingName).setText(Integer.toString(B_MATRIX.getQuantity(buildingName)));
                    buttonMap.get(buildingName).setText(String.format("Purchase %s: %s",
                                             buildingName, B_MATRIX.getNextPurchaseCost(buildingName)/10));
                    buttonMap.get(buildingName).setToolTipText(String.format("Total CPS: %s %nClicks produced: %s", B_MATRIX.getBuildingCps(buildingName)/10, B_MATRIX.getClicksProduced(buildingName)/10));
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