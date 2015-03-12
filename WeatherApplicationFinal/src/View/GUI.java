package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class GUI{
	
	private JFrame  window;
	private JButton bt_run;
	private JPanel jp_north;
	private JPanel jp_south;
	private JTextArea textArea;
	private JScrollPane scrollpane;
	private JLabel ort, time, refreshTime;
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBoxTimes;
	private JComboBox<Integer> comboBoxRefreshTime;
	private String[] orter = {};
	private String[] times = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "21", "22", "23"};
	private Integer[] refreshTimes ={0, 1, 5, 10, 15, 20, 30, 60};
	
	
	/**
	 * Constructor for GUI
	 */
	public GUI() {
		
		
		
		//Create new instances
		window = new JFrame();
		jp_north = new JPanel();
		jp_south = new JPanel();
		bt_run = new JButton("RUN");
		textArea = new JTextArea();
		scrollpane = new JScrollPane(textArea);
		ort = new JLabel("Ort:");
		time = new JLabel("Tid:");
		refreshTime = new JLabel("RefreshTime: ");
		comboBox = new JComboBox<String>(orter);
		comboBoxTimes = new JComboBox<String>(times);
		comboBoxRefreshTime = new JComboBox<Integer>(refreshTimes);
		
		//scrollpane and textArea options
		scrollpane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEnabled(true);
		
		//comboBoxes
		comboBox.setEditable(true);
		comboBoxTimes.setEditable(true);
		comboBoxRefreshTime.setEditable(true);
		
		//Size content
		scrollpane.setPreferredSize(new Dimension(700, 100));
		
		//Adds components to JPanels
		
		jp_north.add(time);
		jp_north.add(comboBoxTimes);
		jp_north.add(ort);
		jp_north.add(comboBox);
		jp_north.add(refreshTime);
		jp_north.add(comboBoxRefreshTime);
		jp_north.add(bt_run);
		jp_south.add(scrollpane);
		
		
		//Position of JPanels
		window.setLayout(new BorderLayout());
		window.add(jp_south, BorderLayout.SOUTH);
		window.add(jp_north, BorderLayout.NORTH);
		
		//Window settings
		window.setSize(700, 200);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	/**
	 * @return inputOrt
	 */
	public String getOrt(){
		
			return String.valueOf(comboBox.getSelectedItem());
			
	}
	
	/**
	 * @return inputTime
	 */
	public String getTime(){
		
		return String.valueOf(comboBoxTimes.getSelectedItem());
		
	}
	
	/**
	 * @return refreshTime
	 */
	public int getRefreshTime(){
		
		return (Integer) comboBoxRefreshTime.getSelectedItem();
		
	}
	
	/**
	 * Adds String to comboBox
	 * @param s
	 */
	public void addItemToComboBox(ArrayList<String> s){
		
		for(String x : s ){

			comboBox.addItem(x);
		}
		
	}
	
	/**
	 * Prints s on textArea
	 * @param s
	 */
	public void setTextareaOutput(String s){
		
		textArea.append(s);
		textArea.append("\n");
	
	}
	
	/**
	 * Adds actionListener to bt_run
	 * @param listenerForWeatherButton
	 */
	public void addWeatherListener(ActionListener listenerForWeatherButton){
		
		bt_run.addActionListener(listenerForWeatherButton);
		
	}
	
	/**
	 * Adds actionListener to coboBoxRefreshTime
	 * @param listenerForComboBox
	 */
	public void addComboBoxListener(ActionListener listenerForComboBox){
		
		comboBoxRefreshTime.addActionListener(listenerForComboBox);
		
	}
	
	/**
	 * Displays error popup
	 * @param errorMessage
	 */
	public void displayErrorMessage(String errorMessage){
		
		JOptionPane.showMessageDialog(window, errorMessage);
		
	}

	
}
