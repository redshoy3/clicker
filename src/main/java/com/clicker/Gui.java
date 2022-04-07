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
    static JPanel upgradePanel;
    static Map<String, JButton> buttonMap;
    static Map<String, JButton> upgradeMap;
    private static final Player PLAYER = App.getPlayer(); 
    private static final BuildingMatrix B_MATRIX = App.getBMatrix();
    private static final UpgradeMatrix U_MATRIX = App.getUMatrix();

    public static void main(String[] args) {
       JFrame frame = new JFrame("Harold Pants: Pro Skater");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(300,300);

       panel = new JPanel(new GridLayout(10, 1));
       clicksPanel = new JPanel(new GridLayout(1,2));
       upgradePanel = new JPanel(new GridLayout(4,4));

       buttonMap = new HashMap<>();
       Map<String, JLabel> labelMap = new HashMap<>();
       upgradeMap = new HashMap<>();

    //THIS IS WHERE THE MAIN BUTTON IS DEFINED
       JLabel playerClicksLabel = new JLabel(
                        String.format("Clicks produced: %,d", PLAYER.getClicks()/10));
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

        //This is the upgrade menu.
        updateUpgrades();


       for (String buildingName : B_MATRIX.getMatrix().keySet()) {
            labelMap.put(buildingName, new JLabel(String.valueOf(B_MATRIX.getQuantity(buildingName))));
            buttonMap.put(buildingName, new JButton(String.format("Purchase %s: %,d", buildingName, B_MATRIX.getNextPurchaseCost(buildingName)/10)));
            JButton button = buttonMap.get(buildingName);
            button.setHorizontalAlignment(SwingConstants.RIGHT);
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
               updateUpgrades();
               
           };     
       }, 0, 1000);


       Timer guiTimer = new Timer();
       guiTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                playerClicksLabel.setText(String.format("Clicks produced: %,d", PLAYER.getClicks()/10));
                updateClickableUpgrades();
                for (String buildingName : B_MATRIX.getMatrix().keySet()) {
                    labelMap.get(buildingName).setText(Integer.toString(B_MATRIX.getQuantity(buildingName)));
                    buttonMap.get(buildingName).setText(String.format("Purchase %s: %,d",
                                             buildingName, B_MATRIX.getNextPurchaseCost(buildingName)/10));
                    buttonMap.get(buildingName).setToolTipText(String.format("Total CPS: %s %nClicks produced: %s", B_MATRIX.getBuildingCps(buildingName)/10, B_MATRIX.getClicksProduced(buildingName)/10));
                }
            }
        
    }, 0, 10);



        Thread cpsThread = new Thread(new Runnable() {  
            @Override
            public void run() {
                long oldCookieTotal = PLAYER.getTotalLifetimeClicks();
                try {
                Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                }

            }
        });
        cpsThread.start();
    }


    public static void update() {
        App.update();
        updateClickableUpgrades();
        updateUpgrades();
    }

    private static void updateClickableUpgrades() {
        for (String s : upgradeMap.keySet()) {
            getClickability(U_MATRIX.getAvailable().get(s));
        }
        for (String s : buttonMap.keySet()) {
            getClickability(B_MATRIX.getMatrix().get(s));
        }
    }

    private static void getClickability(Upgrade upgrade) {
        if (upgrade == null) {return; }
        boolean clickable = false;
        if (PLAYER.getClicks() >= upgrade.getCost()) {
            clickable = true;
        } 
        upgradeMap.get(upgrade.getName()).setEnabled(clickable);
    }

    private static void getClickability(Building building) {
        if (building == null) {return; }
        boolean clickable = false;
        if (PLAYER.getClicks() >= building.getNextPurchaseCost()) {
            clickable = true;
        }
        buttonMap.get(building.getName()).setEnabled(clickable);
    }

    private static void updateUpgrades() {
        for (String upgradeName : U_MATRIX.getAvailable().keySet()) {
            if (!upgradeMap.containsKey(upgradeName)) {
            JButton button = new JButton(upgradeName);
            upgradeMap.put(upgradeName, button);
            button.setToolTipText(String.format("Cost: %,d", U_MATRIX.getCost(upgradeName)/10));
            button.setActionCommand(upgradeName);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (PLAYER.purchaseUpgrade(e.getActionCommand())) {
                        upgradeMap.remove(e.getActionCommand());
                        upgradePanel.remove(button);
                        upgradePanel.revalidate();
                        upgradePanel.repaint();
                        clicksPanel.add(upgradePanel);
                    }
                }

            });
            upgradePanel.add(button);
        }
        
        }
        clicksPanel.add(upgradePanel);
    }
    
}