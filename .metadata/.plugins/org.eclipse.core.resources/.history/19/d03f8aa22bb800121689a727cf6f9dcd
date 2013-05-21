package Tests;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import Building.Building;
import Elevator.ElevatorImpl;

public class TestElevator {
	ElevatorImpl imp;
	public static Building b;
	
	@BeforeClass
	public static void setup() {
		b = new Building(15, 7);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidDefaultFloor() throws InterruptedException {
		imp = new ElevatorImpl(20, 30);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInsertingInvalidFloor() {
		imp = new ElevatorImpl(2, 1);
		imp.insertDestination(20);
	}
	
	@Test
	public void testTimeOutToDefaultFloor() throws InterruptedException {
		imp = new ElevatorImpl(1, 1);
		Thread t = new Thread(imp);
		t.start();
		imp.insertDestination(3);
		synchronized(imp) {
			imp.notify();
		}
		Thread.sleep(20000);
		assertTrue("Timed out to default floor", imp.getCurrentFloor() == imp.getDefaultFloor());
	}

}
