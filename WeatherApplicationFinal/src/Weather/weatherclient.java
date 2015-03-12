package Weather;



import Control.weatherControl;
import Model.weatherModel;
import View.GUI;


public class weatherclient {

	public static void main(String[] args) {
		
		weatherModel wm = new weatherModel();
		GUI gui = new GUI();
		new weatherControl(wm, gui);
		
		
	}
}
