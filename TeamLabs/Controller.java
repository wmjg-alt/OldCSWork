/**
 * Do not modify this file without permission from your TA.
 **/
 import java.awt.event.*;
 
public class Controller implements ActionListener, KeyListener{
//key listener goes in the controller
	private Model model;
	private View view;
	int lastkey = 0;


	
	public Controller(){
		view = new View();
		model = new Model(view.getPicSize(), view.getFrameStartSize());
	}
	
    //run the simulation
	public void start(){
		for(int i = 0; i < 5000; i++)
		{
			//increment the x and y coordinates, alter direction if necessary
			model.updateLocationAndDirection();
			//update the view
			view.update(model.getX(), model.getY(), model.getDirect());
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (lastkey != e.getKeyCode()) {
			if (key == KeyEvent.VK_F) {
				model.stopAction();
				view.setOrcAction(1);
			}
			else if (key == KeyEvent.VK_J) {
				view.setOrcAction(2);
			}
		}
		lastkey = e.getKeyCode();
	}
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_F) {
			view.setOrcAction(0);
			model.goAction();
		}
		else if (key == KeyEvent.VK_J) {
			view.setOrcAction(0);
		}
		lastkey = 0;
	}
	public void keyTyped(KeyEvent e) {}
	public void actionPerformed(ActionEvent e) {
		if(model.getBtnlock() == 0) {
			model.stopAction();
			view.setFrameLock(0);
		}
		else if(model.getBtnlock() == 1) {
			model.goAction();
			view.setFrameLock(1);
		}
	}
	public View getView() {
		return view;
	}
}
