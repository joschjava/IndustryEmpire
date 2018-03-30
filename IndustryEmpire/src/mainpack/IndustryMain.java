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
		
//		new City("neu");
//		for(int y=0;y<200;y+=50) {
//			for(int x=0;x<200;x+=50) {
//				new City(x,y);
//			}
//		}

		
		
		Game.getInstance();
		City essen = new City("Essen");
		City berlin = new City("Berlin");
		City frankfurt = new City("Frankfurt");
		City bonn = new City("Bonn");
		City newtown = new City("Newtown");
		new Buildings();
		new Building(Buildings.WOOD_FACTORY, essen);
		new Building(Buildings.BOOK_FACTORY, frankfurt);
		Vehicle karl = new Vehicle(new VehicleSpecs(5,20), essen);
        Itinerary itinerary = new Itinerary();
        Resource[] input = {new Resource(Resources.WOOD,20)};
        ItineraryItem item = new ItineraryItem(essen, input);
//        item.setWaitForFull(true);
		itinerary.add(item);
        itinerary.add(new ItineraryItem(bonn, null));
        itinerary.add(new ItineraryItem(newtown, null));
        itinerary.add(new ItineraryItem(berlin, null));
        itinerary.add(new ItineraryItem(frankfurt, null));
        
        karl.setItinerary(itinerary);
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Game.getInstance().start();
		
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
//		Game.getInstance().start(2000);
		primaryStage.show();

		
	}




	
	
}
