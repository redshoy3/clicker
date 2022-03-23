package com.clicker;

import com.clicker.Purchase;

public class Player {
    private long clicks;
    private long timesClicked = 0;
    private double clickMultiplier;
    private long clicksFromClicking = 0;
    private static Player player = null;

    private final int CLICK_POWER = 10;

    private Player() {
            this.clicks = 150; 
            this.clickMultiplier = 1.0;       
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

    public long getTimesClicked() {
        return this.timesClicked;
    }

    private void addTimesClicked() {
        timesClicked++;
    }

    public void clickButton() {
        addTimesClicked();
        addClicks(getClickPower());
        addClicksfromClicking();
    }

    private long getClickPower() {
        return (long) (CLICK_POWER * clickMultiplier);
    }

    public long getClicksFromClicking() {
        return this.clicksFromClicking;
    }

    private void addClicksfromClicking() {
        clicksFromClicking += getClickPower();
    }

    public void removeClicks(long clicksDrop) {
        clicks -= clicksDrop;
    }

    public void purchase(String building) {
        Purchase.purchase(building);
    }

    public void reset() {
        removeClicks(this.clicks);
    }
}
