package game;

public interface TickListener {

	/**
	 * Called when next tick occurs
	 */
	public void onTick();
	
	/**
	 * Called when timer is set to a specific time
	 * @param ticks Set "time"
	 * @deprecated not yet implemented
	 */
	public void onChgDate(long ticks);
}
