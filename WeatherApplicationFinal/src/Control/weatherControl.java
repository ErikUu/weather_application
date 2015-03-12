package Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import Model.Weather;
import Model.weatherModel;
import View.GUI;

public class weatherControl{
	
	private GUI gui;
	private weatherModel wModel;
	
	/**
	 * Constructor for weatherControl
	 * @param wModel
	 * @param gui
	 */
	public weatherControl(weatherModel wModel, GUI gui) {
        
		this.wModel = wModel;
		this.gui = gui;
		this.gui.addWeatherListener(new WeatherListener());
		this.gui.addComboBoxListener(new ComboListener());
		
		parseSetComboBox();
		
		gui.addItemToComboBox(wModel.getComboBoxContent());	//Pars out all cities and sets them to comboBox
	}
	
	/**
	 * Sets the starting comboBox
	 */
	private void parseSetComboBox(){
		
		String ortName;
		Document xmlDoc = getDocument("./src/places.xml");
		
		NodeList rootNodes = xmlDoc.getElementsByTagName("places");
		Node rootNode = rootNodes.item(0);
		Element rootElement = (Element) rootNode;
		NodeList localityList = rootElement.getElementsByTagName("locality");
			for(int i = 0; i < localityList.getLength(); i++){
				Node locality = localityList.item(i);
				Element localityElement = (Element) locality;
				ortName = localityElement.getAttribute("name");
				wModel.getComboBoxContent().add(ortName);
				
			}
			
	}
	
	/**
	 * Parses docString
	 * @param docString
	 * @return docString
	 */
	private Document getDocument(String docString){
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //Creates DOM objects from xml-file
			DocumentBuilder builder = factory.newDocumentBuilder();					//Gets document from xml-file
			
			return builder.parse(docString);
			
		} catch (Exception ex){
			System.out.println(ex.getMessage());	
		}
		return null;
	}

	
	
	
	
	
	class WeatherListener implements ActionListener{
		
		private String lo = null, la = null;
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			
			String ort = gui.getOrt();
			String time = gui.getTime();
			
			parseCoordinates(ort);
			
			//Checks if the city is saved in the cache.
			if(!(wModel.getCacheCheckList().contains(ort)) || wModel.getCacheCheckList().size() == 0 ){
				cacheData(la, lo, time, ort);
				System.out.println("Saved " + ort + " to cache" + " - COMPLETE");
			}
			
			//Checks of cache needs to be updated. And removes old cache.
			for(int i = 0; i < wModel.getWeatherList().size(); i++){
				Weather w = wModel.getWeatherList().get(i);
				if(gui.getRefreshTime()<refreshTimeDifference(System.currentTimeMillis(), w.getRefreshTime()) && ort.compareTo(w.getOrt()) == 0){
					
					removeOldCache(ort);
					
					cacheData(la, lo, time, ort);
					System.out.println("Refreshing " + ort + " - COMPLETE");
					break;
				}
				
			}
			
			
			//Prints out the information about the city in the GUI.
			for(Weather w : wModel.getWeatherList()){
				if(ort.compareTo(w.getOrt()) == 0 && time.compareTo(w.getTime()) == 0){
					gui.setTextareaOutput(w.getTemp() + " Grader klockan " + w.getTime() + ",00   -   DATUM: " + w.getDate());
					System.out.println("RUN COMPLETE!");

					break;
				}
			}
		}
		
		/**
		 * Saves data to cache
		 * @param la
		 * @param lo
		 * @param inputTime
		 * @param inputOrt
		 */
		private void cacheData(String la, String lo, String inputTime, String inputOrt){
			
			String temp, time, date;
			int whatToSaveCounter = 0;
			
			try {
				URL u = new URL("http://api.yr.no/weatherapi/locationforecast/1.9/?lat=" +la + ";lon=" + lo);
				URLConnection con = u.openConnection();
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document xmlWeb = builder.parse(new InputSource(con.getInputStream()));
				NodeList weatherTimeList = xmlWeb.getElementsByTagName("time");
				
				for(int i = 0; i < weatherTimeList.getLength(); i++){
					Node weatherTime = weatherTimeList.item(i);
					Element weatherTimeElement = (Element) weatherTime;
					
					NodeList nlist = weatherTimeElement.getElementsByTagName("temperature");
					Element t = (Element) nlist.item(0);
					
					if(nlist.item(0) != null){
						time = weatherTimeElement.getAttribute("from");
						String[] parts = time.split("T");
						date = parts[0];
						time = parts[1].substring(0,2);
						temp = t.getAttribute("value");
						
						if(whatToSaveCounter >= 2){
							wModel.getWeatherList().add(new Weather(inputOrt, time, temp, System.currentTimeMillis(), date));
//							System.out.println(date + " : " + time + " : " + temp);
						}	
						whatToSaveCounter++;	
					}
				}
				wModel.getCacheCheckList().add(inputOrt);	//makes it easy to see if the ort is saved to the cache
				
				
			} catch (IOException e) {
				gui.displayErrorMessage("ERROR");
			} catch (SAXException e) {
				gui.displayErrorMessage("ERROR");
			} catch (ParserConfigurationException e) {
				gui.displayErrorMessage("ERROR");
			}
		}
		
		/**
		 * Parse Coordinates
		 * @param ort
		 */
		private void parseCoordinates(String ort){	
			
			String ortName, longitude, latitude;;
			Document xmlDoc = getDocument("./src/places.xml");
			
			NodeList rootNodes = xmlDoc.getElementsByTagName("places");
			Node rootNode = rootNodes.item(0);
			Element rootElement = (Element) rootNode;
			NodeList localityList = rootElement.getElementsByTagName("locality");
			for(int i = 0; i < localityList.getLength(); i++){
				Node locality = localityList.item(i);
				Element localityElement = (Element) locality;
				ortName = localityElement.getAttribute("name");
		
				Node location = localityElement.getElementsByTagName("location").item(0);
				Element locationElement = (Element) location;
				longitude = locationElement.getAttribute("longitude");
				latitude = locationElement.getAttribute("latitude");
				
				if(ortName.compareTo(ort) == 0){
					lo = longitude;
					la = latitude;
				}
			}
		}
		
		/**
		 * @param currentTime
		 * @param weatherTime
		 * @return timeDifference in sec
		 */
		private long refreshTimeDifference(long currentTime, long weatherTime){
			long timeDifference;
			
			timeDifference = currentTime - weatherTime;
			return timeDifference/1000;
			
		}
		
		/**
		 * Removes old cache
		 * @param ort
		 */
		private void removeOldCache(String ort){
			
			int i = 0;
			
			while(i < wModel.getWeatherList().size()){
				if(ort.compareTo(wModel.getWeatherList().get(i).getOrt()) == 0){
					wModel.getWeatherList().remove(i);
				}else{
					i++;
				}	
			}
		}
		
	}
	
	class ComboListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			wModel.getWeatherList().clear();
			wModel.getCacheCheckList().clear();
			
			
		}
		
		
	}
	

}
