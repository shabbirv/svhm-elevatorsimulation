package Floor;

import java.util.ArrayList;

/**
 * Class that shows the floor and its behavior.  It defines the floorNumber, 
 * as well as getting requests.
 * 
 * @author Shabbir Vijapura/Harmeet Masuta
 *
 */
public class Floor  {
	
	/**
	 * Simply the integer floorNumber
	 */
	private int floorNumber;
	
	/**
	 * Private ArrayList which takes the floor request (data transfer objects) and 
	 * adds them into the array
	 */
	private ArrayList<FloorRequestDTO> frs;
	
	/**
	 * Public method floor which then sets the floorNumber and looks at the FloorRequestDTO ArrayList
	 * @param f - The floor in the building that is being mentioned
	 */
	public Floor (int f) {
		setFloorNumber(f);
		frs = new ArrayList<FloorRequestDTO>();
	}
	
	/**
	 * Public integer that retrieves the floor number and then afterwards it is returned
	 * @return
	 */
	public int getFloorNumber () {
		return this.floorNumber;
	}
	
	/**
	 * Sets the floor number that the elevator needs to know to go in a certain direction
	 * @param floorNumber - The floor number in the building that an elevator needs to get to
	 */
	private void setFloorNumber (int floorNumber) {
		this.floorNumber = floorNumber;
	}

	/**
	 * Adds a request to the elevator and then retrieves the floor number
	 * @param request - takes in a request and processes it as long as the request isn't
	 * for an invalid floor
	 */
	public void addRequest(FloorRequestDTO request) {
		if (request.getFloorNumber() == floorNumber) {
			new Exception("Trying to add request for wrong floor");
		} else {
			frs.add(request);
		}
	}
	
	/**
	 * An ArrayList that get and store the FloorRequest based off the elevatorNumber and floorNumber
	 * @param elevatorNumber - Number of the elevator that responds to the request
	 * @param floorNumber - Floor number the elevator will be arriving at
	 * @return - the requests that were made by the users for an elevator to their floor
	 */
	public ArrayList<FloorRequestDTO> getRequests(int elevatorNumber, int floorNumber) {
		ArrayList<FloorRequestDTO> requests = new ArrayList<FloorRequestDTO>();
		for (FloorRequestDTO fr : frs) {
			if (fr.getElevatorNumber() == elevatorNumber && fr.getFloorNumber() != floorNumber)
				requests.add(fr);
		}
		
		return requests;
	}

	
}
