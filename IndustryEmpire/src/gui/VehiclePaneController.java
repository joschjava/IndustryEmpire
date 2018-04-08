package gui;

import java.util.ArrayList;

import game.City;
import game.Itinerary;
import game.ItineraryItem;
import game.Resource;
import game.Vehicle;
import game.VehicleSpecs;
import gui.objects.ListCellItineraryItem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import mainpack.Const;



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
	public Button btSetNextDest;
	
	@FXML
	public Button btFreight;
	
	@FXML
	public ProgressBar pbFuel;
	
	@FXML
	public Label lbFuel;
	
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

		pbFuel.setVisible(false);
		
		btCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				cancel();
				closeStageFromEvent(e);
			}
		});
		
		btSetNextDest.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				setSelectedAsNextDestination();
			}
		});
		
		
		
//		btSetNextDest
		
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
	
	private void setSelectedAsNextDestination() {
		int selectedIndex = itineraryView.getSelectionModel().getSelectedIndex();
		itinerary.setPos(selectedIndex);
	}
    
	/**
	 * Load the selected vehicle into view
	 * @param vehicle
	 */
    public void loadVehicle(Vehicle vehicle) {
    	this.vehicle = vehicle;
    	itinerary = vehicle.getItinerary();
    	btBuySubmit.setText("Close");
    	btCancel.setVisible(false);
    	
    	//Fuel consumption
    	pbFuel.setVisible(true);
    	pbFuel.progressProperty().bind(vehicle.fuelPercentProperty());
    	
    	DoubleProperty absFuel = vehicle.fuelProperty();
    	double maxFuel = vehicle.getVehicleSpecs().getTankSize();
    	StringProperty txtProperty = lbFuel.textProperty();

        NumberStringConverter converter = new NumberStringConverter() {
        	@Override
        	public String toString(Number value) {
        		if(value.doubleValue()<0) {
        			value = 0.0;
        		}
				return String.format("%."+Const.DISPLAY_DIGITS+"f",value.doubleValue())+
						" / "+String.valueOf(maxFuel);
        	}
        };
        Bindings.bindBidirectional(txtProperty, absFuel, converter);
    	
    	function = EDIT;
    	vehicles.setDisable(true);
    	fillList();
    }
    
    private VehicleSpecs getSelectedVehicleSpecs() {
    	return vehicles.getSelectionModel().getSelectedItem();
    }
    
    /**
     * Fill list with current itinerary
     */
    private void fillList() {
	    itineraryView.setItems(itinerary.getObservableItinerary());
    }
    
    
    /**
     * Adds a destination to itinerary
     */
    private void add() {
    	if(itinerary == null) {
    		itinerary = new Itinerary();
    		fillList();
    	}
    	City city = cityDropDown.getSelectionModel().getSelectedItem();
    	ItineraryItem item = new ItineraryItem(city, null);
    	itinerary.add(item);
		itineraryView.getSelectionModel().selectLast();
		itineraryView.requestFocus();
    }

    /**
     * Adds the currently selected destination from itinerary
     */
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
    	City cityStart = itinerary.getDestination();
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
