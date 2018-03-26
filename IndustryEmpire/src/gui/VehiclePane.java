package gui;

import java.io.IOException;
import java.net.URL;

import game.Vehicle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mainpack.IndustryMain;

public class VehiclePane {

	public static Vehicle show() {
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
		Scene scene = new Scene(rootLayout);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.showAndWait();
		return controller.getVehicle();
	}
	
}
