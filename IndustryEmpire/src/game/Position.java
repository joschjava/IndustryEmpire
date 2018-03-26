package game;

import java.util.ArrayList;

public abstract class Position {

	private static ArrayList<Position> list = new ArrayList<Position>();
	
	public Position() {
		list.add(this);
	}

	public abstract double getX();
	public abstract double getY();
	
	public static void printAllPositions() {
		list.forEach(System.out::println);
	}
	
	public static ArrayList<Position> getPositions() {
		return list;
	}
	
}
