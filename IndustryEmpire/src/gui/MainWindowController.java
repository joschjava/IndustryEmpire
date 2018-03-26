package gui;

import game.Vehicle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {

	
	@FXML
	public Button btBuyVehicle;
	
	@FXML
	public WorldPane worldPane;
	
    @FXML
    public void initialize() {
    	btBuyVehicle.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Vehicle vehicle = VehiclePane.show();
				if(vehicle != null) {
					worldPane.addNewVehicle(vehicle);
				}
			}
		});
    }
	
}
