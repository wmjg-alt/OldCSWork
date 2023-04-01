//package birdyRun;


public enum Lane {  // lane enum to dictate position on screen
	Top("top"),
	Mid("mid"),
	Bottom("bottom");
	
	private String name;
	
	private Lane(String s){
		name = s;
	}
	
	public String getName() {
		return name;
	}

}
