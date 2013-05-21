package Tests;

import org.junit.Test;

import Building.Building;

public class TestBuilding {

	Building b;
	
	@Test(expected=IllegalArgumentException.class)
	public void testBuildingWithNegativeFloors() throws IllegalArgumentException {
		b = new Building(-1, -4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBuildingWithNegativeElevator() throws IllegalArgumentException {
		b = new Building(-1, -4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMovingtoInvalidFloor() throws IllegalArgumentException {
		b = new Building(15, 6);
		b.moveElevatorToFloor(2, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMovingInvalidElevator() throws IllegalArgumentException {
		b = new Building(15, 6);
		b.moveElevatorToFloor(0, 5);
	}


}
