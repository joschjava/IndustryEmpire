package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import mainpack.Const;

public class DriverList extends Application implements TickListener{

	static ObservableList<Driver> driverList;
	
	static int tickCounter = 0;
	static Button btTick;
	static TextField tf;
	
	/** Minimal drivers on the list	 */
	private static final int MIN_DRIVERS = Const.MIN_DRIVERS;
	
	/** Maximal drivers on the list, be aware that this might also be limited by {@link Const#MIN_DRIVER_TIME} and {@link Const#MAX_DRIVER_TIME}	 */
	private static final int MAX_DRIVERS = Const.MAX_DRIVERS;
		
	private static HistoryManager history = new HistoryManager();
	
    @Override
    public void start(Stage hauptStage) {

        ListView<Driver> list = new ListView<Driver>();
        
        VBox pane = new VBox();
        pane.getChildren().add(list);
        Button bt = new Button("Add Driver");
        btTick = new Button("Tick");
        pane.getChildren().add(bt);
        pane.getChildren().add(btTick);
        tf = new TextField();
        tf.setText("-1");
        pane.getChildren().add(tf);
        
       
        driverList = FXCollections.observableArrayList(Driver.extractor());
        list.setItems(driverList);
        for(int i=0;i<MIN_DRIVERS;i++) {
        	driverList.add(new Driver());
        }
        bt.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		driverList.add(new Driver());
        	}
        });
        
        btTick.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

//            	driverList.stream()
//            	.filter((driver) -> driver.decTime())
//            	.forEach((driver) -> driverList.remove(driver));
//            	modifyDrivers();
            }
        });
        Scene scene = new Scene(pane);
        hauptStage.setScene(scene);
        hauptStage.sizeToScene();
        hauptStage.show();
		Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(1000),
        ae -> DriverList.modifyDrivers()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
    }

    public static void modifyDrivers() {
    	tf.setText(String.valueOf(tickCounter++));
        Platform.runLater(new Runnable() {
            @Override public void run() {  
            	//Remove drivers, which time is up / hired by someone else
            	driverList.removeIf((driver) -> driver.decTime());
            	if((Math.random() < 0.4 && driverList.size() < MAX_DRIVERS) || driverList.size()< MIN_DRIVERS) {
             		driverList.add(new Driver());
            	}
            	history.addNumber(driverList.size());
            }
        });

    }
    
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void onTick() {
		modifyDrivers();
	}
	
}
