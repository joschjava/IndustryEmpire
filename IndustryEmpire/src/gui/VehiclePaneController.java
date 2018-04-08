package gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import game.City;
import game.Itinerary;
import game.ItineraryItem;
import game.Resource;
import game.Vehicle;
import game.VehicleSpecs;
import gui.objects.ListCellItineraryItem;
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
import javafx.scene.control.MultipleSelectionModel;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;



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
	public Button btBuySubmit;
	
	@FXML
	public Button btCancel;
	
	@FXML
	public Button btFreight;
	
	private Itinerary itinerary = null;

	private Vehicle vehicle;
	
	private static final int NEW = 0;
	private static final int EDIT = 1;
	
	private int function = NEW;
	
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
		
		btBuySubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				switch(function) {
				case NEW:
					buy();
					break;
					
				case EDIT:
					submitChange();
					break;
					
				default:
					System.err.println("Unknown function in VehiclePane: "+function);
				}
				
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
	
    public void loadVehicle(Vehicle vehicle) {
    	this.vehicle = vehicle;
    	itinerary = vehicle.getItinerary();
    	btBuySubmit.setText("Submit");
    	function = EDIT;
    	vehicles.setDisable(true);
    	fillList();
    }
    
    private VehicleSpecs getSelectedVehicleSpecs() {
    	return vehicles.getSelectionModel().getSelectedItem();
    }
    
    private void fillList() {
	    itineraryView.setItems(itinerary.getObservableItinerary());
    }
    
    private void add() {
    	if(itinerary == null) {
    		itinerary = new Itinerary();
    	    itineraryView.setItems(itinerary.getObservableItinerary());
    	}
    	City city = cityDropDown.getSelectionModel().getSelectedItem();
    	ItineraryItem item = new ItineraryItem(city, null);
    	itinerary.add(item);
		itineraryView.getSelectionModel().selectLast();
		itineraryView.requestFocus();
    }

    private void remove() {
    	if(itinerary != null) {
    		MultipleSelectionModel<ItineraryItem> selectionModel = itineraryView.getSelectionModel();
			ItineraryItem item = selectionModel.getSelectedItem();
    		int prevIndex = selectionModel.getSelectedIndex();
    		itinerary.remove(item);
    		int newSize = itinerary.getObservableItinerary().size();
    		if(newSize > 0 && newSize != prevIndex) {
    			selectionModel.select(prevIndex);
    		}
    		itineraryView.requestFocus();
    	}
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
    
    private void submitChange() {
    	
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
}
