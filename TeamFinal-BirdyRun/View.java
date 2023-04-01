import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.LineBorder;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.AWTKeyStroke;
import javax.swing.ImageIcon;

public class View extends JFrame{
	public int padding =25;
	private BufferedImage ospreyBackground;
	private BufferedImage ospreyBackground2;
	private BufferedImage clapperBackground;
	private BufferedImage clapperBackground2;
	private BufferedImage startGameBackground;
	public int backx;
	public int backspeed;	//match to sprite scroll speed
	private boolean toggle = false;
	private BufferedImage ospreyMinimap;
	private BufferedImage clapperMinimap;
	public int mapblipx;
	public int mapblipy;
	private Nest nest;
	private boolean gameOver;
	int piecesize = 100;
	int playersize = 200;
	public boolean tutorial = true;
	int step = 4;
	BufferedImage nestImg;
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private Player bird;
	private QuizQ quiz;
	private BufferedImage ospreyImg;
	private BufferedImage clapperImg;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private ArrayList<BufferedImage> imgs = new ArrayList<>();
	private ArrayList<BufferedImage> imgO = new ArrayList<>();
	private ArrayList<BufferedImage> imgC = new ArrayList<>();

	
	JFrame frame =  new JFrame("Bird Game");
	JButton startOsprey;
	JButton startClapper;

	static DrawPanel drawPanel;
	MenuPanel menuPanel;
	JProgressBar energyBar;
	JProgressBar nestBar;
	boolean quiztoggle = false;
	
	//constructor gradually builds cache of images, buttons and panels, 
	public View() {
		ImageIcon ospreyIcon = new ImageIcon("Images/ospreyIcon.png");
		ImageIcon clapperrailIcon = new ImageIcon("Images/clapperrailIcon.png");
		
		//start buttons
		startOsprey = new JButton("Play as an Osprey", ospreyIcon);
		startClapper = new JButton("Play as a Clapper Rail", clapperrailIcon);
		//start screen
		//BufferedImage startGameBackground = createImage("Images/startscreen.png"); not implemented

		//osprey images created here
		ospreyBackground = createImage("Images/GameBackground.jpg");
		ospreyBackground2 = createImage("Images/GameBackgroundFl.jpg");//flipped
		ospreyImg = createImage("Images/osprey2d_img.png");
	   	ospreyImg = resize(ospreyImg, 200, 200);
	   	BufferedImage ospreyFoodImg = createImage("Images/food_bfish.png");
	   	BufferedImage ospreyFoodImg2 = createImage("Images/birdprey.png");
	   	ospreyFoodImg = resize(ospreyFoodImg, 100, 100);
	   	ospreyFoodImg2 = resize(ospreyFoodImg2, 60, 60);
		ospreyMinimap = createImage("Images/mini.jpg");
		
		//clapper rail images created here
		clapperBackground = createImage("Images/clapback.jpg");
		clapperBackground2 = createImage("Images/clapbackfl.jpg");
		clapperImg = createImage("Images/clapper_rail.png");
	   	clapperImg = resize(clapperImg, 200, 200);
	   	BufferedImage clapperFoodImg = createImage("Images/food_crabcrd.png");
	   	clapperFoodImg = resize(clapperFoodImg, 75, 75);
	   	BufferedImage clapperFoodImg2 = createImage("Images/seedfood.png");
	   	clapperFoodImg2 = resize(clapperFoodImg2, 75, 75);
	   	
	   	BufferedImage obstacle1= createImage("Images/branchesd-obs.png");
	   	obstacle1 = resize(obstacle1, 100, 100);
	   	BufferedImage obstacle2= createImage("Images/clap_trap.png");
	   	obstacle2 = resize(obstacle2, 100, 100);
		clapperMinimap = createImage("Images/clap_mini.png");
		BufferedImage obstacle3 = createImage("Images/plane_pic.png");
		obstacle3 = resize(obstacle3, 100, 100);
		BufferedImage obstacle4 = createImage("Images/litter.png");
		obstacle4 = resize(obstacle4, 100, 100);
		
		BufferedImage nestpieceImg = createImage("Images/crd_nestpiece.png");
	   	nestpieceImg = resize(nestpieceImg, 100, 100);
	   	BufferedImage nestpieceImg2 = createImage("Images/clapgrass.png");
	   	nestpieceImg2 = resize(nestpieceImg2, 100, 100);
	   	
	   	BufferedImage nestImg = createImage("Images/nest.png");
		nestImg = resize(nestImg, 400, 700);
	   	
		startClapper.setPreferredSize(new Dimension(screenSize.width /2-padding, screenSize.height-padding));
		startOsprey.setPreferredSize(new Dimension(screenSize.width /2 -padding, screenSize.height-padding));
	
		//adds the images to a bufferedImage arrays	
		imgO.add(ospreyFoodImg);//food 0-1
		imgO.add(ospreyFoodImg2);
		imgO.add(obstacle1);//obstacle 2-5
		imgO.add(obstacle2);
		imgO.add(obstacle3);
		imgO.add(obstacle4);
		imgO.add(nestpieceImg);//nest 6
		imgO.add(ospreyBackground);//background 7-8
		imgO.add(ospreyBackground2);
		imgO.add(ospreyMinimap);//minimap 9
		
		imgC.add(clapperFoodImg);
		imgC.add(clapperFoodImg2);
		imgC.add(obstacle1);
		imgC.add(obstacle2);
		imgC.add(obstacle3);
		imgC.add(obstacle4);
		imgC.add(nestpieceImg2);
		imgC.add(clapperBackground);
		imgC.add(clapperBackground2);
		imgC.add(clapperMinimap);
		imgs = imgO;
		
		//creates a player and nest sprite
		bird = new Player(screenSize.width / 8, screenSize.height / 2, ospreyImg);
		nest = new Nest(1.1*screenSize.width, screenSize.height/2, nestImg);
		
		menuPanel = new MenuPanel();
		menuPanel.setBackground(Color.blue);

		startClapper.setActionCommand("Clapper Rail");
		startOsprey.setActionCommand("Osprey");
		
		menuPanel.add(startOsprey);
		menuPanel.add(startClapper);

		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.add(menuPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	//Takes the panel, draws to it, updates certain elements based on view, including minimap blip
	@SuppressWarnings("serial")
	public class DrawPanel extends JPanel {
		protected void paintComponent(Graphics g) {
		
			super.paintComponent(g);
			if(toggle) {
				g.drawImage(imgs.get(7), backx, 0, screenSize.width, screenSize.height,  this);
				//second backgorund image so it can scroll
				g.drawImage(imgs.get(8), backx + screenSize.width, 0, screenSize.width, screenSize.height,  this);
			}
			else {
				g.drawImage(imgs.get(8), backx, 0, screenSize.width, screenSize.height,  this);

				g.drawImage(imgs.get(7), backx + screenSize.width, 0, screenSize.width, screenSize.height,  this);
			}
			
			if(backx <= 0 -screenSize.width) {
				backx = 0;
				toggle = !toggle;
			}
			
			g.drawImage(bird.Image, (int)bird.xloc, (int)bird.yloc, bird.Image.getWidth(), bird.Image.getHeight(),  this);
			g.drawImage(imgs.get(9), 0, screenSize.height - 375, 260, 314, this);
			g.setColor(Color.RED);
			g.fillOval(mapblipx, mapblipy, 15, 15);
			if(tutorial) //draws the tutorial
				tutorial(g);
			else {
				step = 4;//reset tutorial steps
				for(Sprite s: sprites) {
					g.drawImage(s.Image, (int)s.xloc, (int)s.yloc, s.getImgWidth(), s.getImgHeight(),  this);
				}
				
			}
			
		}
	}
	
	//helper function for tutorialization, simple graphics drawing with boolean exit
	public void tutorial(Graphics g) {
		//draws the tutorial to teach the player
			g.setFont(new Font("Courier", Font.BOLD,35));
			if(bird.migratory)
				g.drawString("This is You, a Mighty OSPREY, you're on a long journey home", 350, (int)screenSize.getHeight() *4/5);
			else
				g.drawString("This is You, a CLAPPER RAIL, living your life, minding your business", 350, (int)screenSize.getHeight() *4/5);
			
			try {
				Thread.sleep(1000);//increase/decrease "speed"
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		if( step<=4) {
				g.drawString("Eat Food", (int)screenSize.getWidth() /2 - 40, (int)screenSize.getHeight()/2 - 40);
				Food x = new Food( screenSize.getWidth() /2, screenSize.getHeight()/2, imgs.get(0));
				g.drawImage(x.Image, (int)x.xloc, (int)x.yloc, x.getImgWidth(), x.getImgHeight(),  this);
				
		} if(step <=3) {
				g.drawString("Collect Nest Pieces", (int)screenSize.getWidth() * 2/3 - 40, (int)screenSize.getHeight() /4 - 40);
				NestPiece y = new NestPiece(screenSize.getWidth() * 2/3, screenSize.getHeight() /4, imgs.get(6));
				g.drawImage(y.Image, (int)y.xloc, (int)y.yloc, y.getImgWidth(), y.getImgHeight(),  this);
				
		} if (step <= 2) {
				g.drawString("Avoid Dangers", (int)screenSize.getWidth() * 1/4, (int)screenSize.getHeight() *1/4);
				Obstacle z = new Obstacle(screenSize.getWidth() * 1/4, screenSize.getHeight() *1/4, imgs.get(4));
				g.drawImage(z.Image, (int)z.xloc, (int)z.yloc, z.getImgWidth(), z.getImgHeight(),  this);
				
		} if (step == 1) {g.drawString("Simply fly UP and DOWN with the arrow keys", (int)screenSize.getWidth() /2 -500, (int)screenSize.getHeight() *3/5);
					g.drawString("Press Space to begin", (int)screenSize.getWidth() /2 - 400, (int)screenSize.getHeight() *3/5 + 50);
					step++;
		} if (step == 0) {tutorial =false;}
		step--;

		repaint();
			
	}
	
	
	//the menu panel is built of its elements, painted here
	@SuppressWarnings("serial")
	public class MenuPanel extends JPanel {
		protected void paintComponent(Graphics g) {
			g.drawImage(startGameBackground,0,0,this);
			super.paintComponent(g);
			
		}
	}
   
	//update, the hub of all image movement functionality, access to model's gameover condition, the player, and all sprites
    public void update(ArrayList<Sprite> s, Player b, boolean g) {
		//gets the updated locations of the sprites and player, and gets the game status from model
    		gameOver = g;
		if(bird.migratory)
			imgs =imgO;
		else
			imgs = imgC;
		if (g) {
			
			removeGamePanel();//if the game is over remove the game panel and add the menupanel
		}
		else {
			sprites = s;
			bird = b;

			energyBar.setValue(bird.energyLevel);
			nestBar.setValue(bird.nestProgress);
			
			if(backx == -backspeed){
				if(bird.getMigratory()) {
					//moves the minimap icon
					mapblipy -=12;
					mapblipx += 5;
				}
				else {
					mapblipy -=10;
					mapblipx -= 7;
				}
			}
			backx -= backspeed;
			backspeed = bird.energyLevel / 4;
			drawPanel.repaint();
			
			//always ready to call quiz, on death
			if (bird.isDead() && !quiztoggle) {
				displayQuiz();
				//b.resetDeath();can have unintended effect of multiple quiz questions.
			}
		}
	}
	//quiz elements, option panes filled by QuizQs and bird/player consequences
   public void displayQuiz() {
	   //creates a JOptionPane that contains a quiz
	   quiztoggle = true;
	   QuizQ q = new QuizQ(bird.getMigratory());
	   Answers[] options = q.getOptions();
	   
	   //JOptionPane pane = new JOptionPane(q.getQuestion(), JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
		int option =  JOptionPane.showOptionDialog(null, q.getQuestion(), "Answer correctly to come back to life!",
		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
		null, options, options[0]);
	   

	    if (option != q.getCorrectAns(options).getNum()) { // answer submitted is not correct
	    	JOptionPane.showMessageDialog(null, "Not Correct!");
	    	q.setSubmitted(false);
	    	sprites.clear();
	    	removeGamePanel();
	    } else {
	    	JOptionPane.showMessageDialog(null, "Correct! Live Again!");
	    	q.setSubmitted(true);
	    	bird.revive();
	    	quiztoggle = false;
	    }
    }
    
   //image generator function
	private BufferedImage createImage(String img) {
    	BufferedImage bufferedImage;
    	try {
    		bufferedImage = ImageIO.read(new File(img));
    		return bufferedImage;
    	} 
		catch (IOException e) {
	    	e.printStackTrace();
		}
	   	return null;
	}
	
	//Main method, simply creates a controller object, runtime begins in that constructor.
	public static void main(String[] args) {
		Controller a = new Controller();
	}

	//image resizing function, for scaling
	private static BufferedImage resize(BufferedImage img, int height, int width) {
		//resizes the image so they are all around the same size
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	
	
	public Dimension getScreenSize() {
		return screenSize;
	}
	
	//function to exit main menu, menu is called by menupanel, removed here
	public void removeMenu() {
		backx = 0;

		if (bird.getMigratory()) {//sets minimap icon start location
			mapblipx = 130;
			mapblipy = screenSize.height - 60;
		}
		else {
			mapblipx = 170;
			mapblipy = screenSize.height - 100;
		}
		
		// Energy and Nest Progress bars:
		energyBar = new JProgressBar(0, 100);
		nestBar = new JProgressBar(0, 100);
		energyBar.setValue(bird.energyLevel);
		energyBar.setPreferredSize(new Dimension(screenSize.width /5, 50));
		nestBar.setValue(0);
		nestBar.setPreferredSize(new Dimension(screenSize.width /5, 50));
      	energyBar.setStringPainted(true); 
        nestBar.setStringPainted(true);
        energyBar.setString(String.format("Energy: ", bird.energyLevel));
        nestBar.setString(String.format("Nest Progress: ", bird.nestProgress));

		frame.remove(menuPanel);
		
		if (!bird.getMigratory()) {
			imgs = imgC;
			bird.Image = clapperImg;
		}
		else {
			imgs = imgO;
			bird.Image = ospreyImg;
		}
		drawPanel = new DrawPanel();
		drawPanel.add(energyBar);
		drawPanel.add(nestBar);
		frame.add(drawPanel);
		drawPanel.requestFocusInWindow();
		drawPanel.requestFocus();
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}

	//exit game, return to menu panel
	public void removeGamePanel() {
		frame.remove(drawPanel);
		tutorial = true;
		gameOver = false;
		bird = new Player(screenSize.width / 10, screenSize.height / 2, ospreyImg);
		nest = new Nest(1.1*screenSize.width, screenSize.height/2, nestImg);
		quiztoggle = false;
		sprites.clear();
		backspeed = bird.energyLevel/4;
		
		frame.add(menuPanel);
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}

	
	
	
	public ArrayList<BufferedImage> getImgs() {
		return imgs;
	}
	
	public void setGameOver(boolean g) {
		gameOver = g;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}

	public Nest getNest() {
		return nest;
	}
	public ArrayList<Sprite> getSprites() {
		return sprites;
	}

	public Player getPlayer() {
		return bird;
	}
}