package Building;
import java.util.ArrayList;
import java.util.Iterator;

import Elevator.Elevator;
import Elevator.ElevatorFactory;
import Elevator.ElevatorImpl;
import Floor.FloorListener;
import Floor.FloorListenerImpl;
import Floor.FloorManager;
import Floor.FloorRequestDTO;
import Utils.Utilities;

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
	public Building (int f, int e) throws IllegalArgumentException {
		if (f <= 0 || e <= 0) {
			throw new IllegalArgumentException("Number of Floors or Elevators cannot be negative");
		}
		
		//Create a building with passed in amount of floors and elevators
		System.out.printf("%s Creating Building\n", Utilities.timeToString());
		setNumOfFloors(f);
		setNumOfElevators(e);
		System.out.printf("%s Building created, " + numOfFloors + " Floors, " + numOfElevators + " Elevators\n", Utilities.timeToString());
		
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
	 * Public method returns the total amount of floors in a Building
	 * @return numOfFloors
	 */
	public int getNumOfFloors() {
		return this.numOfFloors;
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
	 * Public method returns the total amount of elevators in a Building
	 * @return numOfElevators
	 */
	public int getNumOfElevators() {
		return this.numOfElevators;
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
	 * @param elevatorNumber - Pass in which elevator number we want to move
	 * @param floorNumber - Gives the floor number that the elevator will be moving towards.
	 */
	public void moveElevatorToFloor(int elevatorNumber, int floorNumber) {
		if (elevatorNumber <= 0 || floorNumber <= 0) {
			throw new IllegalArgumentException("Elevator number and floor number cannot be negative");
		}
		
		System.out.printf("%s Sending Elevator %d to Floor %d.\n", Utilities.timeToString(), elevatorNumber, floorNumber);
		ElevatorImpl e = getElevatorByNumber(elevatorNumber);
		e.insertDestination(floorNumber);
		synchronized(e) {
			e.notify();
		}
	}
	
	/**
	 * Public void method queues requests that will be called once the Elevator reaches the Default Floor
	 * @param elevatorNumber - Passed in elevator number to create FloorRequestDTO
	 * @param floorNumber - Passed in floor number to create FloorRequestDTO
	 */
	public void queueElevatorToFloorUntilReachingDefault(int elevatorNumber, int floorNumber) {
		if (elevatorNumber <= 0 || floorNumber <= 0) {
			throw new IllegalArgumentException("Elevator number and floor number cannot be negative");
		}
		FloorRequestDTO fr = new FloorRequestDTO (elevatorNumber, floorNumber);
		requests.add(fr);
	}
	
	/**
	 * Private implementation that retrieves the elevator by it's unique ID
	 * @param elevatorNumber - The elevator number that the loop should be searching for
	 * @return - The matched elevator.
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
	 * @param elevatorNum - The elevator that reached the default floor
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
