package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import game.Building;
import game.BuildingSpec;
import game.City;
import game.Connection;
import game.Game;
import game.Itinerary;
import game.ItineraryItem;
import game.Position;
import game.Resource;
import game.ResourceSpec;
import game.Resources;
import game.Vehicle;
import game.VehicleSpecs;
import game.Vehicles;
import mainpack.Const;
import objects.ResourceList;
@RunWith(JUnitPlatform.class)
class Unittest {

	@AfterEach
	void setup() {
		Game.resetGame();
	}
	
	/**
	 * Creates two cities on X axis and lets car drive from one to another
	 */
	@Disabled @Test
	void driveTest() {
		Game game = Game.getInstance();
		City essen = new City("Essen", 0, 400);
		Vehicle karl = new Vehicle(new VehicleSpecs(40,20), essen);
        City berlin = new City("Berlin",400,400);
        Connection con = new Connection(essen,berlin);
        Itinerary itinerary = new Itinerary();
        itinerary.add(new ItineraryItem(essen, null));
        itinerary.add(new ItineraryItem(berlin, null));

        itinerary.setLoop(false);
        karl.setItinerary(itinerary);
        for (int i = 0; i < 40; i++) {
        	game.ticknLog();
		}

        City city = karl.getCity();
        
	    assertEquals("Car didn't arrive in destination",berlin, city);

	}

	@Disabled @Test 
	void driveTest2() {
		City essen = new City("Essen", 0, 0);
		Vehicle karl = new Vehicle(new VehicleSpecs(50,20), essen);
        City berlin = new City("Berlin",400,300);
        Connection con = new Connection(essen,berlin);
        Itinerary itinerary = new Itinerary();
        itinerary.add(new ItineraryItem(essen, null));
        itinerary.add(new ItineraryItem(berlin, null));
        karl.setItinerary(itinerary);
        karl.driveToNextItineraryItem();
        for (int i = 0; i < 10; i++) {
        	System.out.print(i+": ");
        	karl.onTick();
		}
        City destination = karl.getCity();
        assertNotEquals("Car should be in "+berlin+" by now",null, destination);
        assertEquals("Car should be in "+berlin+" by now, but is in "+destination, berlin, destination);
	}
	
	/**
	 * Creates three cities and lets car drive to the end in a circle
	 */
	@Disabled @Test 
	void driveTest3() {
		Game game = Game.getInstance();
		City essen = new City("Essen", 0, 0);
		Vehicle karl = new Vehicle(new VehicleSpecs(50,20), essen);
        City berlin = new City("Berlin",400,300);
        City frankfurt = new City("Frankfurt",0,500);
        Itinerary itinerary = new Itinerary();
        itinerary.add(new ItineraryItem(essen, null));
        itinerary.add(new ItineraryItem(berlin, null));
        itinerary.add(new ItineraryItem(frankfurt, null));
        karl.setItinerary(itinerary);
        for (int i = 0; i < 32; i++) {
        	game.tick();
		}
        City destination = karl.getCity();
        assertNotEquals("Car should be in "+essen+" by now",null, destination);
        assertEquals("Car should be in "+essen+" by now, but is in "+destination, essen, destination);
//        assertEquals("Car should not be driving right now ", false, karl.isDriving());
	}
	
	/**
	 * Creates a book factory that makes 10 BOOKS from 1 WOOD in 10 ticks
	 * Loads a city with a book factory and 
	 */
	@Disabled @Test
	void produceTest() {
		Game game = Game.getInstance();
		BuildingSpec BOOK_FACTORY = new BuildingSpec("Book Factory",10);
		BOOK_FACTORY.addResource(Resources.WOOD_DEB, 1, BuildingSpec.INPUT);
		BOOK_FACTORY.addResource(Resources.BOOKS, 10, BuildingSpec.OUTPUT);
		
        City berlin = new City("Berlin",400,300);
        new Building(BOOK_FACTORY, berlin);
        berlin.setResourceAmount(Resources.WOOD_DEB, 100);

        
        for(int i=0;i<100;i++) {
        	System.out.println(berlin.getResourceAmount(Resources.WOOD_DEB));
        	game.tick();
        }
        
        double books = berlin.getResourceAmount(Resources.BOOKS);
        double wood = berlin.getResourceAmount(Resources.WOOD_DEB);
        assertEquals("Books should be 100, but are "+books, 100, books,Const.TOLERANCE);
        assertEquals("Wood should be 90, but are "+wood, 90, wood,Const.TOLERANCE);
        for(int i=0;i<900;i++) {
        	game.tick();
        }
        
        books = berlin.getResourceAmount(Resources.BOOKS);
        wood = berlin.getResourceAmount(Resources.WOOD_DEB);
        System.out.println(berlin.getResourceAmount(Resources.BOOKS));
        System.out.println(berlin.getResourceAmount(Resources.WOOD_DEB));
        assertEquals("Books should be 1000, but are "+books, 1000, books,0.01);
        assertEquals("Wood should be 0, but are "+wood, 0, wood,0.01);
	}
	
	@Disabled @Test
	void itineraryTest() {
		City essen = new City("Essen");
		City berlin = new City("Berlin");
		City frankfurt = new City("Frankfurt");
		VehicleSpecs specs = new VehicleSpecs(Integer.MAX_VALUE, 20);
		Vehicle karl = new Vehicle(specs, essen);
		Itinerary itinerary = new Itinerary();
		itinerary.add(new ItineraryItem(essen, null));
		itinerary.add(new ItineraryItem(berlin, null));
		itinerary.add(new ItineraryItem(frankfurt, null));
		karl.setItinerary(itinerary);
		Game game = Game.getInstance();
		City[] cities = {essen, berlin, frankfurt};
		for (int i = 0; i < 3; i++) {
			int maxTime = 10;
			while(karl.getCity() != cities[i]) {
				game.tick();
				maxTime--;
				if(maxTime == 0) {
					break;
				}
			}
			assertEquals("Wrong city of vehicle", cities[i], karl.getCity());
		}
	}
	
	 @Test
	void multipleLoadTest() {
		City essen = new City("Essen");
		double amount = 20.0;
		essen.chgResource(Resources.TEST1, amount);
		City berlin = new City("Berlin");
		VehicleSpecs specs = new VehicleSpecs(Integer.MAX_VALUE, 20);
		Vehicle karl = new Vehicle(specs, essen);
		Itinerary itinerary = new Itinerary();
		Resource[] res = {new Resource(Resources.TEST1, 5)};
		itinerary.add(new ItineraryItem(essen, res));
		itinerary.add(new ItineraryItem(berlin, null));
        karl.setItinerary(itinerary);
        Game game = Game.getInstance();
        for (int i = 0; i < 50; i++) {
            game.tick();
		}
        assertEquals("Resources didn't arrive", amount, berlin.getResourceAmount(Resources.TEST1),0.0);
	}
	
	
	
	/**
	 * Creates a book factory that makes 10 BOOKS from 1 WOOD in 10 ticks
	 * Loads a city with a book factory and 
	 */
	@Disabled @Test 
	void produceAndDriveTest() {
		Game game = Game.getInstance();
		BuildingSpec BOOK_FACTORY = new BuildingSpec("Book Factory",10);
		BuildingSpec WOOD_FACTORY = new BuildingSpec("Wood Factory",10);
		BOOK_FACTORY.addResource(Resources.WOOD_DEB, 1, BuildingSpec.INPUT);
		BOOK_FACTORY.addResource(Resources.BOOKS, 10, BuildingSpec.OUTPUT);
		WOOD_FACTORY.addResource(Resources.WOOD_DEB, 10, BuildingSpec.OUTPUT);
		
		City berlin = new City("Berlin",0,0);
        City essen = new City("Essen",0,400);
//        new Building(WOOD_FACTORY, essen);

        essen.setResourceAmount(Resources.WOOD_DEB, 100);
              
//        new Building(BOOK_FACTORY, berlin);
        
		Vehicle karl = new Vehicle(new VehicleSpecs(Integer.MAX_VALUE,20), essen);

        Itinerary itinerary = new Itinerary();
        Resource woodLoad = new Resource(Resources.WOOD_DEB, 100);
        Resource[] transfer = {woodLoad};
        itinerary.add(new ItineraryItem(essen, transfer));
        itinerary.add(new ItineraryItem(berlin, null));
        karl.setItinerary(itinerary);
		essen.printResources();
        berlin.printResources();
        karl.printLoad();
        System.out.println("_______________________________");

    
        for (int i = 0; i < 10; i++) {
			game.tick();
			
			essen.printResources();
	        berlin.printResources();
	        karl.printLoad();

	        System.out.println("_______________________________");
		}

        
        
	}
	
	@Disabled @Test 
	/**
	 * Checks isZero() function of Resource
	 */
	void toleranceTest() {
		ResourceSpec testRes = new ResourceSpec("TestResource");
		Resource res = new Resource(testRes);
		
		res.setAmount(0.01);
		assertEquals("Tolerance Error, did you change Balance.TOLERANCE?", false, Resource.isZero(res) );
		
		res.setAmount(0.0);
		assertEquals("Tolerance Error, did you change Balance.TOLERANCE?", true, Resource.isZero(res) );
		
		res.setAmount(0.0001);
		assertEquals("Tolerance Error, did you change Balance.TOLERANCE?", true, Resource.isZero(res) );
		
		res.setAmount(-0.00008);
		assertEquals("Tolerance Error, did you change Balance.TOLERANCE?", true, Resource.isZero(res) );
		
		res.setAmount(100.4038);
		assertEquals("Tolerance Error, did you change Balance.TOLERANCE?", false, Resource.isZero(res) );
	}
	
	
	@Disabled @Test
	/**
	 * Tests resourceList
	 */
	void resourceListTest() {
		ResourceList list = new ResourceList();
		Resource res1 = new Resource(Resources.TEST1, 50);
		Resource res2 = new Resource(Resources.TEST2, 20);
		Resource res3 = new Resource(Resources.TEST3, 80);
		
		list.chgResourceAmountBy(res1, ResourceList.PLUS);
		list.chgResourceAmountBy(res2, ResourceList.PLUS);
		list.chgResourceAmountBy(res3, ResourceList.PLUS);
		
		
		
		Resource res1Extracted = list.getElementByResSpec(res1.getSpec());
		
		assertEquals("getElementByResSpec returns wrong resource", true, res1Extracted.isEqualResource(res1));
		
		//In case you activate empty resource deleting in ResourceList again
//		list.chgResourceAmountBy(res1,ResourceList.MINUS);
//		res1Extracted = list.getElementByResSpec(res1.getSpec());
//		
//		assertEquals("chgResourceAmountBy doesnt delete empty resource", null, res1Extracted);
		
		Resource res3b = new Resource(Resources.TEST3, 20);
		list.chgResourceAmountBy(res3b,ResourceList.MINUS);
		assertEquals("Wrong amount value", 60.0, list.getElementByResSpec(res3b.getSpec()).getAmount(), 0.0);

	}
	
	@Disabled @Test
	/**
	 * Tests the "Wait for full option"
	 */
	void itineraryfullTest() {
		Game game = Game.getInstance();
		BuildingSpec BOOK_FACTORY = new BuildingSpec("Book Factory",10);
		BuildingSpec WOOD_FACTORY = new BuildingSpec("Wood Factory",10);
		BOOK_FACTORY.addResource(Resources.WOOD_DEB, 1, BuildingSpec.INPUT);
		BOOK_FACTORY.addResource(Resources.BOOKS, 10, BuildingSpec.OUTPUT);
		WOOD_FACTORY.addResource(Resources.WOOD_DEB, 10, BuildingSpec.OUTPUT);
		
		City berlin = new City("Berlin",0,0);
        City essen = new City("Essen",0,400);
        new Building(WOOD_FACTORY, essen);
        new Building(BOOK_FACTORY, berlin);
        
		Vehicle karl = new Vehicle(new VehicleSpecs(Integer.MAX_VALUE,20), essen);

        Itinerary itinerary = new Itinerary();
        Resource woodLoad = new Resource(Resources.WOOD_DEB, 10);
        Resource[] transfer = {woodLoad};
        itinerary.add(new ItineraryItem(essen, transfer, true));
        itinerary.add(new ItineraryItem(berlin, null));
        itinerary.setLoop(false);
        karl.setItinerary(itinerary);
		essen.printResources();
        berlin.printResources();
        karl.printLoad();
        System.out.println("_______________________________");

    
        for (int i = 0; i < 150; i++) {
			game.tick();
			essen.printResources();
	        berlin.printResources();
	        karl.printLoad();
	        System.out.println("_______________________________");
		}
        
        double books = berlin.getResourceAmount(Resources.BOOKS);
        assertEquals("Invalid amount of books", 100.0, books,Const.TOLERANCE);
        
	}
	
	@Disabled @Test
	/**
	 * Tests adding resources to a city
	 */
	void ResourceAddTest() {
		City berlin = new City("Berlin");
		berlin.setResourceAmount(Resources.TEST1, 5.0);
		berlin.chgResource(Resources.TEST1, 10.0);
		assertEquals("Wrong amount of resources",15.0, berlin.getResourceAmount(Resources.TEST1), Const.TOLERANCE);
	}
	
	@Disabled @Test
	/**
	 * Tests what happens if you want to get more resources than available
	 */
	void notEnoughResourcesTest() {
		City berlin = new City("Berlin");
		berlin.setResourceAmount(Resources.TEST1, 5.0);
		berlin.chgResource(Resources.TEST1, -10.0);
		assertEquals("Wrong amount of resources",0.0, berlin.getResourceAmount(Resources.TEST1), Const.TOLERANCE);
	}
	
	@Disabled @Test
	/**
	 * Tests all four cases of resourcelist
	 */
	void resourceListTestExtended() {
		ResourceList list = new ResourceList();
		Resource test1 = new Resource(Resources.TEST1, 5.0);
		list.chgResourceAmountBy(test1, ResourceList.PLUS);
		double amount = list.getResAmountByResSpec(test1.getSpec());
		assertEquals("Wrong resource amount", 5.0, amount, Const.TOLERANCE);
		
		ResourceList list2 = new ResourceList();
		Resource test2 = new Resource(Resources.TEST1, 5.0);
		list2.chgResourceAmountBy(test2, ResourceList.MINUS);
		amount = list2.getResAmountByResSpec(test2.getSpec());
		assertEquals("Wrong resource amount", 0.0, amount, Const.TOLERANCE);
		
		// Try getting more resources than available
		ResourceList list3 = new ResourceList();
		Resource test3 = new Resource(Resources.TEST1, 5.0);
		Resource minus = new Resource(Resources.TEST1, 10.0);
		list3.chgResourceAmountBy(test3, ResourceList.PLUS);
		list3.chgResourceAmountBy(minus, ResourceList.MINUS);
		amount = list3.getResAmountByResSpec(test3.getSpec());
		assertEquals("Wrong resource amount", 0.0, amount, Const.TOLERANCE);
		
		ResourceList list4 = new ResourceList();
		Resource test4 = new Resource(Resources.TEST1, 5.0);
		Resource plus = new Resource(Resources.TEST1, 10.0);
		list4.chgResourceAmountBy(test4, ResourceList.PLUS);
		list4.chgResourceAmountBy(plus, ResourceList.PLUS);
		amount = list4.getResAmountByResSpec(test4.getSpec());
		assertEquals("Wrong resource amount", 15.0, amount, Const.TOLERANCE);
	}
	
	
//	Game game = Game.getInstance();
//	Building pancake = new Building(Buildings.PANCAKE_FACTORY);
//	Building pancake2 = new Building(Buildings.PANCAKE_FACTORY);
//	for(int i=0;i<5;i++) {
//		game.tick();
//
//		if(i==2) {
//			pancake.chgResAmount(Resources.MILK, 10);
//			pancake.chgResAmount(Resources.EGG, 20);
//		}
//		
//		if(i==50) {
//			pancake.chgResAmount(Resources.MILK, 5);
//			pancake.chgResAmount(Resources.EGG, 13);
//		}
//		System.out.println(pancake);
////		System.out.println(pancake2);
//	}
//	
//	res = new ResourceChart();
//	ArrayList<HistoryObject> milkHistory = pancake.getHistory(Resources.MILK);
//	ArrayList<HistoryObject> pancakeHistory = pancake.getHistory(Resources.PANCAKE);
//	ArrayList<HistoryObject> eggHistory = pancake.getHistory(Resources.EGG);
//	ArrayList<HistoryObject> heatHistory = pancake.getHistory(Resources.HEAT);
//	res.addSeries(milkHistory, Resources.MILK);
//	res.addSeries(pancakeHistory, Resources.PANCAKE);
//	res.addSeries(eggHistory, Resources.EGG);
//	res.addSeries(heatHistory, Resources.HEAT);
}
