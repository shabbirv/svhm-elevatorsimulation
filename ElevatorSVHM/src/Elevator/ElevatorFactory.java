package Elevator;

/**
 * Public Elevator Factory that create implementations that fit the parameters best.
 * 
 * @author Shabbir Vijapura/Harmeet Masuta
 *
 */
public class ElevatorFactory {
	
	/**
	 * Part of the implementations created that fit the parameters
	 * @param elevatorNum - Number of the elevator
	 * @param floorNum - Number of the floor
	 * @return - Best fit elevator who can complete the duty
	 */
	public static Elevator create(int elevatorNum, int floorNum) {
		return new ElevatorImpl(elevatorNum, floorNum);
	}
}
