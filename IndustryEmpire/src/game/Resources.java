package game;

public class Resources {
	//DEBUGGING RESOURCES
	public static final ResourceSpec MILK = new ResourceSpec("Milk");
	public static final ResourceSpec EGG = new ResourceSpec("Egg");	
	public static final ResourceSpec PANCAKE = new ResourceSpec("Pancake");
	public static final ResourceSpec HEAT = new ResourceSpec("Heat");
	public static final ResourceSpec WOOD_DEB = new ResourceSpec("Wood");
	public static final ResourceSpec BOOKS =  new ResourceSpec("Books");
	
	public static final ResourceSpec TEST1 =  new ResourceSpec("Test1");
	public static final ResourceSpec TEST2 =  new ResourceSpec("Test2");
	public static final ResourceSpec TEST3 =  new ResourceSpec("Test3");
	
	//ACTUAL RESOURCES
	public static final ResourceSpec GOLD =  new ResourceSpec("Gold", "gold.png");
	public static final ResourceSpec STONE =  new ResourceSpec("Stone", "stone.png");
	public static final ResourceSpec WHEAT =  new ResourceSpec("Wheat", "wheat.png");
	public static final ResourceSpec WOOD =  new ResourceSpec("Wood", "Wood.png");
	
	/** Used for Resources that have no picture or of the file That couldn't be found */
	public static final ResourceSpec UNKNOWN =  new ResourceSpec("???", "unknown.png");
}
