package mainpack;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;

import game.Position;

public class Functions {

	private static List<String> firstNames = null;
	private static List<String> lastNames = null;
	private static final String FIRSTNAMES_FILE = "files/firstNames.csv";
	private static final String LASTNAMES_FILE = "files/lastNames.csv";
	
	/**
	 * Loads first and last name from csv files
	 */
	private static void loadNameList() {
		try {
			firstNames = FileUtils.readLines(new File(FIRSTNAMES_FILE), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("First name file not found: "+FIRSTNAMES_FILE);
			e.printStackTrace();
		}
		try {
			lastNames = FileUtils.readLines(new File(LASTNAMES_FILE), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Second name file not found: "+LASTNAMES_FILE);
			e.printStackTrace(); 	
		}
	}
	
	/**
	 * Returns a generated name
	 */
	public static String generateName() {
		if(firstNames == null || lastNames == null) {
			loadNameList();
		}
		int firstNamePos = RandomUtils.nextInt(0, firstNames.size());
		int lastNamePos = RandomUtils.nextInt(0, lastNames.size());
		String firstName = firstNames.get(firstNamePos);
		String lastName = lastNames.get(lastNamePos);
		return firstName+" "+lastName;
	}
	
	public static double getDistance(Position pos1, Position pos2) {
		return Math.sqrt(Math.pow(pos2.getY() - pos1.getY(), 2) + Math.pow(pos2.getX() - pos1.getX(), 2));
	}
	

	/**
	 * Checks if a given resource amount is 0 with a tolerance (Balance.TOLERANCE)
	 * @param res Resource to be checked
	 * @return true if zero, false if not
	 */
	public static boolean isZero(double amount) {
		double tolerance = Balance.TOLERANCE;
		if(-tolerance <= amount && amount <= tolerance) {
			return true;
		} else {
			return false;
		}
	}
	
}
