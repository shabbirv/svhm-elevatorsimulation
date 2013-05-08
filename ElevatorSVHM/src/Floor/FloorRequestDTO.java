package Floor;

/**
 * Data Transfer Object that holds elevator and floor number to create requests
 * 
 * @author Shabbir Vijapura/Harmeet Masuta
 *
 */
public class FloorRequestDTO {
	
	/**
	 * Elevator number that is being held by the object
	 */
	private int elevatorNumber;
	
	/**
	 * Floor number that is being held by the object
	 */
	private int floorNumber;
	
	/**
	 * Public data transfer object that once again holds the elevator and floor numbers as 
	 * Floor requests with parameters e and f
	 * @param e - Takes the elevator as a parameter so it can be accessed
	 * @param f - Takes the floor as a parameter so it can be accessed
	 */
	public FloorRequestDTO(int e, int f) {
		setElevatorNumber(e);
		setFloorNumber(f);
	}
	
	/**
	 * Public integer that gets the elevator number that applies
	 * @return the elevator number
	 */
	public int getElevatorNumber() {
		return elevatorNumber;
	}
	
	/**
	 * Sets the elevator number by its unique ID that will retrieve the floor call
	 * @param elevatorNumber - Number of the elevator that was set
	 */
	private void setElevatorNumber(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
	}
	
	/**
	 * Gets the floor number that the elevator will report to
	 * @return the floor number to the elevator to add to the queue
	 */
	public int getFloorNumber() {
		return floorNumber;
	}
	
	/**
	 * Sets the floor number with one parameter
	 * @param floorNumber - Number of the floor
	 */
	private void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

}
