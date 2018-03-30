package mainpack;

import game.City;
import game.ItineraryItem;
import game.Resource;
import game.Resources;
import gui.objects.ListCellItineraryItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
 
public class ListViewTest extends Application {
 
    TableView<ItineraryItem> tableView = new TableView<ItineraryItem>();

	private static ItineraryItem item;

	private static ObservableList<ItineraryItem> data;
 
    @Override
    public void start(Stage stage) {
        VBox box = new VBox();
        Scene scene = new Scene(box, 200, 200);
        stage.setScene(scene);
        stage.setTitle("ListViewSample");
        box.getChildren().addAll(tableView);
//        VBox.setVgrow(tableView, Priority.ALWAYS);
//        tableView.setRowFactory(new Callback<TableView<ItineraryItem>, 
//            TableRow<ItineraryItem>>() {
//
//				@Override
//				public TableRow<ItineraryItem> call(TableView<ItineraryItem> arg0) {
//					return new ListCellItineraryItem();
//				}
//            }
//        );
 
        stage.show();
    }
    
    
    public static void main(String[] args) {
    	City city = new City("Essen");
    	Resource[] res = {new Resource(Resources.GOLD,25), new Resource(Resources.WOOD,25)};
    	item = new ItineraryItem(city, res);    	
        data = FXCollections.observableArrayList(item);


        launch(args);
    }
}