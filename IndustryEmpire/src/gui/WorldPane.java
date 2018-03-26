package gui;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import game.City;
import game.Position;
import game.Vehicle;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;



@DefaultProperty("extension")
public class WorldPane extends Pane{

	public WorldPane() throws MalformedURLException{
		super();
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
