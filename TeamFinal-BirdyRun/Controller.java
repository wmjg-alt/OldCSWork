//package birdyRun;

import java.awt.event.*;
import java.io.Serializable;
import java.awt.EventQueue;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

public class Controller implements KeyListener, ActionListener, Serializable { 
	private Model model;
	private View view;
	int lastkey;
	Action drawAction;
	static final int refreshRate = 15;
	
	//a View and Model, plus listeners. The model is passed screen data which affect image sizes when players and sprites are generated in teh view for model to calculate.
	public Controller(){
		view = new View();
		model = new Model(view.getScreenSize(), view.getPlayer(), view.getNest(), view.getImgs());
		view.startClapper.addActionListener(this);
		view.startOsprey.addActionListener(this);

	}
	
    //run the simulation
	public void start(){
		drawAction = new AbstractAction(){
			public void actionPerformed(ActionEvent e){

				model.updateLocation();
				if(!view.quiztoggle) {//do not update and recall quiz while quiz is in progress
					view.update(model.getSprites(), model.getPlayer(), model.getGameOver());
				}
				model.setGameOver(view.getGameOver());//gets game status frome view and sets it in model
			}
		};
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				Timer t = new Timer(refreshRate, drawAction);//timer to update game every 15 milliseconds - editable as refreshrate
				t.start();//starts the game
			}
		});
	}
	
	
	//keylistener events. only inputs are up down and boolean tutorial ender
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP|| key == KeyEvent.VK_W) {
			model.move("up");
		}
		else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			model.move("down");
		}
		else if( key == KeyEvent.VK_SPACE) {
			if(view.tutorial) {
				view.tutorial = false;
			}
		}
	}
	//became disused over iterations
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	//events for buttons in the menu being clicked
	public void actionPerformed(ActionEvent ae) {
		String action = ae.getActionCommand();
        if (action.equals("Clapper Rail")) {
			view.getPlayer().setMigratory(false);//sets the clapper rail game
        }
		else if (action.equals("Osprey")) {
			view.getPlayer().setMigratory(true);//sets the Osprey game
		}

        model.setLevelProgress(0);//resets the level progress everytime a game is started
		view.removeMenu();//removes menu panel and adds the game panel(osprey or clapper rail)
		view.drawPanel.addKeyListener(this);
        model.setImgs(view.getImgs());//gets the bufferedimgs from view and sets it in the model
		start();
	}
}