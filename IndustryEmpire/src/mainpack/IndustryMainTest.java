package mainpack;

import game.Game;
import game.Player;
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

	
	public static void main(String[] args) {
		Player.getInstance().chgMoneyValueBy(50);
		Player.getInstance().chgMoneyValueBy(50);
		System.out.println(Player.getInstance().getMoney());
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {


		
	}




	
	
}
