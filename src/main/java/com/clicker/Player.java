package com.clicker;


public class Player {
    private long clicks;
    private long timesClicked = 0;
    private long totalLifetimeClicks = 0;
    private double clickMultiplier;
    private long clicksFromClicking = 0;
    private static Player player = null;

    private static final int CLICK_POWER = 10;

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
        totalLifetimeClicks += clicksAdd;
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

    public long getTotalLifetimeClicks() {
        return totalLifetimeClicks;
    }


    private void addClicksfromClicking() {
        clicksFromClicking += getClickPower();
    }

    public void removeClicks(long clicksDrop) {
        clicks -= clicksDrop;
    }

    public void updateClickMultiplier(double mult) {
        clickMultiplier += mult;
    }
    public void purchase(String building) {
        Purchase.purchase(building);
    }

    public boolean purchaseUpgrade(String upgrade) {
        return Purchase.purchaseUpgrade(upgrade);
    }

    public void reset() {
        removeClicks(this.clicks);
    }
}
