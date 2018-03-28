package objects;

import java.util.ArrayList;

import game.Resource;

public class InputOutputResources {

	ArrayList<Resource> input = new ArrayList<Resource>();
	ArrayList<Resource> output = new ArrayList<Resource>();
	
	public void addInput(Resource res) {
		input.add(res);
	}
	
	
	public ArrayList<Resource> getInput() {
		return input;
	}
	public ArrayList<Resource> getOutput() {
		return output;
	}
	
	
	
	
}
