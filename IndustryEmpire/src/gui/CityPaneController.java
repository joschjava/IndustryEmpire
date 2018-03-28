package gui;

import game.City;
import game.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CityPaneController {
	
	@FXML
	HBox resourceDisplay;

	@FXML
	Label lbCityName;
	
    @FXML
    public void initialize() {
    	
    }
	
    public void setCityName(City city) {
    	lbCityName.setText(city.getName());
    }
 
    public void addResource(Resource res) {
    	//TODO: Possible Performance inhancement: Load small image before and scale at loading
    	resourceDisplay.getChildren().add(new ResourceContainer(res));
    }
    
}

