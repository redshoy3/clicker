package com.clicker;

public class Building implements Comparable<Building>{
    private final long clicksPerSecond;
    private long clicksProduced;
    private double cpsMultiplier;
    private final long initialPrice;
    private int quantity;
    private final String name;

    public Building(String name, long clicksPerSecond, long initialPrice) {
        this.name = name;
        this.clicksPerSecond = clicksPerSecond;
        this.initialPrice = initialPrice;
        this.clicksProduced = 0;
        this.cpsMultiplier = 1;
        this.quantity = 0;
    }

    void addMultiplier(double multiplier) {
        cpsMultiplier *= (1.0 + multiplier);
    }

    long getCps() {
        return (long) (clicksPerSecond * cpsMultiplier * quantity);
    }

    long getInitialPrice() {
        return this.initialPrice;
    }

    void addQuantity(int toAdd) {
        quantity += toAdd;
    }

    void reset() {
        quantity = 0;
        cpsMultiplier = 1.0;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void addClicksProduced() {
        clicksProduced += getCps();
    }

    public long getClicksProduced() {
        return this.clicksProduced;
    }

    public long getNextPurchaseCost() {
        return (long) (initialPrice * ( Math.pow(1.15, quantity)));
    }

    @Override
    public String toString() {
        return String.format("Type: %s%nNumber owned: %s%nTotal Production: %s%nCost of Next: %s", this.name, this.quantity, this.getCps(), this.getNextPurchaseCost());            
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof Building) {
            Building bThat = (Building) that;
            return (this.getInitialPrice() == bThat.getInitialPrice());
        }
        else { return false; }
    }

    @Override
    public int compareTo(Building that) {
        return Long.compare(this.initialPrice, that.initialPrice);
    }
}
