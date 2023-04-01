/**
 * Model: Contains all the state and logic
 * Does not contain anything about images or graphics, must ask view for that
 *
 * has methods to
 *  detect collision with boundaries
 * decide next direction
 * provide direction
 * provide location
 **/
 
public class Model {
	static int frameWidth;
    static int frameHeight;
    int imgWidth;
    int imgHeight;
	int xloc;
	int yloc;
	int xIncr = 8;
    int yIncr = 2;
	Direction direct;
	boolean north = false;
    boolean east = true;
	
	Model(int width, int height, int imageHeight, int imageWidth) {
		frameWidth = width;
		frameHeight = height;
		imgWidth = imageWidth;
		imgHeight = imageHeight;
	}
	
	public int detectCollision() {
		//detects if there is a collision
		//these booleans have padding to make the frame edges neater
    	boolean xb = (xloc+ xIncr +(4*imgWidth/5) <= frameWidth);//x east limit
    	xb = xb && (xloc+xIncr +(imgWidth/4) >= 0);//x west limit
    	boolean yb = (yloc+ yIncr +imgHeight <= frameHeight);//y south limit 
    	yb = yb && (yloc+yIncr+(imgWidth/5) >= 0);//y north limit
    	
    	if (!xb) {
    		return 1;
    	}
		else if (!yb) {
			return 2;
		}
		else {
			return 0;
		}
	}
	
	public void updateLocationAndDirection() {
		xloc += xIncr;
		yloc += yIncr;
		
		if (detectCollision() == 1) {
			xIncr *= -1;
			east = !east;
		}
		else if (detectCollision() == 2) {
			yIncr *= -1;
			north = !north;
		}
		
		if(!north && east)
    		direct =  Direction.SOUTHEAST;
    	else if(north && east)
    		direct = Direction.NORTHEAST;
    	else if(!north && !east)
    		direct = Direction.SOUTHWEST;
    	else if(north && !east)
    		direct = Direction.NORTHWEST;
    	else
    		direct = Direction.NORTH;
	}
	
	public int getX() {
		return xloc;
	}
	public int getY() {
		return yloc;
	}
	
	public Direction getDirect() {
		return direct;
	}
}