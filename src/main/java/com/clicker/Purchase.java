package com.clicker;
import java.util.Map;

public class Purchase {
    public static void purchase(String name) {
        Player player = App.getPlayer();
        long playerClicks = player.getClicks();
        BuildingMatrix priceMatrix = App.getBMatrix();
        long price = priceMatrix.getMatrix().get(name).getNextPurchaseCost();
        if (playerClicks >= price) {
            player.removeClicks(price);
            priceMatrix.updateQuantity(name, 1);
        }
    }
    
    public static boolean purchaseUpgrade(String name) {
        UpgradeMatrix upgradeMatrix = App.getUMatrix();
        Player player = App.getPlayer();
        long cost =  upgradeMatrix.getCost(name);
        if (player.getClicks() >= cost) {
            upgradeMatrix.apply(name);
            player.removeClicks(cost);
            return true;
        }
    return false;
    }
}
