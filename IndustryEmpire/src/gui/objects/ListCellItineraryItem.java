package gui.objects;

import java.io.File;

import game.ItineraryItem;
import game.Resource;
import gui.WorldPane;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mainpack.Const;

public class ListCellItineraryItem extends ListCell<ItineraryItem> {

	
	
		private VBox cellContainer;
		private HBox resContainer;
		private Label lb;
		private Font font = new Font("Courier New", 15);
		private ImageView emptyIv = new ImageView();
		
		public ListCellItineraryItem(){
			String imageResource = ListCellItineraryItem.class.getResource("/empty.png").toExternalForm();
			Image image = new Image(imageResource);
			emptyIv.setImage(image);
			emptyIv.setPreserveRatio(true);
			emptyIv.setFitHeight(20);
		}
		
		@Override
		protected void updateItem(ItineraryItem item, boolean empty) {
			super.updateItem(item, empty);
		     if (empty || item == null) {
		         setText(null);
		         setGraphic(null);
		     } else if(item != null) {
//		    	 lb.textFillProperty().bind(Bindings.when(item.nextDestinationProperty()).then(Color.ORANGE));
		    	 item.nextDestinationProperty().addListener(
		    			 (observable, oldvalue, newValue) ->{
		    				 setHighlightAsNextDestination(newValue);
    		    });
		    	cellContainer = new VBox();
				initCityLabel(item);
				initResources(item);
				setGraphic(cellContainer);
			}
		}

		public void setHighlightAsNextDestination(boolean highlight) {
		    	if(highlight) {
		    		lb.setTextFill(Color.ORANGE);
		    	} else {
		    		lb.setTextFill(Color.BLACK);
		    	}
		}
		
		private void initCityLabel(ItineraryItem item) {
			lb = new Label(item.getDestination().getName());
			lb.setFont(font);
			cellContainer.getChildren().add(lb);
		}

		private void initResourceContainer() {
			resContainer = new HBox();
			resContainer.setSpacing(10);
			resContainer.setAlignment(Pos.CENTER_LEFT);
			cellContainer.getChildren().add(resContainer);
		}

		private void initResources(ItineraryItem item) {
			initResourceContainer();
			Resource[] resource = item.getLoad();
			if(resource != null) {
				for (int i = 0; i < resource.length; i++) {
					ImageView iv = new ImageView(resource[i].getSpec().getImage());
					iv.setPreserveRatio(true);
					iv.setFitWidth(20);
					resContainer.getChildren().add(iv);
				}
			} else {
				resContainer.getChildren().add(emptyIv);
			}
		}
		
}