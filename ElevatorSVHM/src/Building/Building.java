package Building;
import java.util.ArrayList;
import java.util.Iterator;

import Utils.Utilities;

import Elevator.Elevator;
import Elevator.ElevatorFactory;
import Elevator.ElevatorImpl;
import Floor.FloorListener;
import Floor.FloorListenerImpl;
import Floor.FloorManager;
import Floor.FloorRequestDTO;

/**
* This is the building that contains the elevator simulation, and the elevators are created and held in here.
* 
* @author Shabbir Vijapura/Harry Masuta
*/
public class Building implements FloorListener {
	
	/**
	 * Private integer that contains the numOfFloors and numOfElevators.
	 */
	private int numOfFloors, numOfElevators;
	
	/**
	 * Private class that contains the Elevator Implementation array.
	 */
	private ArrayList<ElevatorImpl> elevators;
	
	/**
	 * Private class that listens for changes in ElevatorFloor
	 */
	private FloorListenerImpl floorListener = new FloorListenerImpl();
	
	/**
	 * Private ArrayList of requests for Floor
	 */
	private ArrayList<FloorRequestDTO> requests = new ArrayList<FloorRequestDTO>(); 

	/**
	 * Public method that creates the building instance that holds all the elevators that are
	 * created and begins the threading for each elevator
	 * @param f - Number of floors that are needed/wanted in the simulation overall
	 * @param e - Number of elevators that are needed/wanted in the sumulation overall
	 */
	public Building (int f, int e) {
		
		//Create a building with passed in amount of floors and elevators
		System.out.println("Creating Building");
		setNumOfFloors(f);
		setNumOfElevators(e);
		System.out.println("Building created, " + numOfFloors + " Floors, " + numOfElevators + " Elevators");
		
		//Add Listener for changes in floor
		floorListener.addListener(this);
	}
	
	/**
	 * Public method that starts each of the threads in the elevator
	 * @throws Exception if thread is interrupted.
	 */
	public void start() throws Exception{
		
		//Start all threads to being simulation
		for(Elevator e : elevators){
			Thread t = new Thread(e);
			t.start();
		}

	}

	/**
	 * Public method that sets the number of floors
	 * @param numOfFloors - Tells us the total number of floors.
	 */
	public void setNumOfFloors(int numOfFloors) {
		
		//Uses FloorManager to create Floors based on number passed in Constructor
		FloorManager m = FloorManager.getInstance();
		m.setNumberOfFloors(numOfFloors);
		this.numOfFloors = numOfFloors;
	}

	/**
	 * Public method that sets the number of elevators and takes one parameter
	 * @param numOfElevators - Tells us the total number of elevators.
	 */
	public void setNumOfElevators(int numOfElevators) {
		
		//Creates Elevators using ElevatorFactory
		//TODO: Don't use 1 as default floor all the time
		for (int i = 0; i < numOfElevators; i++) {
			if (elevators == null)
				elevators = new ArrayList<ElevatorImpl>();
			elevators.add((ElevatorImpl) ElevatorFactory.create(i + 1, 1));
		}
		this.numOfElevators = numOfElevators;
	}
	
	/**
	 * Public void method that takes two parameters and moves the elevator from floor to floor
	 * @param elevatorNumber - Gives the unique elevatorNumber, 1, 2, 3, 4, 5 or 6
	 * @param floorNumber - Gives the floor number that the elevator will be moving towards.
	 */
	public void moveElevatorToFloor(int elevatorNumber, int floorNumber) {
		System.out.printf("%s Sending Elevator %d to Floor %d.\n", Utilities.timeToString(), elevatorNumber, floorNumber);
		ElevatorImpl e = getElevatorByNumber(elevatorNumber);
		e.insertDestination(floorNumber);
		synchronized(e) {
			e.notify();
		}
	}
	
	
	public void queueElevatorToFloorUntilReachingDefault(int elevatorNumber, int floorNumber) {
		FloorRequestDTO fr = new FloorRequestDTO (elevatorNumber, floorNumber);
		requests.add(fr);
	}
	
	/**
	 * Private implementation that retrieves the elevator by it's unique ID
	 * @param elevatorNumber - The elevator number that it will be gotten by (1 through 6)
	 * @return - The capable elevator.
	 */
	private ElevatorImpl getElevatorByNumber(int elevatorNumber) {
		for (int i = 0; i < elevators.size(); i++) {
			ElevatorImpl e = elevators.get(i);
			if (e.getElevatorNumber() == elevatorNumber)
				return e;
		}
		
		return null;
	}
	
	/**
	 * Public boolean that 'when' you reach the default floor, it checks again for more requests
	 * @param elevatorNum - The elevator number (1-6)
	 */
	@Override
	public boolean elevatorDidReachBottomFloorWithRequests(int elevatorNum) {
		//Checks if there are any Requests pending when reaching the Default Floor
		boolean hasAdditionalRequests = false;
        Iterator<FloorRequestDTO> it = requests.iterator();
        while (it.hasNext()) {
			FloorRequestDTO fDTO = it.next();
			if (fDTO.getElevatorNumber() == elevatorNum) {
				moveElevatorToFloor(fDTO.getElevatorNumber(), fDTO.getFloorNumber());
				hasAdditionalRequests = true;
				it.remove();
            }
        }

		return hasAdditionalRequests;
	}
		
}
