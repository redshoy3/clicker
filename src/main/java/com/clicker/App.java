package com.clicker;

import java.lang.Thread;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Hello world!
 */
public final class App {
    static final Player player = Player.getInstance();
    static final BuildingMatrix bMatrix = BuildingMatrix.make();
    Timer timer;
    
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        
        
        player.getClicks();
        //Thread.sleep(20000);
        for (Building b : bMatrix.getMatrix().values()) {
            System.out.println(b.toString());
        }
        System.out.println(bMatrix.getTotalCps());
    }

    public static Player getPlayer() {
        return player;
    }

    public static BuildingMatrix getBMatrix() {
        return bMatrix;
    }

    public static void update() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                player.addClicks( (int) bMatrix.getTotalCps());
                System.out.println(String.valueOf(player.getClicks()));
            }
        }, 0, 1000);
    }
}
