package gui;

import java.util.ArrayList;

import game.Building;
import game.City;
import game.Resource;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CityPaneController {
	
	@FXML
	HBox resourceDisplay;

	@FXML
	Label lbCityName;
	
	@FXML
	HBox hbFactory;
	
    @FXML
    public void initialize() {
    	
    }
	
    public void setCity(City city) {
    	lbCityName.setText(city.getName());
    	ArrayList<Building> buildings = city.getBuildingList();
    	HBox tooltip = new HBox();
    	tooltip.getChildren().add(new Label("Hello!"));
//    	tooltip.setVisible(false);
//    	hbFactory.getChildren().add(tooltip);
    	Stage stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT);
		
    	buildings.forEach((building) -> {
    		System.out.println(building);
    		ImageView iv = new ImageView(building.getBuildingSpec().getImage());
    		iv.setPreserveRatio(true);
    		iv.setFitHeight(50);
//    		Tooltip.install(iv, new Tooltip("Test"));
    		Scene scene = new Scene(getResourceTooltip(building));
    		
    		iv.getProperties().put("tooltip", stage);
    		iv.setOnMouseEntered((event) -> {
    			stage.setScene(scene);
	    		stage.setX(event.getScreenX());
	    		stage.setY(event.getScreenY());
	    		stage.show();
    		});
    		
    		iv.setOnMouseExited((event) -> {
    			stage.close();
    		});
    		hbFactory.getChildren().add(iv);
    	});
    }
 
    public HBox getResourceTooltip(Building building) {
    	HBox tooltip = new HBox();
    	tooltip.setSpacing(5);
    	VBox input = new VBox();
    	input.getChildren().add(new Label("Input"));

    	VBox output = new VBox();
    	output.getChildren().add(new Label("Output"));
    	
    	building.getBuildingSpec().getInputRes().forEach(res -> {
    		ImageView imageView = new ImageView(res.getSpec().getImage());
    		imageView.setPreserveRatio(true);
    		imageView.setFitWidth(50);
    		input.getChildren().add(imageView);
    	});
    	
    	building.getBuildingSpec().getOutputRes().forEach(res -> {
    		ImageView imageView = new ImageView(res.getSpec().getImage());
    		imageView.setPreserveRatio(true);
    		imageView.setFitWidth(50);
			output.getChildren().add(imageView);
    	});
    	
    	tooltip.getChildren().addAll(input,output);
    	return tooltip;
    }
    
    public void addResource(Resource res) {
    	//TODO: Possible Performance inhancement: Load small image before and scale at loading
    	resourceDisplay.getChildren().add(new ResourceContainer(res));
    }
    
    
}

