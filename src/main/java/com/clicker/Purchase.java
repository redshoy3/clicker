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
}
