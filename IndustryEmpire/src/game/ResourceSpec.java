package game;

public class ResourceSpec {

	private String name;

	private static int idCtr = 0;
	private int id;
	
	public ResourceSpec(String name) {
		this.name = name;
		this.id = idCtr++;
	}
	
	public int getResId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
