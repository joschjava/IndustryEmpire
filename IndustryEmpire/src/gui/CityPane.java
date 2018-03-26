package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import game.City;
import game.Resource;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mainpack.IndustryMain;

public class CityPane {

	
	public static void show(City city) {
        FXMLLoader loader = new FXMLLoader();
        URL res = IndustryMain.class.getResource("/citydialog.fxml");
        loader.setLocation(res);
        HBox rootLayout = null;
		try {
			rootLayout = (HBox) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CityPaneController controller = loader.getController();
		ArrayList<Resource> resources = city.getAllResources();
		System.out.println(resources.size());
		resources.forEach((resource)->{
			controller.addResource(resource);
		});
		
		Scene scene = new Scene(rootLayout);
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.showAndWait();
	}
}
