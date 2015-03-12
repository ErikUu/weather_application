package Model;

public class Weather {

	private String ort, time, temp , date;
	private long refreshTime;
	
	/**
	 * Constructor for Weather
	 * @param ort
	 * @param time
	 * @param temp
	 * @param refreshTime
	 * @param date
	 */
	public Weather(String ort, String time, String temp, long refreshTime, String date){
		
		this.ort = ort;
		this.time = time;
		this.temp = temp;
		this.refreshTime = refreshTime;
		this.date = date;
		
		
	}
	
	/**
	 * @return ort
	 */
	public String getOrt() {
		return ort;
	}
	
	/**
	 * @return time
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * @return temp
	 */
	public String getTemp() {
		return temp;
	}

	/**
	 * @return refreshTime
	 */
	public long getRefreshTime() {
		return refreshTime;
	}
	
	/**
	 * @return date
	 */
	public String getDate(){
		return date;
	}
	
	
	
	
	
}
