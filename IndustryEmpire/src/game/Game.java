package game;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.util.Duration;
import mainpack.Const;

public class Game {
    private List<TickListener> tickListener = new ArrayList<TickListener>();

    private static LongProperty tickTime = new SimpleLongProperty(0);
    private static Game game = null;
    private Player player = Player.getInstance();
    public static int tickInterval = Const.STD_TICK_INTERVAL;
//    public static long lastTime = 0;
    
	private static Timeline ticker;
    
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
    
    public static void setTickInterval(int tickInterval) {
    	Game.tickInterval = tickInterval;
    }
    
    public void addListener(TickListener toAdd) {
    	tickListener.add(toAdd);
    }

    /**
     * Returns the needed time in ms for the number of ticks
     * @param ticks
     * @return
     */
    public static int getTimeForNrTicks(int ticks) {
    	return ticks*tickInterval;
    }
    
//    public static void pause() {
//    	if(ticker != null) {
//    		ticker.stop();
//    	}
//	}
    
//    public static void continueGame() {
//    	Game.getInstance().start();
//    }

//	public void removeListener(TickListener toAdd) {
//    	pause();
//    	tickListener.remove(toAdd);
//    	continueGame();
//    }
    
    public static long getTick() {
    	return tickTime.get();
    }
    
    public static LongProperty tickProperty() {
    	return tickTime;
    }
    
    public void ticknLog() {
    	System.out.println(tickTime.get()+1);
    	tick();
    }
    
    public void tick() {
//    	long now = System.currentTimeMillis();
//    	System.out.println(now-lastTime);
//    	lastTime = now;
    	
    	tickTime.set(tickTime.get()+1);
     	if(tickTime.get()==Long.MAX_VALUE) {
    		System.err.println("Maximum time value reached!");
    	}
    	
     	tickListener.forEach( tl -> tl.onTick());
    }
    
    /**
     * Starts the game ticker
     */
    public void start() {
    	start(tickInterval);
    }
    
    /**
     * Starts the game ticker with a specified interval for each tick in ms
     */
    public void start(int intervalTime) {
    	tickInterval = intervalTime;
    	ticker = new Timeline(
				new KeyFrame(Duration.millis(0), ae -> tick()),
				new KeyFrame(new Duration(intervalTime))
				);
    	ticker.setCycleCount(Timeline.INDEFINITE);
    	ticker.play();
    }
    
    public void resetGame() {
    	game = null;
    	System.out.println("Game resetted");
    }
}
