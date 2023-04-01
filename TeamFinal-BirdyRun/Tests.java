import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
//import junit.framework.Test;

public class Tests {
	@Test
	public void testDetectCollisions() {
		Model model = new Model();
		assertEquals(0, model.detectCollision());
		assertEquals(1, model.detectCollision());
	}

	@Test
	public void testRegen() {
		Model model = new Model();
		model.setEnergyLevel(50);
		model.regen();
		assertEquals(52, model.getEnergyLevel());
		assertEquals(45, model.getEnergyLevel());
	}
	@Test
	public void testFatigue() {
		Model model = new Model();
		model.setEnergyLevel(50);
		model.fatigue();
		assertEquals(49, model.getEnergyLevel());
		assertEquals(55, model.getEnergyLevel());
	}
	@Test
	public void testBuildNest() {
		Model model = new Model();
		model.setNestProgress(0);
		model.buildNest();
		assertEquals(10, model.getNestProgress());
		assertEquals(1, model.getNestProgress());
	}
	
}