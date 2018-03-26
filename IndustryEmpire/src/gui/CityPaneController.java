package gui;

import game.Resource;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CityPaneController {
	
	@FXML
	HBox resourceDisplay;

    @FXML
    public void initialize() {
    	
    }
	
 
    public void addResource(Resource res) {
    	//TODO: Possible Performance inhancement: Load small image before and scale at loading
    	resourceDisplay.getChildren().add(new ResourceContainer(res));
    }
    
}

