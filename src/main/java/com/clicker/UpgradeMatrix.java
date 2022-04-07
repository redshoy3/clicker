package com.clicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


//TODO Add a liist of aavailablee upgrades, remove them from original list. ADD COSTS, sort upgrades by cost
public class UpgradeMatrix {
    private Map<String, Upgrade> upgradeMap;
    private Map<String, Upgrade> availableMap;
    private static UpgradeMatrix um = null;


    public static UpgradeMatrix make() {
        if (um == null) {
            um = new UpgradeMatrix();
        }
        return um;
    }

    private UpgradeMatrix() {
        this.upgradeMap = new LinkedHashMap<>();
        this.availableMap = new LinkedHashMap<>();
        loadMatrix();
    }

    private void loadMatrix() {
        try {
            File upgrades = new File("src/main/java/com/clicker/upgrades.txt");
            Scanner scanner = new Scanner(upgrades);
            while (scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(",");
                upgradeMap.put(split[0], new Upgrade(split[0], 
                                            split[1], 
                                            Integer.valueOf(split[2]),
                                            Double.valueOf(split[3]),
                                            Long.valueOf(split[4])
                                            ));
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public Map<String, Upgrade> getAvailable() {
        List<String> toRemove = new ArrayList<>();
        for (String name: upgradeMap.keySet()) {
            if (upgradeMap.get(name).checkAvailable()) {
                availableMap.put(name, upgradeMap.get(name));
                toRemove.add(name);
            }
        }
        //System.out.println(ul);
        for (String s : toRemove) {
            upgradeMap.remove(s);
        }

        return new LinkedHashMap<>(availableMap);
    }

    public void apply(String name) {
        Upgrade upgrade = availableMap.get(name);
        BuildingMatrix bMatrix = App.getBMatrix();
        if (upgrade.getBuilding().equals("Player")) {
            App.getPlayer().updateClickMultiplier(upgrade.getBonus());
        }
        bMatrix.updateMultiplier(upgrade.getBuilding(), upgrade.getBonus());

        availableMap.remove(name);

    }

    public long getCost(String name) {
        if (availableMap.containsKey(name)) {
            return availableMap.get(name).getCost();
        }
        return Long.MAX_VALUE;
    }
}
