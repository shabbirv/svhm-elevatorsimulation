package Elevator;

/**
 * The interface for Elevator that defines the behavior for all elevator implementations that are done
 * 
 * Gets the direction, status and insert's the future destination.
 * 
 * @author Shabbir Vijapura/Harmeet Masuta
 *
 */
public interface Elevator extends Runnable {

	/**
	 * Public enum that defines all the different directions that an Elevator instance can be in
	 * 
	 * Elevator can go UP, DOWN, or neither(NONE)
	 */
	public enum ElevatorDirection {
		UP,
		DOWN,
		NONE
	}
	
	/**
	 * Public enum that defines all the different states that an Elevator instance can be in
	 * @see #getCurrentStatus()
	 *
	 */
	public enum ElevatorStatus {
		IDLE,
		IDLE_AT_DEFAULT,
		DOORS_OPENING,
		DOORS_CLOSING,
		DOORS_OPENED,
		MOVING
	}
	
	/**
	 * Public method that inserts the destination into the Elevator Array and stores it
	 * @param f - Takes the floor and stores it as a future Elevator destination
	 */
	public void insertDestination(int f) throws InterruptedException;
		
}
