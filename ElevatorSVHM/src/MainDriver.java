import Building.Building;
import Floor.Floor;
import Floor.FloorManager;
import Floor.FloorRequestDTO;


public class MainDriver {


	public static void main(String[] args) throws Exception {
		Building b = new Building(15, 6);
		
		//Start simulation
		b.start();
		
		//Add small delay to make sure building is ready to receive requests
		Thread.sleep(300);
		
		b.moveElevatorToFloor(1, 11);
		b.moveElevatorToFloor(2, 14);
		b.moveElevatorToFloor(2, 13);
		b.moveElevatorToFloor(2, 15);
		
		//Queue these floor requests when elevator 1 reaches its default floor
		b.queueElevatorToFloorUntilReachingDefault(1, 10);
		b.queueElevatorToFloorUntilReachingDefault(1, 1);
		
		//These requests will only be received when elevator 1 goes to the 10th floor
		//This will be replaced by "People" logic 
		Floor f = FloorManager.getInstance().getFloorAtNumber(10);
		f.addRequest(new FloorRequestDTO(1, 2));
		f.addRequest(new FloorRequestDTO(1, 5));
		f.addRequest(new FloorRequestDTO(1, 3));
	}
	
}
