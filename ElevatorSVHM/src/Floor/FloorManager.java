package Floor;
import java.util.ArrayList;

/**
 * This class manages the Floor and sets/gets the number of floors, and
 * The class also initializes (int numFloors) and (int floorNumber)
 * 
 * @author Shabbir Vijapura/Harmeet Masuta
 */
public class FloorManager {
	
		/**
	 	* Keeps the instance ready for use as a Singleton.
	 	*/
		private static FloorManager floorManager;
		
		/**
		 * Contains the instances for floor for the entire building
		 */
		private static ArrayList<Floor> floors;
		
		/**
		 * A private constructor that creates floor instances based on how many building stories there are and keeps them in an ArrayList.
		 */
		private FloorManager() {
			floors = new ArrayList<Floor>();
		}
		
		/**
		 * A public static method that belongs to the FloorManager instance.
		 * @return The Floor Manager Singleton
		 */
		public static FloorManager getInstance() {
			if(floorManager == null) {
				floorManager = new FloorManager();
				floors = new ArrayList<Floor>();
			}
				
			return floorManager;
		}
		
		/**
		 * Public - that gets the number of floors
		 * @param numFloors - The number of total floors that are in the building.
		 */
		public void setNumberOfFloors(int numFloors) {
			for(int i=1; i <= numFloors; i++){
				floors.add(new Floor(i));
			}
		}
		
		/**
		 * Method that returns how many Floors are in the Building
		 * @return number of floors in a Building
		 */
		public int getNumberOfFloors(){
			return floors.size();
		}
		
		/**
		 * Public method that searches through the floors ArrayList and finds the matching floor
		 * @param floorNumber is what floorNumber the elevator needs to understand
		 * @return which floorNumber the elevator needs to get to.
		 */
		public Floor getFloorAtNumber(int floorNumber) {
			for (int i = 0; i < floors.size(); i++) {
				Floor floor = floors.get(i);
				if (floor.getFloorNumber() == floorNumber)
					return floor;
			}
			return null;
		}
}
