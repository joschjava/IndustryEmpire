package gui;

import java.util.ArrayList;

import game.City;
import game.Position;
import game.Vehicle;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import mainpack.Const;



@DefaultProperty("extension")
public class WorldPane extends StackPane{

	/** Contains cities and static objects	 */
	private Pane staticPane = new Pane();
	
	/** Contains moving objects like vehicles */
	private Pane dynamicPane = new Pane();
	
	public WorldPane(){
		super();
		this.setMinWidth(Const.MAPSIZE_X*1.1);
		this.setMinHeight(Const.MAPSIZE_Y*1.1);


		dynamicPane.setPickOnBounds(false);
		
		
		this.getChildren().add(staticPane);
		this.getChildren().add(dynamicPane);
	
		
		placeObjects();
//		this.setCursor();

		String image = WorldPane.class.getResource("/mud.png").toExternalForm();
		this.setStyle("-fx-background-image: url('" + image + "'); " +
		           "-fx-background-position: center center; ");
		
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
		dynamicPane.getChildren().add(newPane);
	}
	
	public void addNewCity(City city) {
		Pane newPane = new GuiCity(city).getPane();
		staticPane.getChildren().add(newPane);
	}
	
}
