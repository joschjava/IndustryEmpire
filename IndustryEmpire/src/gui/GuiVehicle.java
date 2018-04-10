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
	private ImageView iv;
	private ImageView ivFuel;
	private static Image image = null;
	private static Image image_m = null;
	private static Image fuelImage = null;
	private Glow highlightEffect;

	
	public GuiVehicle(Vehicle vehicle){
		pane = new Pane();

        
		File file = new File("graphics/truck.png");
		File file_m = new File("graphics/truck_m.png");
		
		
		if(fuelImage == null) {
			String fuelImageRes = GuiVehicle.class.getResource("/gas-pump.png").toExternalForm();
			fuelImage = new Image(fuelImageRes);
		}
		ivFuel = new ImageView(fuelImage);
		ivFuel.setPreserveRatio(true);
		ivFuel.setFitHeight(15);
		ivFuel.relocate(0, -20);
		if(image == null || image_m == null) {
			image = new Image(file.toURI().toString(),20,20, true, true);
			image_m = new Image(file_m.toURI().toString(),20,20, true, true);
		}
		iv = new ImageView();
		iv.setImage(image);
		iv.relocate(0,0);
		
		iv.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               highlight();
            }
        });
        
		iv.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               unhighlight();
            }
        });
        
		iv.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               MenuDialog.showBuyVehicle(vehicle);
            }
        });
        
		iv.setCursor(Cursor.OPEN_HAND);
		
		//Attach guiVehicle to guiVehicle Position
		pane.layoutXProperty().bind(vehicle.xProperty());
		pane.layoutYProperty().bind(vehicle.yProperty());
		
		//Let vehicle disappear when in city
		BooleanBinding vehicleDriving = Bindings.when(
				vehicle.statusProperty().isEqualTo(Vehicle.Status.DRIVING)
				.or(vehicle.statusProperty().isEqualTo(Vehicle.Status.REFUEL)))
				.then(true).otherwise(false);
		iv.visibleProperty().bind(vehicleDriving);
		iv.mouseTransparentProperty().bind(Bindings.not(vehicleDriving));

		// Show fuel symbol when refueling
		BooleanBinding vehicleRefuel = Bindings.when(
				vehicle.statusProperty().isEqualTo(Vehicle.Status.REFUEL))
				.then(true).otherwise(false);
		ivFuel.visibleProperty().bind(vehicleRefuel);
		ivFuel.setMouseTransparent(true);
		
		//Update Rotation of vehicle
		vehicle.angleProperty().addListener((observable, oldvalue, newValue)->{
			updateRotation(newValue.doubleValue());
		});
		
		pane.getChildren().add(iv);
		pane.getChildren().add(ivFuel);
	}
	
	// CHANGE TO X Y AND PROPERTY LISTENER
	/**
	 * @deprecated
	 * @param p
	 * @param angleDeg
	 */
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
	
	private void updateRotation(double angleDeg) {
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
