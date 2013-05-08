package Utils;
import java.util.Calendar;


public class Utilities {
	
	public static String timeToString(){
		Calendar c = Calendar.getInstance();
		return String.format("%02d:%02d:%02d ", c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
	}
	
}
