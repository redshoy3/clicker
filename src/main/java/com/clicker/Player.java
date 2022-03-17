package com.clicker;

import com.clicker.Purchase;

public class Player {
    private long clicks;
    private static Player player = null;

    private Player() {
            this.clicks = 150;        
    }

    public static Player getInstance() {
        if (player == null) {
            player = new Player();
        }
        return player;
    } 

    public long getClicks() {
        return this.clicks;
    }

    public void addClicks(long clicksAdd) {
        clicks += clicksAdd;
    } 
    public void removeClicks(long clicksDrop) {
        clicks -= clicksDrop;
    }

    public void purchase(String building) {
        Purchase.purchase(building);
    }
}
