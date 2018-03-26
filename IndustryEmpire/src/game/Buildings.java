package game;

public class Buildings {
	
	public static BuildingSpec PANCAKE_FACTORY;
//	public static BuildingSpec WOOD_FACTORY;
	public static BuildingSpec BOOK_FACTORY;
	
	public static void init() {
//		PANCAKE_FACTORY = new BuildingSpec("Pancake Factory",10);
//		PANCAKE_FACTORY.addResource(Resources.MILK, 3, BuildingSpec.INPUT);
//		PANCAKE_FACTORY.addResource(Resources.EGG, 6, BuildingSpec.INPUT);
//		PANCAKE_FACTORY.addResource(Resources.PANCAKE, 10, BuildingSpec.OUTPUT);
//		PANCAKE_FACTORY.addResource(Resources.HEAT, 20, BuildingSpec.OUTPUT);
//		
//		WOOD_FACTORY = new BuildingSpec("Wood Factory",10);
//		WOOD_FACTORY.addResource(Resources.WOOD, 3, BuildingSpec.OUTPUT);
		
		BOOK_FACTORY = new BuildingSpec("Book Factory",10);
		BOOK_FACTORY.addResource(Resources.WOOD, 1, BuildingSpec.INPUT);
		BOOK_FACTORY.addResource(Resources.BOOKS, 3, BuildingSpec.OUTPUT);

	}
	
}
