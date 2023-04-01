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
	int picSize;
	int frameStartSize;
	int xloc;
	int yloc;
	int xIncr = 8;
    int yIncr = 2;
	Direction direct;
	boolean north = false;
    boolean east = true;
	int btnlock = 0;
	int storedXIncr;
	int storedYIncr;
	
	Model(int pic, int frame) {
		picSize = pic;
		frameStartSize = frame;
	}
	
	public int detectCollision() {
		//detects if there is a collision
		//these booleans have padding to make the frame edges neater
    	boolean xb = (xloc+ xIncr +(4*picSize/5) <= frameStartSize);//x east limit
    	xb = xb && (xloc+xIncr +(picSize/4) >= 0);//x west limit
    	boolean yb = (yloc+ yIncr +picSize <= frameStartSize);//y south limit 
    	yb = yb && (yloc+yIncr+(picSize/5) >= 0);//y north limit
    	
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
	public void stopAction() {
		if (!(xIncr == 0 && yIncr ==  0)) {
			storedXIncr = xIncr;
			storedYIncr = yIncr;
		}
		xIncr = 0;
		yIncr = 0;
		btnlock = 1;
	}
	public void goAction() {
		xIncr = storedXIncr;
		yIncr = storedYIncr;
		btnlock = 0;
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
	public int getBtnlock() {
		return btnlock;
	}
}