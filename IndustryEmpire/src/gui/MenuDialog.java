package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import game.City;
import game.ItineraryItem;
import game.Resource;
import game.Vehicle;
import game.VehicleSpecs;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainpack.IndustryMain;

public class MenuDialog {

	public static Resource[] showResourceLoadDialog(VehicleSpecs vehicleSpecs, ItineraryItem itinItem) {
		String fxmlFile = "resourceloaddialog.fxml";
        FXMLLoader loader = new FXMLLoader();
        URL res = IndustryMain.class.getResource("/"+fxmlFile);
        loader.setLocation(res);
        VBox rootLayout = null;
		try {
			rootLayout = (VBox) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ResourceLoadController controller = loader.getController();
		controller.setVehicleSpecs(vehicleSpecs);
		controller.setItineraryItem(itinItem);
		Scene scene = new Scene(rootLayout);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.showAndWait();
		return controller.getResources();
	}
	
	public static void showCity(City city) {
        FXMLLoader loader = new FXMLLoader();
        URL res = IndustryMain.class.getResource("/citydialog.fxml");
        loader.setLocation(res);
        VBox rootLayout = null;
		try {
			rootLayout = (VBox) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CityPaneController controller = loader.getController();
		controller.setCity(city);
		ArrayList<Resource> resources = city.getAllResources();
		System.out.println(resources.size());
		resources.forEach((resource)->{
			controller.addResource(resource);
		});
		
		Scene scene = new Scene(rootLayout);
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	public static Vehicle showBuyVehicle(Vehicle vehicle) {
        FXMLLoader loader = new FXMLLoader();
        URL res = IndustryMain.class.getResource("/vehicledialog.fxml");
        loader.setLocation(res);
        VBox rootLayout = null;
		try {
			rootLayout = (VBox) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		VehiclePaneController controller = loader.getController();
		if(vehicle != null) {
			controller.loadVehicle(vehicle);
		}
		Scene scene = new Scene(rootLayout);
		Stage stage = new Stage();
//		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.showAndWait();
		return controller.getVehicle();
	}
	
	
//	private static <T> T showDialog(String fxmlFile, boolean getController) {
//		T controller = null;
//        FXMLLoader loader = new FXMLLoader();
//        URL res = IndustryMain.class.getResource("/"+fxmlFile);
//        loader.setLocation(res);
//        VBox rootLayout = null;
//		try {
//			rootLayout = (VBox) loader.load();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		if(getController) {
//			controller = loader.getController();
//		} 
//		return controller;
//	}
	
	
}
