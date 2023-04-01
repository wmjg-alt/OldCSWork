//package birdyRun;
import java.awt.image.BufferedImage;


public class Food extends Sprite{		//food class for food objects
	
	Food(double x, double y, BufferedImage img) {
		xloc = x;
		yloc = y;
		Image = img;
		type = "Food";
	}
}