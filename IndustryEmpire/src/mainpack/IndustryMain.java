package mainpack;

import java.io.IOException;
import java.net.URL;

import game.Building;
import game.Buildings;
import game.City;
import game.Game;
import game.Itinerary;
import game.ItineraryItem;
import game.Resource;
import game.Resources;
import game.Vehicle;
import game.VehicleSpecs;
import game.Vehicles;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Performance enhancer:
// Update factories only when it is interacted with
// Image is being assigned to GuiVehicle every time

//Load list before game:
// Images for resources? (ResSpec.loadImage?)

public class IndustryMain extends Application {

	Pane route = new Pane();
	
	public static void main(String[] args) {

		
		
		Game.getInstance();
		City essen = new City("Essen");          
		essen.setResourceAmount(Resources.WOOD, 20);
		City berlin = new City("Berlin");
		City frankfurt = new City("Frankfurt");
		City bonn = new City("Bonn");
		City newtown = new City("Newtown");
		new Buildings();
		new Building(Buildings.WOOD_FACTORY, essen);
//		new Building(Buildings.WOOD_FACTORY, essen);
		new Building(Buildings.BOOK_FACTORY, frankfurt);
		new Building(Buildings.SUPER_RES_FACTORY, essen);
		
		Vehicle karl = new Vehicle(Vehicles.FUEL_CONSUMPTION_TRUCK, essen);
        Itinerary itinerary = new Itinerary();
        Resource[] input = {new Resource(Resources.WOOD,20)};
        ItineraryItem item = new ItineraryItem(essen, input);
        Resource[] input2 = {new Resource(Resources.BOOKS,20)};
        ItineraryItem item2 = new ItineraryItem(frankfurt, input2);
//        item.setWaitForFull(true);
        itinerary.add(item);
		itinerary.add(item2);

        
        karl.setItinerary(itinerary);
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
//		City essen = new City("Essen");
//		new Building(Buildings.SUPER_RES_FACTORY, essen);
//		CityPane.show(essen);
		
		
        FXMLLoader loader = new FXMLLoader();
        URL res = IndustryMain.class.getResource("/mainwindow.fxml");
        loader.setLocation(res);
        VBox rootLayout = null;
		try {
			rootLayout = (VBox) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		VehicleCreation controller = loader.getController();
		
		
			
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
//		Game.getInstance().start(2000);
		primaryStage.show();
		Game.getInstance().start();
		
	}




	
	
}
