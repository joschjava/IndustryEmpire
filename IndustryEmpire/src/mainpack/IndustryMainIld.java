package mainpack;

import java.util.ArrayList;

import game.City;
import game.Game;
import game.Itinerary;
import game.ItineraryItem;
import game.Position;
import game.Vehicle;
import game.VehicleSpecs;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class IndustryMainIld extends Application{

	Pane route = new Pane();
	
    private double orgSceneX;
	private double orgSceneY;
	private double orgTranslateX;
	private double orgTranslateY;
	
	public static void main(String[] args) {
		City essen = new City("Essen");
		City berlin = new City("Berlin");
		City frankfurt = new City("Frankfurt");
		City bonn = new City("Bonn");
		VehicleSpecs specs = new VehicleSpecs(2, 20);
		VehicleSpecs specs2 = new VehicleSpecs(3, 20);
		Vehicle karl = new Vehicle(specs, essen);
		Vehicle karl2 = new Vehicle(specs2, berlin);
		Itinerary itinerary = new Itinerary();
		itinerary.add(new ItineraryItem(essen, null, null));
		itinerary.add(new ItineraryItem(berlin, null, null));
		itinerary.add(new ItineraryItem(frankfurt, null, null));
		karl.setItinerary(itinerary);
		
		Itinerary itinerary2 = new Itinerary();
		itinerary2.add(new ItineraryItem(essen, null, null));
		itinerary2.add(new ItineraryItem(frankfurt, null, null));
		itinerary2.add(new ItineraryItem(bonn, null, null));
        karl2.setItinerary(itinerary2);
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Hello World!");

		Pane pane = new Pane();
		placeObjects(pane);

		Timeline ticker = new Timeline(
				new KeyFrame(Duration.millis(0), ae ->  {
					Game.getInstance().tick();
					placeObjects(pane);
				}),
				 new KeyFrame(new Duration(50))
				);
		ticker.setCycleCount(Animation.INDEFINITE);
		ticker.play();
		
		StackPane root = new StackPane();
		root.getChildren().add(pane);
		root.getChildren().add(route);

		
	    addDragOption(root);
	        
		primaryStage.setScene(new Scene(root, Const.MAPSIZE_X, Const.MAPSIZE_Y));
		primaryStage.show();
	}

	private void addDragOption(StackPane root) {
		EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
	            new EventHandler<MouseEvent>() {
				@Override
	            public void handle(MouseEvent t) {
	                orgSceneX = t.getSceneX();
	                orgSceneY = t.getSceneY();
	                orgTranslateX = ((StackPane)(t.getSource())).getTranslateX();
	                orgTranslateY = ((StackPane)(t.getSource())).getTranslateY();
	            }
	        };
	         
	        EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = 
	            new EventHandler<MouseEvent>() {
	     
	            @Override
	            public void handle(MouseEvent t) {
	                double offsetX = t.getSceneX() - orgSceneX;
	                double offsetY = t.getSceneY() - orgSceneY;
	                double newTranslateX = orgTranslateX + offsetX;
	                double newTranslateY = orgTranslateY + offsetY;	                 
	                ((StackPane)(t.getSource())).setTranslateX(newTranslateX);
	                ((StackPane)(t.getSource())).setTranslateY(newTranslateY);
	            }
	        };
			root.setOnMousePressed(circleOnMousePressedEventHandler);
			root.setOnMouseDragged(circleOnMouseDraggedEventHandler);
	}

	public void updatePosition(Pane pane) {
		
	}
	
	private void placeObjects(Pane pane) {
		pane.getChildren().clear();
		ArrayList<Position> objects = Position.getPositions();
        
        objects.stream().forEach(pos -> {
        	Circle circle = null;
        	Text text = null;
        	if (pos instanceof City) {
				City city = (City) pos;
				circle = new Circle(10, Color.BLUE);
				circle.relocate(city.getX(), city.getY());
				text = new Text(city.getName());
				text.relocate(city.getX(), city.getY() + 30);
        	} else if (pos instanceof Vehicle) {
        		Vehicle vehicle = (Vehicle) pos;
				circle = new Circle(5, Color.ORANGE);
				circle.relocate(vehicle.getX(), vehicle.getY());
				text = new Text(String.valueOf(vehicle));
				text.relocate(vehicle.getX(), vehicle.getY() - 50);
				Circle c = new Circle(1, Color.BLACK);
				c.relocate(vehicle.getX(), vehicle.getY());
				route.getChildren().add(c);
				if(route.getChildren().size()>200) {
					Node n = route.getChildren().get(0);
					route.getChildren().remove(n);
				} 
        	}
        	try {
        		pane.getChildren().add(circle);
        		pane.getChildren().add(text);
        	} catch (NullPointerException e) {
        		System.err.println(pos);
        	}
        });
	}


	
	
}
