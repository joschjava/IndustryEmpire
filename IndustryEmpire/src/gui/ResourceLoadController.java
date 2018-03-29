package gui;

import java.util.ArrayList;

import game.ItineraryItem;
import game.Resource;
import game.ResourceSpec;
import game.VehicleSpecs;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
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
	
	int load = 10;

private VehicleSpecs vehicleSpecs;

private ItineraryItem itinItem;

//private NumberBinding leftLoad;
	
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
    
    private void addResourceToLoad(ResourceSpec resSpec, int amount) {
    	ResourceSetter resourceSetter = new ResourceSetter(resSpec, amount);
    	flowInputRes.getChildren().add(resourceSetter);
    	bindProgressProperty();
    }
    
    private void addResourceToLoad(ResourceSpec resSpec) {
    	ResourceSetter resourceSetter = new ResourceSetter(resSpec);
    	flowInputRes.getChildren().add(resourceSetter);
    	bindProgressProperty();
    }

    //TODO: Don't do this every time
    private void bindProgressProperty() {
    	FilteredList<Node> resSetters = flowInputRes.getChildren().filtered((node) -> {
    		if(node instanceof ResourceSetter) {
    			return true;
    		} else {
    			return false;
    		} 
    	});
    	if(resSetters.size() > 0) {
    		ResourceSetter resSetter0 = ((ResourceSetter)resSetters.get(0));
    		NumberBinding num0 = resSetter0.amountProperty().add(new SimpleIntegerProperty(0));
    		NumberBinding lastBinding = num0;
    		for(int i=1;i<resSetters.size();i++) {
    			ResourceSetter resSetter = ((ResourceSetter)resSetters.get(i));
    			NumberBinding num = lastBinding.add(resSetter.amountProperty());
    			lastBinding = num;
    		}
    		NumberBinding result = lastBinding.divide((double)load);
    		vehLoad.progressProperty().bind(result);
    		vehLoad.styleProperty().bind(
    				Bindings.when(result.lessThan(1.0)).then("-fx-accent: green").otherwise("-fx-accent: red")
    		);
    	}    	
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

	public void setItineraryItem(ItineraryItem itinItem) {
		Resource[] load = itinItem.getLoad();
		if(load != null) {
			for (int i = 0; i < load.length; i++) {
				addResourceToLoad(load[i].getSpec(), (int) load[i].getAmount());
			}
		}
	}
	
}
