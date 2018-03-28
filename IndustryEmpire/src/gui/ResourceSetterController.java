package gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.NumberStringConverter;
import mainpack.Const;

public class ResourceSetterController {

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
    	btInc.setOnAction(new EventHandler<ActionEvent>() {
    		@Override public void handle(ActionEvent e) {
    			incAmount();
    		}
    	});
    	
    	btDec.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				decAmount();
			}
		});
        NumberStringConverter converter = new NumberStringConverter();
    	lbAmount.textProperty().bindBidirectional(amount, converter);
    }
	
    private void incAmount() {
		if(amount.get() < Const.MAX_RES_CHOICE) {
			amount.set(getAmount()+1);
		}
    }
    
	private void decAmount() {
		if(amount.get() > 0) {
			amount.set(getAmount()-1);
		}
	}
    
	public void setImage(Image image) {
		resourceImage.setImage(image);
	}
	
    private void closeStageFromEvent(ActionEvent actionEvent) {
        Node  source = (Node)  actionEvent.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        EventHandler<WindowEvent> onCloseRequest = stage.getOnCloseRequest();
        if(onCloseRequest != null) {
        	onCloseRequest.handle(null);
        }
        stage.close();
    }

    public IntegerProperty amountProperty() {
    	return amount;
    }
    
	public int getAmount() {
		return amount.get();
	}
    
}
