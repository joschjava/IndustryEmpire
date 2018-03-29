package game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player {

	IntegerProperty money = new SimpleIntegerProperty(0);

	public IntegerProperty moneyProperty() {
		return money;
	}
	
	public int getMoney() {
		return money.get();
	}
	
	public void chgMoneyValueBy(int amount) {
		money.set(money.get()+amount);
	}
	
}
