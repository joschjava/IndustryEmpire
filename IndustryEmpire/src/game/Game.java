package game;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Game {
    private List<TickListener> tickListener = new ArrayList<TickListener>();

    private static long tickTime = 0;
    private static Game game = null;
    
    private Game() {
    	init();
    	
    }
    
    public static Game getInstance() {
    	if(game == null) {
    		game = new Game();
    	}
    	return game;
    }
    
    private void init(){
    	Buildings.init();
    	new Vehicles();
    }
    
    public void addListener(TickListener toAdd) {
    	tickListener.add(toAdd);
    }

    public static long getTick() {
    	return tickTime;
    }
    
    public void ticknLog() {
    	System.out.println(tickTime+1);
    	tick();
    }
    
    public void tick() {
    	tickTime++;
     	if(tickTime==Long.MAX_VALUE) {
    		System.err.println("Maximum time value reached!");
    	}
    	
        for (TickListener hl : tickListener) {
            hl.onTick();
        }
    }
    
    /**
     * Starts the game ticker
     */
    public void start() {
    	start(100);
    }
    
    /**
     * Starts the game ticker with a specified interval for each tick in ms
     */
    public void start(int intervalTime) {
    	Timeline ticker = new Timeline(
				new KeyFrame(Duration.millis(0), ae -> tick()),
				new KeyFrame(new Duration(intervalTime))
				);
    	ticker.setCycleCount(Timeline.INDEFINITE);
    	ticker.play();
    }
    
    public static void resetGame() {
    	game = null;
    	System.out.println("Game resetted");
    }
}
