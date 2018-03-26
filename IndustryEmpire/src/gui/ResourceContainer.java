package gui;

import java.text.NumberFormat;

import com.sun.javafx.css.converters.StringConverter;

import game.Resource;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import mainpack.Const;

public class ResourceContainer extends VBox{

	Text txtAmount;
	
	ResourceContainer(Resource res){
    	Image image = res.getSpec().getImage();
    	this.setAlignment(Pos.CENTER);
    	ImageView iv = new ImageView(image);
    	double amount = res.getAmount();
    	txtAmount = new Text(String.format("%."+Const.DISPLAY_DIGITS+"f",amount));
    	StringProperty txtProperty = txtAmount.textProperty();

        NumberStringConverter converter = new NumberStringConverter() {
        	@Override
        	public String toString(Number value) {
				return String.format("%."+Const.DISPLAY_DIGITS+"f",value.doubleValue());
        	}
        };
        Bindings.bindBidirectional(txtProperty, res.AmountProperty(), converter);


    	iv.setPreserveRatio(true);
    	iv.setFitHeight(50);
    	this.getChildren().addAll(iv,txtAmount);
	}

}
