import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.*;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
//package birdyRun;


public class Model implements Serializable{
	private int levelProgress = 0;
	private ArrayList<Sprite> sprites = new ArrayList<>();
	private Player bird;
	private boolean birdDead = false;
	private Dimension screenSize;
	private ArrayList<BufferedImage> imgs = new ArrayList<>();
	private double yIncr = 0.0;
	private Nest nest;
	private boolean gameOver;

	private double lane1 = 0.0;
	private double lane2;
	private double lane3;
	private double lane4;
	private double target;
	private boolean moving = false;
	
	private int lock;
	static final int bottomOfScreen = 0;
	static final double stopMoving = 0.0;
	static final double movementMultiplier = 0.25;
	static final double offscreenMultiplier = 1.1;
	static final int nestMovementSpeed = 7;
	static final int waves = 4;
	static final int foodNum = 8;
	static final int obsNum = 7;
	static final int nestNum = 2;

	//size morphs lanes otherwise they'd be constant. the rest is passed nest, player, and sprites
	public Model(Dimension bounds, Player b, Nest n, ArrayList<BufferedImage> a) {
		nest = n;
		screenSize = bounds;
		bird = b;
		imgs = a;
		
		//screen split into four lanes
		lane2 = (double)(screenSize.height / 4);
		lane3 = (double)(screenSize.height / 2);
		lane4 = (double)(screenSize.height*3 / 4);
	}

	//collision detection based on images and rectangles, resulting effects called, like energy level changes
	public void detectCollision() {
		Iterator<Sprite> itr = sprites.iterator();
		while (itr.hasNext()) {//iterates through the collection of sprites on screen (obstacles, food, nestpieces)
			Sprite s = itr.next();
			Rectangle p;
			Rectangle o;
			p = new Rectangle((int)bird.xloc,(int)bird.yloc,bird.getImgWidth(),bird.getImgHeight());

			if (s.type.equals("Nest")) {
				o = new Rectangle((int)nest.xloc, bottomOfScreen, nest.getImgWidth(), screenSize.height);//nest hitbox height is the entire screen so the player always hits it
				if(o.intersects(p)) {
					gameOver = true;//wants the player reaches the nest the game is over
					levelProgress= 0;
					lock =0;
					bird.energyLevel = 40;
				}
			}
			else {
				o = new Rectangle((int)s.xloc,(int)s.yloc,s.getImgWidth(),s.getImgHeight());
				if (o.intersects(p)) {
					//image collision is very taxing so we make a rectangle arpund each object and check if the player rectangle collides with the sprite rectangle
					//if they do collide it checks if the actual images collides
						if (imageCollision(s.xloc, s.yloc, s.Image, bird.xloc, bird.yloc, bird.Image)) {
							//checks what type of sprite collided with the player and does there respective game mechanic and then removes the sprite from the collection
							if (s.type.equals("Food")) {
								bird.regen();
								itr.remove();
							}
							else if (s.type.equals("NestPiece")) {
								bird.buildNest();
								itr.remove();
							}
							else if (s.type.equals("Obstacle")) {
								bird.damage();
								itr.remove();
							}
						}
					
						
					}
				}
			
		}
	}
	
	//Collision detection of images taking 2 objects' x's and y's and images. comparing for collision detection, updating "destruction"
	public static boolean imageCollision(double x1, double y1, BufferedImage image1, double x2, double y2, BufferedImage image2) {
		//iterates through the pixels of each image and if the pixel is not transparent then it has collided
		// initialization
		double width1 = x1 + image1.getWidth() -1;
        double height1 = y1 + image1.getHeight() -1;
        double width2 = x2 + image2.getWidth() -1;
        double height2 = y2 + image2.getHeight() -1;

		int xstart = (int) Math.max(x1, x2);
		int ystart = (int) Math.max(y1, y2);
		int xend   = (int) Math.min(width1, width2);
		int yend   = (int) Math.min(height1, height2);

		// intersection rect
		int toty = Math.abs(yend - ystart);
		int totx = Math.abs(xend - xstart);

		for (int y=1;y < toty-1;y++){
			int ny = Math.abs(ystart - (int) y1) + y;
			int ny1 = Math.abs(ystart - (int) y2) + y;
			for (int x=1;x < totx-1;x++) {
				int nx = Math.abs(xstart - (int) x1) + x;
				int nx1 = Math.abs(xstart - (int) x2) + x;
				try {
					if (((image1.getRGB(nx,ny) & 0xFF000000) != 0x00) && ((image2.getRGB(nx1,ny1) & 0xFF000000) != 0x00)) {
					// collide!!
					return true;
					}
				} 
				catch (Exception e) {
					System.out.println("s1 = "+nx+","+ny+"  -  s2 = "+nx1+","+ny1);
				}
			}
		}
		return false;
	}
	
	//player movement, as called by controller keylisteners
	public void move(String x) {
		if(!moving){//if the player is not currently moving
			if(x.equals("up")) {
				bird.fatigue();
				if(bird.yloc <= lane1)//makes sure the player cant go above the screen
					yIncr = stopMoving;
				else{
					yIncr = 0- (lane2 / 16); //moves the player up a lane
					moving = true;
					target = bird.yloc - lane2; //minus 1/4 the screen
				}	
			}
			else if (x.equals("down")) {
				bird.fatigue();
				if(bird.yloc >= lane4)//makes sure player cant go below the screen
					yIncr = stopMoving;
				else{
					yIncr = lane2 / 16;//moves the player down a lane
					moving = true;
					target = bird.yloc +lane2; // plus 1/4 the screen
				}
			}		
			else if (x.equals("stop")) {
				yIncr = stopMoving;
				moving = false;
			}
		}
	}
	//when there's a dirth of sprites on screen, spawn sprites, based on a balance that makes the map maneuverable.
	public void spawnObjects() {
		//if the player is not dead spawn controlled number of elements for each type
		if(!bird.isDead()) {
			for(int i = 0; i < foodNum; i++) {
				double temp = randY();
				if (temp <= lane3) 
					sprites.add(new Food(randX(), temp, imgs.get(1))); 
				else 
					sprites.add(new Food(randX(), temp, imgs.get(0))); 
				
			}
			for(int i = 0; i < obsNum; i++) {
				double temp = randY();
				System.out.println(temp);
				if (temp <= lane1)
					sprites.add(new Obstacle(randX(), temp, imgs.get(4))); 
				else if (temp <= lane2)
					sprites.add(new Obstacle(randX(), temp, imgs.get(2))); 
				else if (temp <= lane3)
					sprites.add(new Obstacle(randX(), temp, imgs.get(3)));
				else
					sprites.add(new Obstacle(randX(), temp, imgs.get(5))); 
			}
			for(int i = 0; i < nestNum; i++) {
				sprites.add(new NestPiece(randX(), randY(), imgs.get(6))); 
			}
		}
	}
	
	//randomly selects a lane //double//
	public double randY() { 
		//randomly choses one of the lanes
		List<Double> yValue = new ArrayList<>(); 
		yValue.add(lane1);
		yValue.add(lane2);
		yValue.add(lane3);
		yValue.add(lane4);
        Random rand = new Random(); 
        return yValue.get(rand.nextInt(yValue.size())); 
    } 
	
	//random generates just offscreen for incoming wave of obs and food
	public double randX() {
		//randomly chooses and xcoordinate in the range of screenwidth to screenwidth * 2
        return (Math.random() *((screenSize.width * 2) + 1)) + (offscreenMultiplier * screenSize.width); 
	}

	//the major updates occur here, collisions called, movement increases, object spawns, and death checks
	public void updateLocation() {
		if (!gameOver) {//checks if player reached the nest
			if(moving){//if the player is mvoeing up or down 
				bird.yloc += yIncr;//sets the rate the player moves
				if(bird.yloc == target){//if the player reaches the target lane stop moving
					yIncr = stopMoving;
					moving = false;			
				}
			} 
			if (levelProgress <= waves) {//there is 5 waves
				lock = 0;
				if(!sprites.isEmpty()) {//if there are sprites still on screen
					Iterator<Sprite> itr = sprites.iterator();
					while(itr.hasNext()) {//iterates through the spawned sprites and moves them a distance based on there energy level
						Sprite s = itr.next();
						s.xloc = s.xloc - (movementMultiplier * bird.energyLevel); 
						if (s.xloc < -100) {
							itr.remove();//removes the sprite if it is 100 pixels behind the screen, so it removes it off screen
						}
					}
					detectCollision();
				}
				else {
					spawnObjects();//spawns sprites if all the sprites have been removes
					levelProgress++;
					System.out.println("Level progress: " + levelProgress);
				}
			}
			else {
				if(lock == 0) {
					sprites.clear();//clears the sprite collection after wave 5 is spawned
				}
				if (!sprites.isEmpty()) {
					nest.xloc = nest.xloc - nestMovementSpeed;//moves nest sprite across screen
					detectCollision();
				}
				else {
					nest.xloc = offscreenMultiplier * screenSize.width;
					sprites.add(nest);
					lock = 1;
				}
			}
			if (bird.isDead()) {//if bird is not dead
				QuizQ quiz = new QuizQ(bird.getMigratory());//creates quiz based on the type of bird
				if (quiz.getSubmitted()) {
					birdDead = false;
					bird.resetDeath();
					
				} else {
					birdDead = true;
					bird.setDeath(true);
					levelProgress = 0;
					//lock = 0;
					//bird.energyLevel = 40;
				}
			}
		}
	}
	
	
	
	//rest are getters and setters
	public Nest getNest() {
		return nest;
	}
	public void setImgs(ArrayList<BufferedImage> i){
		this.imgs = i;
	}
	
	public ArrayList<Sprite> getSprites() {
		return sprites;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	public void setGameOver(boolean g) {
		gameOver = g;
	}
	
	public void setLevelProgress(int n) {
		levelProgress = n;
	}
	
	public Player getPlayer() {
		return bird;
	}
}