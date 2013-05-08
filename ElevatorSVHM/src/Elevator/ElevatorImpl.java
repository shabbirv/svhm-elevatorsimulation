package Elevator;

import java.util.ArrayList;
import java.util.Collections;

import Utils.Utilities;

import Floor.Floor;
import Floor.FloorListenerImpl;
import Floor.FloorManager;
import Floor.FloorRequestDTO;
/**
 * An elevator implementation that creates an elevator
 * 
 * This class is the implementation of the Elevator interface.  In here it takes requests
 * at each destination involving the time to open and close the doors, letting people in and out,
 * the amount of time the doors remain open and closing the doors.  Also, if an elevator remains
 * at one floor for a certain 'period' of time, it times out and returns to its default floor
 * 
 * @author Shabbir Vijapura/Harmeet Masuta
 * 
 */
public class ElevatorImpl implements Elevator {
	
	/**
	 * The number of milliseconds it takes an elevator to move up and down each individual floor
	 * @see #goToNextFloor()
	 */
	private static long travelTime = 1000;
	
	/**
	 * The number of milliseconds it takes for the elevator doors to open and close
	 * @see #toggleDoorOpen()
	 */
	private static long doorToggleTime = 3000;
	
	/**
	 * The number of milliseconds the door remains open for people to get in and out
	 * @see #toggleDoorOpen(boolean)
	 */
	private static long doorIdleTime = 3000;
	
	/**
	 * The number of milliseconds an elevator waits with no destinations, and then returns to 
	 * the default floor
	 */
	private static long idleTime = 5000;
	
	/**
	 * The elevators individual identification number in the building (#'s 1-6)
	 * @see #getElevatorNumber()
	 */
	private int elevatorNumber;
	
	/**
	 * The floor that the elevator is 'currently' at which it could be stopped there or passing it
	 * @see #getCurrentFloor()
	 */
	private int currentFloor;
	
	/**
	 * The number of the 'default' floor the elevator will return to after there are no
	 * destinations left for the elevator
	 * @see #setDefaultFloorNum(int)
	 * @see #getDefaultFloor()
	 */
	private int defaultFloorNum;
	
	/**
	 * Private ArrayList that stores the destinations inside
	 * @see #insertDestination(int)
	 */
	private ArrayList<Integer> destinations = new ArrayList<Integer>();	
	
	/**
	 * Private class that informs those listening about changes in the Elevator Floor
	 * and when the Default floor is reached.
	 * @see #floorListenerImpl
	 */
	private FloorListenerImpl floorListenerImpl = new FloorListenerImpl();

	/**
	 * Obtains the direction/what direction the elevator needs to go, whether UP, DOWN, or NONE
	 * @see #getDirection()
	 * @see #setDirection(Elevator.Elevator.ElevatorDirection)
	 */
	private ElevatorDirection direction;
	
	/**
	 * The current status of the elevator
	 * @see #getStatus()
	 * @see #setStatus(Elevator.Elevator.ElevatorStatus)
	 */
	private ElevatorStatus status;
	
	/**
	 * A constructor that allows you to create a ElevatorImpl that sets the default floor,
	 * the elevator number, the direction, and its status
	 * @param e - The elevator that will be assigned, based off number
	 * @param floorNum - The default floor number that the elevator will return to after a timeout.
	 */
	
	public ElevatorImpl (int e, int floorNum) throws IllegalArgumentException {
		
		//Some initializer things
		currentFloor = floorNum;
		setDefaultFloorNum(floorNum);
		setElevatorNumber(e);
		setDirection(ElevatorDirection.UP);
		setStatus(ElevatorStatus.IDLE_AT_DEFAULT);
	}
	
	/**
	 * Has the important loop of the elevator.  It runs constantly because it needs to check
	 * for destinations that might have appeared in the queue.  After a certain amount of time
	 * with no 'destinations', it will return to the default floor
	 */
	@Override
	public void run() {
		try{

		while(true){
				if(destinations.size() == 0){
					if(getCurrentFloor() != getDefaultFloor()) { 
						System.out.printf("%s Elevator %d waiting for timeout.\n", Utilities.timeToString(), getElevatorNumber());
						synchronized(this){
							
							//Set the elevator to idle and its direction 
							setStatus(ElevatorStatus.IDLE);
							setDirection(ElevatorDirection.NONE);
							
							// Wait for timeout or new request
							this.wait(ElevatorImpl.idleTime);

							// If a new request is added ignore going to Default floor and continue loop
							if(destinations.size() != 0) 
								continue;						

							//No new requests go to default floor
							System.out.printf("%s Elevator %d timed out, returning to default floor.\n", Utilities.timeToString(), getElevatorNumber());
							this.insertDestination(getDefaultFloor());
						}
					}

					//If we are at the default floor
					if(getCurrentFloor() == getDefaultFloor()){ 
						
						//If you're already at the Default Floor don't show the message
						if (getStatus() != ElevatorStatus.IDLE_AT_DEFAULT)
							System.out.printf("%s Elevator %d returned to Default floor. No further destinations.\n",
									Utilities.timeToString(), getElevatorNumber());
						
						//Change status appropriately
						setStatus(Elevator.ElevatorStatus.IDLE_AT_DEFAULT);
						setDirection(ElevatorDirection.NONE);
						
						//Check if has any pending requests after reaching default floor
						if (!floorListenerImpl.didReachDefaultFloorWithAdditionalRequests(this.elevatorNumber)) {
							synchronized(this) { //No additional requests start waiting for requests
								this.wait();
							}
						}
					}
					
				}				
				else{
					goToNextFloor();
				}

		}

		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This moves the elevator to the next floor.  The elevator sleeps based off the time it takes
	 * to simulate the time it takes to move.  After arriving at the destination, it let's the
	 * people get off and on
	 * @see #arriveAtDestination()
	 * @throws InterruptedException if the sleeping threads are interrupted
	 */
	private void goToNextFloor() throws InterruptedException{

		//Run through loop until we reach our destination
		while(getCurrentFloor() != destinations.get(0)){
			
			//Are we going up or down?
			if(destinations.get(0) > getCurrentFloor()){ //Going up
				
				//Simulate delay for moving from floor to floor
				setDirection(ElevatorDirection.UP);
				Thread.sleep(ElevatorImpl.travelTime);

				System.out.printf("%s Elevator %d passing %d on way up to Floor %d. Remaining Destinations: %s\n", 
						Utilities.timeToString(), elevatorNumber, currentFloor, destinations.get(0), destinationsToString());
				
				//Increment the elevators current floor
				currentFloor++;

			}else if(destinations.get(0) < getCurrentFloor()){ // Going down
				
				//Simulate delay for moving from floor to floor
				setDirection(ElevatorDirection.DOWN);
				Thread.sleep(ElevatorImpl.travelTime);

				System.out.printf("%s Elevator %d passing %d on way down to Floor %d. Remaining Destinations: %s\n", 
						Utilities.timeToString(), elevatorNumber, currentFloor, destinations.get(0), destinationsToString());
				
				//Decrement the elevators current floor
				currentFloor--;
			}
		}

		//Loop ended, we have reached our destination
		reachedDestination();
	}
	
	/**
	 * Private method that deals with everything when an elevator arrives at its destination
	 * It removes the destination from the list, and opens/closes the door
	 * @see #toggleDoorOpen(boolean)
	 * @throws InterruptedException
	 */
	private void reachedDestination() throws InterruptedException {
		System.out.printf("%s Elevator %d arrived at Floor %d. Opening Door\n", Utilities.timeToString(), elevatorNumber, currentFloor);
		
		//We've arrived so remove destination from ArrayList
		destinations.remove(0);
		
		//Simulate door opening and closing
		toggleDoorOpen(true);
		toggleDoorOpen(false);
	}
	
	/**
	 * Thread that opens/closes the door and the amount of time in milliseconds that it should take
	 * @param open - Boolean check whether the door should be opened or closed
	 * @throws InterruptedException - If the thread is interrupted during sleep
	 */
	private void toggleDoorOpen(boolean open) throws InterruptedException {
		if (open) {
			Thread.sleep(ElevatorImpl.doorToggleTime);
			checkForPeople();
		} else {
			System.out.printf("%s Elevator %d Closing Door\n", Utilities.timeToString(), getElevatorNumber());
			Thread.sleep(ElevatorImpl.doorToggleTime);
		}
	}
	
	/**
	 * This private method checks for people and then waits for them to walk in,
	 * and then sets the direction/status
	 * @throws InterruptedException when the operation is interrupted
	 */
	private void checkForPeople() throws InterruptedException {
		System.out.printf("%s Elevator %d Waiting for people to walk in\n",Utilities.timeToString(), getElevatorNumber());
		
		if (destinations.size() == 0) {
			setDirection(ElevatorDirection.NONE);
			setStatus(ElevatorStatus.IDLE);
		}
		
		//Check if this floor has any pending requests
		//TODO Add "People" logic here
		Floor f = FloorManager.getInstance().getFloorAtNumber(currentFloor);
		ArrayList<FloorRequestDTO> requests = f.getRequests(this.getElevatorNumber(), this.getCurrentFloor());
		for (FloorRequestDTO request : requests) {
			insertDestination(request.getFloorNumber());
		}
		
		//Simulate waiting for people to come inside the elevator
		Thread.sleep(ElevatorImpl.doorIdleTime);
	}
	
	/**
	 * In here, this adds a destination to the elevators pending requests.  If the elevator is on floor 8
	 * and going up, it will only take requests above floor 8.  If the elevator is on floor 8 and going
	 * down, it will only take requests that are below floor 8.
	 * @param f - f is the floor that is being inserted into the destination
	 */
	public void insertDestination(int f) {
		if(f <= 0 || f > FloorManager.getInstance().getNumberOfFloors()) {
			throw new IllegalArgumentException("Cannot insert floor that is less than 1 or more than the total number of floors");
		}

		if ((direction == ElevatorDirection.UP && (f < getCurrentFloor() || f == getCurrentFloor()))) {
			System.out.printf("%s Elevator %d for Floor %d is not in the current direction of travel - ignored\n", Utilities.timeToString(), getElevatorNumber(), f);
			return;
		} else if ((direction == ElevatorDirection.DOWN) && (f > getCurrentFloor() || f == getCurrentFloor())) {
			System.out.printf("%s Elevator %d for Floor %d is not in the current direction of travel - ignored\n", Utilities.timeToString(), getElevatorNumber(), f);
			return;
		} 
			
		if(getStatus() == ElevatorStatus.IDLE || getStatus() == ElevatorStatus.IDLE_AT_DEFAULT){
			if(f > currentFloor)
				setDirection(ElevatorDirection.UP);
			else if(f < currentFloor)
				setDirection(ElevatorDirection.DOWN);
		}	
		
		destinations.add(f);
		sortDestinations();
	}
	
	/**
	 * Function that creates a string representation of the remaining destinations
	 * @return destinations as a string
	 */
	public String destinationsToString(){
		String s = "[";
		int destsLeft = destinations.size();
		for(int i = 0; i < destsLeft - 1; i++){
			s += destinations.get(i) + ", ";
		}

		if(destsLeft > 0){
			s += destinations.get(destsLeft - 1);
		}
		return s + "]";
	}
	
	/**
	 * Sorts the destinations ArrayList based on which direction the Elevator is moving towards
	 * Reverses the array if the elevator is moving down
	 */
	private void sortDestinations() {
		if(direction == ElevatorDirection.UP)
			Collections.sort(destinations);
		else if(direction == ElevatorDirection.DOWN){ //We are going down reverse the order
			Collections.sort(destinations);
			Collections.reverse(destinations);
		}
	}
	
	//Javadoc above explains everything below
	//Getters and Setters
	
	public int getCurrentFloor () {
		return currentFloor;
	}
	
	public static long getIdleTime() {
		return idleTime;
	}

	public static void setIdleTime(long idleTime) {
		ElevatorImpl.idleTime = idleTime;
	}
	
	private void setDefaultFloorNum(int floorNum) throws IllegalArgumentException {
		if(floorNum <= 0 || floorNum > FloorManager.getInstance().getNumberOfFloors()) {
			throw new IllegalArgumentException("Default floor cannot be less than 0 or greater than number of floors");
		}
		this.defaultFloorNum = floorNum;
	}
	
	public int getDefaultFloor () {
		return defaultFloorNum;
	}

	public ElevatorStatus getStatus() {
		return status;
	}

	public void setStatus(ElevatorStatus status) {
		this.status = status;
	}

	public int getElevatorNumber() {
		return elevatorNumber;
	}

	public void setElevatorNumber(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
	}
	
	public ElevatorDirection getDirection() {
		return direction;
	}
	
	public void setDirection(ElevatorDirection direction) {
		this.direction = direction;
	}

	public static long getDoorIdleTime() {
		return doorIdleTime;
	}

	public static void setDoorIdleTime(long doorIdleTime) {
		ElevatorImpl.doorIdleTime = doorIdleTime;
	}

	public static long getDoorToggleTime() {
		return doorToggleTime;
	}

	public static void setDoorToggleTime(long doorToggleTime) {
		ElevatorImpl.doorToggleTime = doorToggleTime;
	}

}
