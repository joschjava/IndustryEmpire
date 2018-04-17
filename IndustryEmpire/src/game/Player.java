package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class Player {

	private IntegerProperty money = new SimpleIntegerProperty(0);
	private static Player player = null;
	private Timeline moneyAnimation;
	
	private Player(){
		
	}
	
	public static Player getInstance() {
		if(player == null) {
			player = new Player();
		}
		return player; 
	}
	
	public IntegerProperty moneyProperty() {
		return money;
	}
	
	public int getMoney() {
		return money.get();
	}
	
	public void chgMoneyValueBy(int amount) {
//		if(moneyAnimation.getStatus() == Animation.Status.RUNNING) {
//			
//		}
//		KeyValue newMoney = new KeyValue(money, money.get()+amount);
//    	moneyAnimation = new Timeline(
//				new KeyFrame(Duration.millis(0)),
//				new KeyFrame(new Duration(1000), newMoney)
//				);
//    	moneyAnimation.play();
		
		money.set(money.get()+amount);
	}
	
}
