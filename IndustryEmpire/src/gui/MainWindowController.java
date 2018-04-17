package gui;

import game.Player;
import game.Vehicle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {

	
	@FXML
	public Button btBuyVehicle;
	@FXML
	public Button btDebug;
	
	@FXML
	public WorldPane worldPane;
	
    @FXML
    public void initialize() {
    	btBuyVehicle.setOnAction(new EventHandler<ActionEvent>() {
    		@Override public void handle(ActionEvent e) {
    			Vehicle vehicle = MenuDialog.showBuyVehicle(null);
    			if(vehicle != null) {
    				worldPane.addNewVehicle(vehicle);
    			}
    		}
    	});
    	btDebug.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Player.getInstance().chgMoneyValueBy(1000);
			}
		});
    	
    }
	
}
