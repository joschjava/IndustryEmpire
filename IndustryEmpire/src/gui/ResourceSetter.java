package gui;

import java.io.IOException;
import java.net.URL;

import game.Resource;
import game.ResourceSpec;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mainpack.IndustryMain;

public class ResourceSetter extends Pane{

	ResourceSingleController controller;
	ResourceSpec resSpec;
	NumberBinding leftLoad;
	
	/**
	 * Creates a pane to set a single Resource amount
	 * @param resSpec
	 */
	public ResourceSetter(ResourceSpec resSpec){
		super();
		this.resSpec = resSpec;
		createXml();
	}

	private void createXml() {
		String fxmlFile = "resourcesetter.fxml";
        FXMLLoader loader = new FXMLLoader();
        URL res = IndustryMain.class.getResource("/"+fxmlFile);
        loader.setLocation(res);
        HBox rootLayout = null;
		try {
			rootLayout = (HBox) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller = loader.getController();
		controller.setImage(resSpec.getImage());
		//TODO: This is a bit bad coding
		this.getChildren().add(rootLayout);
	}
	
	public Resource getResource() {
		double amount = controller.getAmount();
		Resource res = new Resource(resSpec, amount);
		return res;
	}
	
	public IntegerProperty amountProperty() {
		return controller.amountProperty();
	}
}
