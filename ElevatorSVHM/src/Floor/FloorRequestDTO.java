package Floor;

public class FloorRequestDTO {
	private int elevatorNumber;
	private int floorNumber;
	
	public FloorRequestDTO(int e, int f) {
		setElevatorNumber(e);
		setFloorNumber(f);
	}
	
	public int getElevatorNumber() {
		return elevatorNumber;
	}
	private void setElevatorNumber(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
	}
	public int getFloorNumber() {
		return floorNumber;
	}
	private void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

}
