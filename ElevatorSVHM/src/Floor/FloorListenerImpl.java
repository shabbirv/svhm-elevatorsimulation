package Floor;
import java.util.ArrayList;

/**
 * Class that allows those that implement it to become listeners.  
 * Changes in the floor of the elevator are reported here and any listeners are notified 
 * of the change
 * 
 * @author Shabbir Vijapura/Harmeet Masuta
 *
 */
public class FloorListenerImpl {
	
	/**
	 * Static ArrayList of listeners
	 */
    static private ArrayList<FloorListener> listeners = new ArrayList<FloorListener>();

    /**
     * Public function that allows any class that implements FloorListener to become a listener
     * @param toAdd - A class that implements FloorListener
     */
    public void addListener(FloorListener toAdd) {
        listeners.add(toAdd);
    }

    /**
     * Public boolean function that is called once the elevator reaches the default floor
     * @param elevatorNum - Which elevator reached the default floor
     * @return True if there are pending requests and otherwise False
     */
    public boolean didReachDefaultFloorWithAdditionalRequests(int elevatorNum) {
        // Notify everybody that may be interested.
    	boolean hasAdditionalRequests = false;
        for (FloorListener fl : listeners)
            hasAdditionalRequests = fl.elevatorDidReachDefaultWithRequests(elevatorNum);
        
        return hasAdditionalRequests;
    }
}
