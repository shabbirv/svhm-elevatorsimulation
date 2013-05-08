package Floor;
import java.util.ArrayList;


public class FloorListenerImpl {
    static private ArrayList<FloorListener> listeners = new ArrayList<FloorListener>();

    public void addListener(FloorListener toAdd) {
        listeners.add(toAdd);
    }

    public boolean didReachDefaultFloorWithAdditionalRequests(int elevatorNum) {
        // Notify everybody that may be interested.
    	boolean hasAdditionalRequests = false;
        for (FloorListener fl : listeners)
            hasAdditionalRequests = fl.elevatorDidReachDefaultWithRequests(elevatorNum);
        
        return hasAdditionalRequests;
    }
}
