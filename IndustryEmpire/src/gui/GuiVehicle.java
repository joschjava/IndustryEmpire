package gui;

import java.awt.Point;
import java.io.File;

import game.Vehicle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class GuiVehicle {

	private Text text;
	private Pane pane;
	private Vehicle vehicle;
	private ImageView iv;
	Image image;
	Image image_m;

	
	public GuiVehicle(Vehicle vehicle){
		this.vehicle = vehicle;
		pane = new Pane();
		File file = new File("graphics/truck.png");
		File file_m = new File("graphics/truck_m.png");
		image = new Image(file.toURI().toString(),20,20, true, true);
		image_m = new Image(file_m.toURI().toString(),20,20, true, true);
		iv = new ImageView();
		iv.setImage(image);
		iv.relocate(0,0);
		text = new Text(vehicle.toString());
		text.relocate(0, iv.getLayoutY()-20);
		pane.getChildren().addAll(iv, text);
		vehicle.setObserver(this);
		updatePosition(vehicle.getPosition(),0);
	}
	
	public void updatePosition(Point p, double angleDeg) {
		pane.relocate(p.getX(), p.getY());
		text.setText(vehicle.toString());
		if(angleDeg>180) {
			angleDeg -= 360;
			setFlippedVehicle(true);
		} else {
			setFlippedVehicle(false);
		}
		iv.setRotate(angleDeg);
	}
	
	public void setFlippedVehicle(boolean isFlipped) {
		if(isFlipped) {
			if(iv.getImage() != image_m) {
				iv.setImage(image_m);
			}
		} else {
			if(iv.getImage() != image) {
				iv.setImage(image);
			}
		}
	}
	
	public Pane getPane() {
		return pane;
	}
	
}
