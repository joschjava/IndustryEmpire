package game;

import java.io.File;

import javafx.scene.image.Image;
import mainpack.Const;

public class ResourceSpec {

	private String name;

	private static int idCtr = 0;
	private int id;
	private Image image;	
	private static File unknownFile;
	
	public ResourceSpec(String name) {
		this(name, null);
	}
	
	public ResourceSpec(String name, String imageFileName) {
		this.name = name;
		this.id = idCtr++;
		File imageFile = new File(
						Const.GRAPHICS_FOLDER + "/" +
					 	Const.RES_FOLDER + "/" + 
					 	imageFileName
					);
		if(!imageFile.isFile()) {
			System.out.println("Image not found or not defined for Resource: "+name);
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
	
	public Image getImage() {
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
