package game;

public class Cities {
	
	public static void generateCities() {
		City essen = new City("Essen");    
		City berlin = new City("Berlin");
		City frankfurt = new City("Frankfurt");
		City bonn = new City("Bonn");
		City newtown = new City("Newtown");
		new Buildings();
		new Building(Buildings.WOOD_FACTORY, essen);
//		new Building(Buildings.WOOD_FACTORY, essen);
		new Building(Buildings.BOOK_FACTORY, frankfurt);
		new Building(Buildings.SUPER_RES_FACTORY, essen);
	}
	
}
