package Utils;
import java.util.Calendar;

/**
 * Class that contains helper functions that are used throughout the project such as 
 * the calendar, and time in minutes, hours, and seconds
 * 
 * @author Shabbir Vijapura/Harmeet Masuta
 *
 */
public class Utilities {
	
	
	/**
	 * Formats the current time
	 * @return the formatted string of the current time
	 */
	public static String timeToString(){
		Calendar c = Calendar.getInstance();
		return String.format("%02d:%02d:%02d ", c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
	}
	
}
