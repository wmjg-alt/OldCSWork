import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Sprite implements Serializable {
	//all screen objects extend this class so they can go into the same collection morph
	public double xloc;
	public double yloc;
	public BufferedImage Image;
	public String type;
	
	public int getImgWidth() {
		return Image.getWidth();
	}
	public int getImgHeight() {
		return Image.getHeight();
	}
}
