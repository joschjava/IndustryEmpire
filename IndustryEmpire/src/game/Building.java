package game;

import java.util.ArrayList;
import java.util.HashMap;

import mainpack.Functions;
import objects.HistoryObject;

public class Building implements TickListener{

	/** Building specifications: How many resources are needed for in- and output */
	private BuildingSpec buildingSpec;
	
	/** Defines how much is stored in input */
	private ArrayList<Resource> input;
	
	/** Defines how much is stored in output */
	private ArrayList<Resource> output;
	
	private City city = null;
	
	private static int buildingCnt = 0;
	public int buildingId = -1;
	
	public Building(BuildingSpec buildingSpec, City city){
		this.buildingSpec = buildingSpec;
		buildingId = buildingCnt++;
		initAmounts();
		Game.getInstance().addListener(this);
		if(city != null) {
			this.city = city;
		} else {
			System.err.println("Error creating building "+buildingId+"("+buildingSpec.getName()+"): City cannot be null");
		}
		city.addBuilding(this);
	}
	
	/** Initialises resources for in- and output */
	private void initAmounts() {
		input = buildingSpec.getInputRes();
		output = buildingSpec.getOutputRes();
	}
	
	
	/**
	 * Returns all ResourceIds of input and output
	 * initAmounts must be called before
	 * @return
	 */
	private ResourceSpec[] getInputOutputResourceIds(){
		if(input == null && output == null) {
			System.err.println("Resources have not been initialised. initAmounts() must be called before");
		}
		ResourceSpec[] allResources = new ResourceSpec[input.size()+output.size()];
		int counter = 0;
		for (Resource res : input) {
			allResources[counter] = res.getSpec();
			counter++;
		}
		for (Resource res : output) {
			allResources[counter] = res.getSpec();
			counter++;
		}
		return allResources;
	}
	
	@Override
	public void onTick() {
		boolean produce = true;
		for (Resource res:input) {
			double amount = city.getResourceAmount(res.getSpec());
			if(Functions.isZero(amount)) {
				produce = false;
				break;
			}
		}
		
		if (produce) {
			for (int i = 0; i < output.size(); i++) {
				Resource res = output.get(i);
				ResourceSpec spec = res.getSpec();
				double amntPerTick = res.getAmount()/((double) this.buildingSpec.getSpeed());
				city.chgResource(spec, amntPerTick); 
			}
			
			for (int i = 0; i < input.size(); i++) {
				Resource res = input.get(i);
				ResourceSpec spec = res.getSpec();
				double amntPerTick = res.getAmount()/((double) this.buildingSpec.getSpeed());
				city.chgResource(spec, -amntPerTick); 
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder outputStr = new StringBuilder();
		outputStr.append(buildingSpec.getName()+" ("+buildingId+")\n");
		outputStr.append("\t Input: \n");
		for (int i = 0; i < input.size(); i++) {
			outputStr.append("\t\t"+input.get(i)+"\n");
		}
		outputStr.append("\t Output: \n");
		for (int i = 0; i < output.size(); i++) {
			outputStr.append("\t\t"+output.get(i)+"\n");
		}
		return outputStr.toString();
	}
	
}
