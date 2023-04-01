
 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.*;
import javax.swing.JButton;
import java.awt.event.*;

public class View extends JFrame {

	final int frameCount = 10;
	HashMap<Direction, BufferedImage> imgs; 
	HashMap<Direction, BufferedImage> fireImgs;
	HashMap<Direction, BufferedImage> jumpImgs;
	int xloc = 100;
    int yloc = 100;
    final int xIncr = 1;
    final int yIncr = 1;
    int picSize = 165;
    final int frameStartSize = 800;
    final int drawDelay = 30; //msec
	int picNum = 0;
	int frameLock = 1;
	int orcAction = 0;
	BufferedImage currImg;

    
    DrawPanel drawPanel = new DrawPanel();
    Action drawAction;
	static JButton StopB = new JButton("Stop");
	


    public View() {
    	drawAction = new AbstractAction(){
    		public void actionPerformed(ActionEvent e){
					drawPanel.repaint();
    		}
    	};
    	
    	add(drawPanel);
		drawPanel.add(StopB);
		imgs = new HashMap<Direction, BufferedImage>();
		fireImgs = new HashMap<Direction, BufferedImage>();
		jumpImgs = new HashMap<Direction, BufferedImage>();
		imgs.put(Direction.NORTH,createImage("orc_animation/orc_forward_" +Direction.NORTH+".png"));
		currImg = imgs.get(Direction.NORTH);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(frameStartSize, frameStartSize);
    	setVisible(true);
    	pack();
    }
	
	public void update(int x, int y, Direction direct) {
		if (!imgs.containsKey(direct)) {
			imgs.put(direct, createImage("orc_animation/orc_forward_" +direct+".png"));
		}
		if (!jumpImgs.containsKey(direct)) {
			jumpImgs.put(direct, createImage("orc_animation/orc_jump_" +direct+".png"));
		}
		if (!fireImgs.containsKey(direct)) {
			fireImgs.put(direct, createImage("orc_animation/orc_fire_" +direct+".png"));
		}
		if (orcAction == 0) {
			currImg = imgs.get(direct);
		}
		else if (orcAction == 1) {
			currImg = fireImgs.get(direct);
		}
		else if (orcAction == 2) {
			currImg = jumpImgs.get(direct);
		}
		picNum = (picNum + frameLock) % frameCount;
		xloc = x;
		yloc= y;
		
		try {
			Thread.sleep(10);//increase/decrease "speed"
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    	
		drawPanel.repaint();
	}
	
    @SuppressWarnings("serial")
	private class DrawPanel extends JPanel {
    	int picNum = 0;

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.gray);
	    	picNum = (picNum + frameLock) % frameCount;
	    	g.drawImage(currImg.getSubimage(picSize*picNum, 0, picSize, picSize), xloc, yloc, Color.gray, this);
		}

		public Dimension getPreferredSize() {
			return new Dimension(frameStartSize, frameStartSize);
		}
	}
    
    //Read image from file and return
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
	
	public static void main(String[] args) {
		Controller a = new Controller();
		addStopListener(a);
		StopB.addKeyListener(a);
		a.start();
		/*EventQueue.invokeLater(new Runnable(){
			public void run(){
				addStopListener(a);
			}
		});*/
	}
	
	public int getPicSize() {
		return picSize;
	}
	public int getFrameStartSize() {
		return frameStartSize;
	}
	static void addStopListener(Controller c) {
		StopB.addActionListener(c);
	}
	public void setFrameLock(int a) {
		frameLock = a;
	}
	public void setPicSize(int a) {
		picSize = a;
	}
	public void setOrcAction(int a) {
		orcAction = a;
	}
}