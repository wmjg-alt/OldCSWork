//package birdyRun;
import java.awt.image.BufferedImage;

public class Obstacle extends Sprite {
	// obstacles objects for the game
	Obstacle(double x, double y, BufferedImage img) {
		xloc = x;
		yloc = y;
		Image = img;
		type = "Obstacle";
	}
	
}
