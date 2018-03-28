package gui;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mainpack.IndustryMain;
import objects.InputOutputResources;

public class MenuDialog {

	public static InputOutputResources showResourceLoadDialog() {
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
		Scene scene = new Scene(rootLayout);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.showAndWait();
		return controller.getInputOutputResources();
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
