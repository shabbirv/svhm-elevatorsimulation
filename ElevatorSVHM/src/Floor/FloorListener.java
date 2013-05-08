package Floor;

/**
 * Interface that represents the behavior of a FloorListener.  Listens for the elevator
 * to reach the default floor and when an elevator changes floors
 * 
 * @author Shabbir Vijapura/Harmeet Masuta
 *
 */
public interface FloorListener {
	
	/**
	 * Public boolean that checks that when the elevator reaches the default floor
	 * if it has any more requests to process or if it should remain idle
	 * @param elevatorNum - Number of the elevator that is being checked
	 */
    public boolean elevatorDidReachDefaultWithRequests(int elevatorNum);
}

