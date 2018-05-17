package gui;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import game.City;
import game.Game;
import game.Player;
import game.Position;
import game.Vehicle;
import gui.objects.DateLabel;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import mainpack.Const;



@DefaultProperty("extension")
public class WorldPane extends StackPane{

	/** Contains cities and static objects	 */
	private Pane staticPane = new Pane();
	
	/** Contains moving objects like vehicles */
	private Pane dynamicPane = new Pane();
	
	/** Contains descriptions such as money etc that are printed on top of screen */
	private VBox overlay = new VBox();
	
	public WorldPane(){
		super();
		this.setMinWidth(Const.MAPSIZE_X*1.1);
		this.setMinHeight(Const.MAPSIZE_Y*2);

		dynamicPane.setPickOnBounds(false);
		overlay.setPickOnBounds(false);
		overlay.setPadding(new Insets(10));

		Label money = new Label();
		DateLabel date = new DateLabel();
		Player player = Game.getInstance().getPlayer();
		overlay.getChildren().add(money);
		overlay.getChildren().add(date);
		money.setStyle("-fx-background-color: #FFFFFF");
		date.setStyle("-fx-background-color: #FFFFFF");
		NumberStringConverter moneyConverter = new NumberStringConverter() {
			
			@Override
			public String toString(Number value) {
				NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
				String formatedValue = numberFormat.format(value.intValue());
				return String.format(Const.CURRENCY_SYMBOL+" "+formatedValue);
			}
		};
		
		money.textProperty().bindBidirectional(player.moneyProperty(), moneyConverter);
		
		
		this.getChildren().add(staticPane);
		this.getChildren().add(dynamicPane);
		this.getChildren().add(overlay);
		
		
		placeObjects();
//		this.setCursor();

		String image = WorldPane.class.getResource("/mud.png").toExternalForm();
		this.setStyle("-fx-background-image: url('" + image + "'); " +
		           "-fx-background-position: center center; ");
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
