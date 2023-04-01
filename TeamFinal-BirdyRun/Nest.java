import java.util.*;

import javax.swing.JTextArea;

import java.awt.image.BufferedImage;

public class Nest extends Sprite{
	//The nest at the end of the game 
	Nest(double x, double y, BufferedImage img) {
		xloc = x;
		yloc = y;
		Image = img;
		type = "Nest";
	}
}