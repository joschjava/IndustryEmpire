package gui;

import java.util.ArrayList;

import game.City;
import game.Position;
import game.Vehicle;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;



@DefaultProperty("extension")
public class WorldPane extends Pane{

	public WorldPane(){
		super();
		placeObjects();
	}
	

	public ObservableList<Node> getExtension() {
		return getChildren();
	}
	
	private void placeObjects() {
		ArrayList<Position> objects = Position.getPositions();
        objects.stream().forEach(pos -> {
        	if (pos instanceof City) {
				City city = (City) pos;
				addNewCity(city);
        	} else if (pos instanceof Vehicle) {
        		Vehicle vehicle = (Vehicle) pos;
        		addNewVehicle(vehicle);
        	}
        });
	}
	
	public void addNewVehicle(Vehicle vehicle) {
		Pane newPane = new GuiVehicle(vehicle).getPane();
		this.getChildren().add(newPane);
	}
	
	public void addNewCity(City city) {
		Pane newPane = new GuiCity(city).getPane();
		this.getChildren().add(newPane);
	}
	
}
