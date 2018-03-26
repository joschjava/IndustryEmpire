package mainpack;


/**
 * Contains constants for balancing the game
 * @author Jonas Scholten
 *
 */
public class Balance {

	/*
	 * GENERAL
	 */
	
	public static final int MAPSIZE_X = 500;
	public static final int MAPSIZE_Y = 500;
	
	/** Tolerance within a given resource is valid. E.g. 0.00005 of a resource equals empty resource = 0.0 */
	public static final double TOLERANCE = 0.001;
	
	/* 
	 * FILES
	 */
	
	public static final String GRAPHICS_FOLDER = "graphics";
	public static final String RES_FOLDER = "res";
	
	/** Image file for resources whose images couldn't be found or weren't properly defined */
	public static final String IM_UNKNOWN = "unknown.png";
	
	
	/*
	 * DISPLAY
	 */
	/** Number of digits after comma being displayed in view */
	public static final int DISPLAY_DIGITS = 2;
	
	/*
	 * DRIVER LIST 
	 */
	
	/** Minimal drivers on the list	 */
	public static final int MIN_DRIVERS = 2;
	
	/** Maximal drivers on the list, be aware that this might also be limited by {@link #MIN_DRIVER_TIME} and {@link #MAX_DRIVER_TIME}	 */
	public static final int MAX_DRIVERS = 10;
	
	/** Minimum time a driver is on the market */
	public static final int MIN_DRIVER_TIME = 5;
	
	/** Maximum time a driver is on the market */
	public static final int MAX_DRIVER_TIME = 40;

	/** Minimum basic salary of a driver (might be higher or lower due to driver qualifications) */
	public static final int MIN_DRIVER_SALARY = 10000;

	/** Maximum basic salary of a driver (might be higher or lower due to driver qualifications) */
	public static final int MAX_DRIVER_SALARY = 15000;
	
	
}
