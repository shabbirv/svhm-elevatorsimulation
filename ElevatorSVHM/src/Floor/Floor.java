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
	 * 
	 * @param request
	 */
	public void addRequest(FloorRequestDTO request) {
		if (request.getFloorNumber() == floorNumber) {
			new Exception("Trying to add request for wrong floor");
		} else {
			frs.add(request);
		}
	}
	
	public ArrayList<FloorRequestDTO> getRequests(int elevatorNumber, int floorNumber) {
		ArrayList<FloorRequestDTO> requests = new ArrayList<FloorRequestDTO>();
		for (FloorRequestDTO fr : frs) {
			if (fr.getElevatorNumber() == elevatorNumber && fr.getFloorNumber() != floorNumber)
				requests.add(fr);
		}
		
		return requests;
	}

	
}
