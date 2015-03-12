package Model;

import java.util.ArrayList;

public class weatherModel {
	
	private ArrayList<String> comboBoxContent = new ArrayList<String>();
	private ArrayList<Weather> weatherList = new ArrayList<Weather>();
	private ArrayList<String> cacheCheckList = new ArrayList<String>();
	
	/**
	 * @return weatherList
	 */
	public ArrayList<Weather> getWeatherList(){
		return weatherList;
	}
	
	/**
	 * @return comboBoxContent
	 */
	public ArrayList<String> getComboBoxContent(){
		return comboBoxContent;
	}
	
	/**
	 * @return cacheCeckList
	 */
	public ArrayList<String> getCacheCheckList() {
		return cacheCheckList;
	}

	/**
	 * sets cacheList
	 * @param cacheCheckList  
	 */
	public void setCacheCheckList(ArrayList<String> cacheCheckList) {
		this.cacheCheckList = cacheCheckList;
	}

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
}
