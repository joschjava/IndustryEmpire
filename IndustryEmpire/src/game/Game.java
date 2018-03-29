package game;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import mainpack.Const;

public class Game {
    private List<TickListener> tickListener = new ArrayList<TickListener>();

    private static long tickTime = 0;
    private static Game game = null;
    private Player player = new Player();
    
    
    private Game() {
    	init();
    }
    
    public static Game getInstance() {
    	if(game == null) {
    		game = new Game();
    	}
    	return game;
    }
    
    public Player getPlayer() {
    	return player;
    }
    
    private void init(){
//    	new Buildings();
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
    	if(Math.random()<0.4) {
    		player.chgMoneyValueBy(1000);
    	}
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
    	start(Const.STD_TICK_INTERVAL);
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
