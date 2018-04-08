package objects;

import java.io.IOException;
import java.net.URL;

import gui.ResourceLoadController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mainpack.IndustryMain;

public class DialogObject<T, U> {

	private T controller;
	private U rootLayout;
	
	DialogObject(String fxmlFile){
        FXMLLoader loader = new FXMLLoader();
        URL res = IndustryMain.class.getResource("/"+fxmlFile);
        loader.setLocation(res);
        VBox rootLayout = null;
		try {
			rootLayout = (VBox) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller = loader.getController();
	}
	
	
	
}
