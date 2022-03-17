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
    public static void main(String args[]){
       JFrame frame = new JFrame("Harold Pants: Pro Skater");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(300,300);

       panel = new JPanel(new GridLayout(10, 1));
       clicksPanel = new JPanel(new GridLayout(1,2));

       Map<String, JButton> buttonMap = new HashMap<>();
       Map<String, JLabel> labelMap = new HashMap<>();

       JLabel playerClicksLabel = new JLabel(Long.toString(App.getPlayer().getClicks()));
       JButton clickButton = new JButton("Jam on 'em");

       clickButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            App.getPlayer().addClicks(10);
            playerClicksLabel.setText(Long.toString(App.getPlayer().getClicks()));
        }});

        clicksPanel.add(clickButton);

       for (String buildingName : App.getBMatrix().getMatrix().keySet()) {
            labelMap.put(buildingName, new JLabel(String.valueOf(App.getBMatrix().getQuantity(buildingName))));
            buttonMap.put(buildingName, new JButton(String.format("Purchase %s", buildingName)));
            JButton button = buttonMap.get(buildingName);
            JPanel innerPanel = new JPanel(new GridLayout(1,2));
            button.setSize(80, 80);
            button.setActionCommand(buildingName);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //clean this up, make the gui update into a method.
                    String buttonName = e.getActionCommand();
                    App.getPlayer().purchase(buttonName);
                    labelMap.get(buttonName).setText(Integer.toString(App.getBMatrix().getQuantity(buttonName)));
                    buttonMap.get(buttonName).setText(String.format("Purchase %s: %s", buttonName, Long.valueOf(App.getBMatrix().getNextPurchaseCost(buttonName))));
                    playerClicksLabel.setText(Long.toString(App.getPlayer().getClicks()));
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
       update();

       Timer timer = new Timer();
       timer.schedule(new TimerTask() {
           public void run() {
                playerClicksLabel.setText(Long.toString(App.getPlayer().getClicks()));
                for (String s : buttonMap.keySet()) {
                    if (App.getPlayer().getClicks() >= App.getBMatrix().getNextPurchaseCost(s)) {
                        buttonMap.get(s).setEnabled(true);
                    }

                    else {buttonMap.get(s).setEnabled(false);}
                }
            }
       }, 0,1000);
       
    }


    public static void update() {
        App.update();
    }

}