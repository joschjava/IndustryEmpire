package gui;

import java.awt.Point;
import java.io.File;

import game.Vehicle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GuiVehicle {

//	private Text text;
	private Pane pane;
	private Vehicle vehicle;
	private ImageView iv;
	Image image;
	Image image_m;
	private Glow highlightEffect;

	
	public GuiVehicle(Vehicle vehicle){
		this.vehicle = vehicle;
		pane = new Pane();
        pane.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               highlight();
            }
        });
        
        pane.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               unhighlight();
            }
        });
        pane.setCursor(Cursor.OPEN_HAND);
        
		File file = new File("graphics/truck.png");
		File file_m = new File("graphics/truck_m.png");
		image = new Image(file.toURI().toString(),20,20, true, true);
		image_m = new Image(file_m.toURI().toString(),20,20, true, true);
		iv = new ImageView();
		iv.setImage(image);
		iv.relocate(0,0);
		
		//Let vehicle disappear when in city
		BooleanBinding vehicleDriving = Bindings.when(vehicle.statusProperty().isEqualTo(Vehicle.DRIVING)).then(true).otherwise(false);
		iv.visibleProperty().bind(vehicleDriving);
		pane.mouseTransparentProperty().bind(Bindings.not(vehicleDriving));
//		iv.visibleProperty()
//		text = new Text(vehicle.toString());
//		text.relocate(0, iv.getLayoutY()-20);
		pane.getChildren().add(iv);
		vehicle.setObserver(this);
		updatePosition(vehicle.getPosition(),0);
	}
	
	// CHANGE TO X Y AND PROPERTY LISTENER
	public void updatePosition(Point p, double angleDeg) {
		pane.relocate(p.getX(), p.getY());
//		text.setText(vehicle.toString());
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
	
	private void highlight() {
		if(highlightEffect ==  null) {
			highlightEffect = new Glow(1.0);
			ColorAdjust red = new ColorAdjust(0.0, 1.0, 0.0, 0.0);
			highlightEffect.setInput(red);
		}
		iv.setEffect(highlightEffect);
	}
	
	private void unhighlight() {
		iv.setEffect(null);
	}
	
	public Pane getPane() {
		return pane;
	}
	
}
