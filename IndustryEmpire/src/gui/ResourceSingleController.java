package gui;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;
import mainpack.Const;

public class ResourceSingleController {

	@FXML
	ImageView resourceImage;
	
	@FXML
	Button btInc;
	
	@FXML
	Button btDec;
	
	@FXML
	Label lbAmount;
	
	IntegerProperty amount = new SimpleIntegerProperty(0);
	
    @FXML
    public void initialize() {
    	btInc.setOnMouseClicked((event) -> {
    		incAmount(getChgAmount(event));
    	});
    	
    	btDec.setOnMouseClicked((event) -> {
    		decAmount(getChgAmount(event));
    	});
    	
    	
        NumberStringConverter converter = new NumberStringConverter();
    	lbAmount.textProperty().bindBidirectional(amount, converter);
    }
	
    private void incAmount() {
    	incAmount(1);
    }
    
    private void incAmount(int chg) {
//    	if(leftLoad != null) {
//        	int leftLoadInt = leftLoad.intValue();
//        	if(leftLoadInt > chg) {
//        		chg = leftLoadInt;
//        	}
//    	} else {
//    		System.err.println("Left load not set! Use setLeftLoadBinding in "+ResourceSingleController.class.getName());
//    	}

    	int newAmount = getAmount()+chg;
		if(newAmount > Const.MAX_RES_CHOICE) {
			newAmount = Const.MAX_RES_CHOICE;
		} 
		amount.set(newAmount);
    }
    
    private void decAmount() {
    	decAmount(1);
    }
    
	private void decAmount(int chg) {		
    	int newAmount = getAmount()-chg;
		if(newAmount < 0) {
			newAmount = 0;
		} 
		amount.set(newAmount);
	}
	
	/**
	 * Returns the change of value depending on which key is pressed
	 * @param event Mouseevent
	 * @return int value of amount
	 */
	private int getChgAmount(MouseEvent event) {
		int chg = 1;
		if(event.isControlDown()) {
			chg = Const.RES_AMOUNT_CTRL;
		} else if(event.isShiftDown()) {
			chg = Const.RES_AMOUNT_SHIFT;
		}
		return chg;
	}
	
	public void setAmount(int value) {
		System.out.println("Amount: "+value);
		amount.set(value);
	}
	
	public void setImage(Image image) {
		resourceImage.setImage(image);
	}

    public IntegerProperty amountProperty() {
    	return amount;
    }
    
	public int getAmount() {
		return amount.get();
	}
    
}
