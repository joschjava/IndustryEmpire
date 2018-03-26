package gui;

import game.Resource;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mainpack.Balance;

public class ResourceContainer extends VBox{

	Text txtAmount;
	
	ResourceContainer(Resource res){
    	Image image = res.getSpec().getImage();
    	this.setAlignment(Pos.CENTER);
    	ImageView iv = new ImageView(image);
    	double amount = res.getAmount();
    	txtAmount = new Text(String.format("%."+Balance.DISPLAY_DIGITS+"f",amount));
//    	txtAmount.textProperty().bind(observable);
    	iv.setPreserveRatio(true);
    	iv.setFitHeight(50);
    	this.getChildren().addAll(iv,txtAmount);
	}

}
