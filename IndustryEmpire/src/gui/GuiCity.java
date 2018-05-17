package gui;

import java.io.File;

import game.City;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class GuiCity {

	private Text text;
	private Pane pane;
	private City city;
	private ImageView iv;
	private Glow highlightEffect;
	
	public GuiCity(City city){
		this.city = city;
		pane = new Pane();
		pane.setPickOnBounds(false);
		File file = new File("graphics/city.png");

		int size = 50;
//		pane.setStyle("-fx-border-color: black");
		Image image = new Image(file.toURI().toString(),size,size, true, true);
		iv = new ImageView();
		iv.setImage(image);
		pane.relocate(city.getX(), city.getY());
        pane.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            	MenuDialog.showCity(city);
            }
        });
        pane.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               highlight();
            }
        });
        
        pane.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               unhighlight();
            }
        });
        
//		pane.setStyle("-fx-border-color: black");
//		circle = new Circle(10, Color.BLUE);
//		circle.relocate(city.getX(), city.getY());
		text = new Text(city.getName());
		text.relocate(0, size*1.1);
		pane.getChildren().addAll(iv, text);
		pane.setCursor(Cursor.CROSSHAIR);
	}
	
	public void highlight() {
		if(highlightEffect ==  null) {
			highlightEffect = new Glow(1.0);
			ColorAdjust red = new ColorAdjust(0.0, 1.0, 0.0, 0.0);
			highlightEffect.setInput(red);
		}
		iv.setEffect(highlightEffect);
	}
	
	public void unhighlight() {
		iv.setEffect(null);
	}
	
	public Pane getPane() {
		return pane;
	}
	
}
