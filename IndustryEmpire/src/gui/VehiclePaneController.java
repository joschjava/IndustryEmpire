package gui;

import java.util.ArrayList;

import game.City;
import game.Itinerary;
import game.ItineraryItem;
import game.Resource;
import game.Vehicle;
import game.VehicleSpecs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import gui.objects.ListCellItineraryItem;



public class VehiclePaneController {

	@FXML	
	public ListView<VehicleSpecs> vehicles;
	
	@FXML
	public ListView<ItineraryItem>  itineraryView;
	
	@FXML
	public ComboBox<City> cityDropDown;
	
	@FXML
	public Button btAdd;
	
	@FXML
	public Button btDelete;
	
	@FXML
	public Button btBuy;
	
	@FXML
	public Button btCancel;
	
	@FXML
	public Button btFreight;
	
	private Itinerary itinerary = null;

	private Vehicle vehicle;
	
    @FXML
    public void initialize() {
    	ArrayList<VehicleSpecs> allSpecs = VehicleSpecs.getAllSpecs();
    	ObservableList<VehicleSpecs> items = FXCollections.observableArrayList(allSpecs);
		vehicles.setItems(items);
		vehicles.getSelectionModel().select(0);
		ArrayList<City> cityList = City.getAllCities();
		ObservableList<City> cities = FXCollections.observableArrayList(cityList);
		cityDropDown.setItems(cities);
		cityDropDown.getSelectionModel().select(0);
		
		btAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				add();
			}
		});
		
		btDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				remove();
			}
		});
		
		btBuy.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				buy();
				closeStageFromEvent(e);
			}
		});

		btCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				cancel();
				closeStageFromEvent(e);
			}
		});
		
        itineraryView.setCellFactory(new Callback<ListView<ItineraryItem>, 
                ListCell<ItineraryItem>>() {

    				@Override
    				public ListCell<ItineraryItem> call(ListView<ItineraryItem> item) {
    					return new ListCellItineraryItem();
    				}
                }
            );
		
		btFreight.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	ItineraryItem itinItem = itineraryView.getSelectionModel().getSelectedItem();
		        Resource[] res = MenuDialog.showResourceLoadDialog(getSelectedVehicleSpecs(),itinItem);
		        getSelectedItineraryItem().setLoad(res);
		    }
		});

    }
	
    private VehicleSpecs getSelectedVehicleSpecs() {
    	return vehicles.getSelectionModel().getSelectedItem();
    }
    
    private void add() {
    	if(itinerary == null) {
    		itinerary = new Itinerary();
    	}
    	City city = cityDropDown.getSelectionModel().getSelectedItem();
    	ItineraryItem item = new ItineraryItem(city, null);
    	itinerary.add(item);
    	//TODO: Make this better!!!
    	shittyUpdateList();
    }

    private void remove() {
    	if(itinerary != null) {
    		ItineraryItem item = itineraryView.getSelectionModel().getSelectedItem();
    		itinerary.remove(item);
    	}
    	shittyUpdateList();
    }
    
    private ItineraryItem getSelectedItineraryItem() {
    	return itineraryView.getSelectionModel().getSelectedItem();
    }
    
    private void buy() {
    	VehicleSpecs specs = vehicles.getSelectionModel().getSelectedItem();
    	City cityStart = itinerary.getItinerary().get(0).getDestination();
    	vehicle = new Vehicle(specs, cityStart);
    	vehicle.setItinerary(itinerary);
    }
    
    private void cancel() {
    	itinerary = null;
    }
    
    public Vehicle getVehicle() {
    	return vehicle;
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
    
	//TODO: Make this better!!!
    /**
     * @deprecated Bad Coding!!
     */
    private void shittyUpdateList() {
		ArrayList<ItineraryItem> itineraryList = itinerary.getItinerary();
		ObservableList<ItineraryItem> itineraryObserv = FXCollections.observableArrayList(itineraryList);
		itineraryView.setItems(itineraryObserv);
		if(itineraryList.size() == 1) {
			itineraryView.getSelectionModel().select(0);
		}
    }
}
