package com.clicker;

public class Upgrade {
    private final String upgradeName;
    private final String building;
    private final int requiredQuantity;
    private final double bonus;
    private final long cost;

    public Upgrade(String upgradeName, String building, int requiredQuantity, double bonus, long cost) {
        this.upgradeName = upgradeName;
        this.building = building;
        this.requiredQuantity = requiredQuantity;
        this.bonus = bonus;
        this.cost = cost;
    }

    public boolean checkAvailable() {
        if ("Player".equals(building)) {
            return App.getPlayer().getTimesClicked() >= requiredQuantity;
        }

        return App.getBMatrix().getQuantity(building) >= requiredQuantity;
    }

    public String getName() {
        return this.upgradeName;
    }
    public long getCost() {
        return this.cost;
    }

    public double getBonus() {
        return this.bonus;
    }

    public String getBuilding() {
        return this.building;
    }

    @Override
    public String toString() {
        return String.format("%s, cost %s, building affected %s", upgradeName, bonus, building);
    }
}
