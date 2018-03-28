package gui;

import java.util.ArrayList;

import game.City;
import game.Position;
import game.Vehicle;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import mainpack.Const;



@DefaultProperty("extension")
public class WorldPane extends Pane{

	public WorldPane(){
		super();
		this.setMinWidth(Const.MAPSIZE_X*1.1);
		this.setMinHeight(Const.MAPSIZE_Y*1.1);
		placeObjects();

//		File bg = new File("/graphics/mud.png");
//		BackgroundImage myBI;
//		ImageView iv = new ImageView(new Image(bg.toURI().toString()));
//		myBI = new BackgroundImage(new Image(bg.toURI().toString(),32,32,false,true),
//		        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
//		          new BackgroundSize(500, 500, false, false, false,true));
		//then you set to your node
//		this.setBackground(Background.EMPTY);
//		getChildren().add(iv);
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
