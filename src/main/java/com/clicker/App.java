package com.clicker;

import java.io.IOException;
import java.lang.System.Logger;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hello world!
 */
public final class App {
    private static final Player player = Player.getInstance();
    private static final BuildingMatrix bMatrix = BuildingMatrix.make();
    Timer timer;
    
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */


    public static Player getPlayer() {
        return player;
    }

    public static BuildingMatrix getBMatrix() {
        return bMatrix;
    }

    public static void update() {
        readConsole();
        player.addClicks( (int) bMatrix.getTotalCps());
            
    }

    private static void readConsole() {
        boolean available = false;
        try { available = System.in.available() > 0;}
        catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        } 
        if (available) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String text = scanner.nextLine();
            if (text.indexOf("/") == 0) {
                ConsoleParser.parse(text);
            }
        }
    }
}

    public static void reset() {
        player.reset();
        bMatrix.resetBuildings();
    }

    public void run() {
        update();
    }
}
