package mainpack;

import game.Game;
import game.Resources;
import gui.MenuDialog;
import gui.ResourceSetter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// Performance enhancer:
// Update factories only when it is interacted with
// Image is being assigned to GuiVehicle every time

public class IndustryMainTest extends Application {

	Pane route = new Pane();
	
	public static void main(String[] args) {
		new Resources();
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
//		MenuDialog.showResourceLoadDialog(null);
//		ResourceSetter resSetter = new ResourceSetter(Resources.GOLD);

//		Scene scene = new Scene(resSetter);
//		primaryStage.setScene(scene);
//		Game.getInstance().start();
//		primaryStage.show();

		
	}




	
	
}
