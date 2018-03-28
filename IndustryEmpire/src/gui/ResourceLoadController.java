package gui;

import java.util.ArrayList;

import game.ResourceSpec;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import objects.InputOutputResources;

public class ResourceLoadController {

	
	@FXML
	public FlowPane flowAllRes;
	
	@FXML
	public FlowPane flowInputRes;
	
	@FXML
	public FlowPane flowOutputRes;
	
	@FXML
	public Button btClose;
	
    @FXML
    public void initialize() {
    	ArrayList<ResourceSpec> allRes = ResourceSpec.getAllResourceSpecs();
    	allRes.forEach((resSpec)->{
    		Image im = resSpec.getImage();
    		ImageView iv = new ImageView(im);
    		iv.setPreserveRatio(true);
    		iv.setFitHeight(50);
    		flowAllRes.getChildren().add(iv);
    	});
    }

	public InputOutputResources getInputOutputResources() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
