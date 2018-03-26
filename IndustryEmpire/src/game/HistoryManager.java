package game;

import java.util.ArrayList;

import objects.HistoryObject;

public class HistoryManager {

	private ArrayList<HistoryObject> history = new ArrayList<HistoryObject>();
	private HistoryObject lastObject = null;
	
	public void addNumber(int number) {
		addNumber(Double.valueOf(number));
	}
	
	public void addNumber(double number){
		long now = Game.getTick();
		if(lastObject != null) {
			long lastTime = lastObject.getTime();
			if(now-lastTime>1) {
				history.add(new HistoryObject(lastObject.getValue(),now));
			}
		}
		lastObject = new HistoryObject(number,now);
		history.add(lastObject);
	}
	
	public ArrayList<HistoryObject> getHistory(){
		return history;
	}

	public static void print(ArrayList<HistoryObject> history) {
		history.forEach((historyOb) -> System.out.println(historyOb.getValue()));
	}
	
	
}
