package Tests;

import org.junit.Test;

import Building.Building;

public class TestBuilding {

	Building b;
	
	//Tests creating a building with negative floors
	@Test(expected=IllegalArgumentException.class)
	public void testBuildingWithNegativeFloors() throws IllegalArgumentException {
		b = new Building(-1, -4);
	}
	
	//Tests creating a building with negative elevators
	@Test(expected=IllegalArgumentException.class)
	public void testBuildingWithNegativeElevator() throws IllegalArgumentException {
		b = new Building(-1, -4);
	}
	
	//Tests moving to a floor that does not exist
	@Test(expected=IllegalArgumentException.class)
	public void testMovingtoInvalidFloor() throws IllegalArgumentException {
		b = new Building(15, 6);
		b.moveElevatorToFloor(2, 0);
	}
	
	//Tests moving with an elevator that does not exist
	@Test(expected=IllegalArgumentException.class)
	public void testMovingInvalidElevator() throws IllegalArgumentException {
		b = new Building(15, 6);
		b.moveElevatorToFloor(0, 5);
	}


}
