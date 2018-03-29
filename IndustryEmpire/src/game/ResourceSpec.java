package game;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;
import mainpack.Const;

/**
 * Define a specification for a resource, that contains name and image. Is needed to later create the real resources
 * @author Jonas Scholten
 *
 */
public class ResourceSpec {

	private String name;

	private static int idCtr = 0;
	private int id;
	private Image image;	
	private String imageFileName;
	private static File unknownFile;
	
	//TODO: Performance: Change this to Array
	private static ArrayList<ResourceSpec> allResources = new ArrayList<ResourceSpec>();
	
	
	public ResourceSpec(String name) {
		this(name, null);
	}
	
	public ResourceSpec(String name, String imageFileName) {
		this.name = name;
		this.id = idCtr++;
		this.imageFileName = imageFileName;
		allResources.add(this);
	}

	private void loadImage() {
		File imageFile = new File(
						Const.GRAPHICS_FOLDER + "/" +
					 	Const.RES_FOLDER + "/" + 
					 	imageFileName
					);
		if(!imageFile.isFile()) {
			System.err.println("Image not found or not defined for Resource: "+name);
			if(unknownFile == null) {
				imageFile = new File(
						Const.GRAPHICS_FOLDER + "/" +
					 	Const.RES_FOLDER + "/" + 
					 	Const.IM_UNKNOWN
					);
				unknownFile = imageFile;
			} else {
				imageFile = unknownFile;
			}
		}
		image = new Image(imageFile.toURI().toString());
	}
	
	public static ArrayList<ResourceSpec> getAllResourceSpecs() {
		return allResources;
	}
	
	public Image getImage() {
		if(image == null) {
			loadImage();
		}
		return image;
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
