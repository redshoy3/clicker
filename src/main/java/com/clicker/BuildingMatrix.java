package com.clicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BuildingMatrix {
    private Map<String, Building> buildingMap;
    private static BuildingMatrix bm = null;

    public static BuildingMatrix make() {
        if (bm == null) {
            bm = new BuildingMatrix();
        }
        return bm;
    }

    private BuildingMatrix() {
        this.buildingMap = new HashMap<>();
        this.loadBuildings();
    }

    private void loadBuildings() {
        try {
            File buildings = new File("src/main/java/com/clicker/buildings.txt");
            Scanner scanner = new Scanner(buildings);
            while (scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(",");
                buildingMap.put(split[0], new Building(split[0], 
                                            Long.valueOf(split[1]), 
                                            Long.valueOf(split[2])));
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public Map<String, Building> getMatrix() {
        return this.buildingMap;
    }

    public void updateQuantity(String name, int quantity) {
        try {
            buildingMap.get(name).addQuantity(quantity);
        }
        catch (Exception e){
            System.out.println(String.format("Couldn't Find Building %s", name));
        }
    }

    public void updateMultiplier(String name, double multiplier) {
        try {
            buildingMap.get(name).addMultiplier(multiplier);
        }
        catch (NullPointerException e) {
            e.getStackTrace();
        }
    }

    public int getQuantity(String name) {
        return buildingMap.get(name).getQuantity();
    }

    public long getNextPurchaseCost(String name) {
        return buildingMap.get(name).getNextPurchaseCost();
    }
    
    public double getTotalCps() {
        double cpsCounter = 0.0;
        for (Building b : buildingMap.values()) {
            cpsCounter += b.getCps();
        }
        return cpsCounter;
    }
}
