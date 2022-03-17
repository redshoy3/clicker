package com.clicker;

public class Building {
    private final long clicksPerSecond;
    private double cpsMultiplier;
    private final long initialPrice;
    private int quantity;
    private final String name;

    public Building(String name, long clicksPerSecond, long initialPrice) {
        this.name = name;
        this.clicksPerSecond = clicksPerSecond;
        this.initialPrice = initialPrice;
        this.cpsMultiplier = 1;
        this.quantity = 0;
    }

    void addMultiplier(double multiplier) {
        cpsMultiplier *= (1.0 + multiplier);
    }

    long getCps() {
        return (long) (clicksPerSecond * cpsMultiplier * quantity);
    }

    void addQuantity(int toAdd) {
        quantity += toAdd;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public long getNextPurchaseCost() {
        return (long) (initialPrice * ( Math.pow(1.15, quantity)));
    }

    @Override
    public String toString() {
        return String.format("Type: %s%nNumber owned: %s%nTotal Production: %s%nCost of Next: %s", this.name, this.quantity, this.getCps(), this.getNextPurchaseCost());            
    }
}
