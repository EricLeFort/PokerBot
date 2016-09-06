package handStorage;

/**
* @author Eric Le Fort
* @version 1.0
*/
class Date{
	private String timezone;
	private int year, month, day, hour, min, sec;
	
	/**
	 * Creates a new <code>Date</code> based on the values passed in.
	 * @param year - An integer from 0-9999.
	 * @param month - An integer from 1-12.
	 * @param day - An integer from 1-31.
	 * @param hour - An integer from 0-23.
	 * @param min - An integer from 0-59.
	 * @param sec - An integer from 0-59.
	 * @param timezone - A <code>String</code> containing the timezone code.
	 * @throws IllegalArgumentException
	 */
	public Date(int year, int month, int day, int hour, int min, int sec, String timezone) throws IllegalArgumentException{
		if(year < 0 || year > 9999
				|| month < 1 || month > 12
				|| day < 1 || day > 31
				|| hour < 0 || hour > 23
				|| min < 0 || min > 59
				|| sec < 0 || sec > 59){
			throw new IllegalArgumentException("One of the parameters was outside of the acceptable range.");
		}
		
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.sec = sec;
		this.timezone = timezone;
	}//Constructor
	
	/**
	 * Returns a <code>String</code> in the format "YYYY/MM/DD HH:MM:SS TIMEZONE" based on the values stored
	 * in this <code>Date</code>.
	 * @return A <code>String</code> representing this <code>Date</code>.
	 */
	@Override
	public String toString(){
		return String.format("%04d/%02d/%02d %02d:%02d:%02d " + timezone, year, month, day, hour, min, sec);
	}//toString()
	
}//Date
