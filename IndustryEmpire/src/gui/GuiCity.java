package gui;

import game.City;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class GuiCity {

	private Circle circle;
	private Text text;
	private Pane pane;
	private City city;
	
	
	public GuiCity(City city){
		this.city = city;
		pane = new Pane();
		circle = new Circle(10, Color.BLUE);
		circle.relocate(city.getX(), city.getY());
		text = new Text(city.getName());
		text.relocate(city.getX(), city.getY() + 30);
		pane.getChildren().addAll(circle, text);
	}
	
	public Pane getPane() {
		return pane;
	}
	
}
