package gui;

import java.util.ArrayList;

import game.Resource;
import game.ResourceSpec;
import game.Vehicle;
import game.VehicleSpecs;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
	public ProgressBar vehLoad;
	
//	static DoubleProperty progressProp;
//	NumberBinding sumResources;
//	NumberBinding progress;
//	boolean first = true;
	
	int load = 10;

private VehicleSpecs vehicleSpecs;
	
    @FXML
    public void initialize() {
    	ArrayList<ResourceSpec> allRes = ResourceSpec.getAllResourceSpecs();
    	
    	allRes.forEach((resSpec)->{
    		Image im = resSpec.getImage();
    		ImageView iv = new ImageView(im);
//    		iv.getProperties().put("ResourceSpec", resSpec);
    		iv.setPreserveRatio(true);
    		iv.setFitHeight(50);
            iv.setOnMouseClicked(new EventHandler<MouseEvent>(){
                @Override
                public void handle(MouseEvent event) {
                	addResourceToLoad(resSpec);
                }
            });
    		flowAllRes.getChildren().add(iv);
    	});
        
		btClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				closeStageFromEvent(e);
			}
		});
        
    }
    
    private void addResourceToLoad(ResourceSpec resSpec) {
    	ResourceSetter resourceSetter = new ResourceSetter(resSpec);
    	flowInputRes.getChildren().add(resourceSetter);
    }

    
	public Resource[] getResources() {
		FilteredList<Node> resList = flowInputRes.getChildren().filtered((child) -> {
			if(child instanceof ResourceSetter) {
				return true;
			} else {
				return false;
			}
		});
		int numRes = resList.size();
		Resource[] loadRes = new Resource[numRes];
		int counter = 0;
		for (Node node : resList) {
			ResourceSetter resSetter = (ResourceSetter) node;
			loadRes[counter++] = resSetter.getResource();
		}
		return loadRes;
	}

	public void setVehicleSpecs(VehicleSpecs vehicleSpecs) {
		this.vehicleSpecs = vehicleSpecs;
	}
	
    private void closeStageFromEvent(ActionEvent actionEvent) {
        Node  source = (Node)  actionEvent.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        EventHandler<WindowEvent> onCloseRequest = stage.getOnCloseRequest();
        if(onCloseRequest != null) {
        	onCloseRequest.handle(null);
        }
        stage.close();
    }

	 
	
//	System.out.println(progressProp.doubleValue());
//	if(first) {
//		progressProp = vehLoad.progressProperty();
//		sumResources = resourceSetter.amountProperty().add(0);
////		sumResources.add(resourceSetter.amountProperty());
//    	progress = sumResources.divide((double)load);
//    	progressProp.bind(progress);
//    	first = false;
//	} else {
//		System.out.println("else");
//		progress = sumResources.add(resourceSetter.amountProperty()).divide((double)load);
//		progressProp.bind(progress);
//	}
//	sumResources.getDependencies().forEach(System.out::println);
	
}
