/**
 * View: Contains everything about graphics and images
 * Know size of world, which images to load etc
 *
 * has methods to
 * provide boundaries
 * use proper images for direction
 * load images for all direction (an image should only be loaded once!!! why?)
 **/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;
public class View extends JPanel{

	int frameWidth = 800;
    int frameHeight = 500;
    int imgWidth = 165;
    int imgHeight = 165;
	int picNum = 0;
	int frameCount =10;
	Map<Direction, BufferedImage> imgs;
	BufferedImage currImg;
	JFrame frame = new JFrame();
	int xloc =0;
	int yloc =0;
	
	public void update(int x, int y, Direction direct) {
		if(!imgs.containsKey(direct)){
			imgs.put(direct, createImage("orc_animation/orc_forward_" +direct+".png"));
		}
		
		currImg = imgs.get(direct);
		picNum = (picNum + 1) % frameCount;
		xloc = x;
		yloc= y;
		
		try {
			Thread.sleep(10);//increase/decrease "speed"
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    	
		frame.repaint();
	}
	public void paint(Graphics g){
		g.drawImage(currImg.getSubimage(imgWidth*picNum, 0, imgWidth, imgHeight), xloc, yloc, Color.gray, this);
	}//cuts the Image to the proper frame of animation every update. 
	//But does not repeated create images as they're stored in a hashmap
	
	
	View(){
		//initializing to some state (north), to avoid null pointer exceptions yo
		imgs = new HashMap<Direction, BufferedImage>();
		imgs.put(Direction.NORTH,createImage("orc_animation/orc_forward_" +Direction.NORTH+".png"));
		currImg = imgs.get(Direction.NORTH);
		
    	frame.getContentPane().add(this);
    	frame.setBackground(Color.gray);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameWidth, frameHeight);
    	frame.setVisible(true);

    }  
	
	private BufferedImage createImage(String toLoad){
    	BufferedImage bufferedImage;
    	try {
    		bufferedImage = ImageIO.read(new File(toLoad));
    		return bufferedImage;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
	
	
	
	
	public int getWidth() {
		return frameWidth;
	}
	public int getHeight() {
		return frameHeight;
	}
	public int getImageWidth() {
		return imgWidth;
	}
	public int getImageHeight() {
		return imgHeight;
	}
}