//package birdyRun;
import java.awt.image.BufferedImage;


public class NestPiece extends Sprite { // nest pieces can be collected

	NestPiece(double x, double y, BufferedImage img) {
		xloc = x;
		yloc = y;
		Image = img;
		type = "NestPiece";
	}	
}
