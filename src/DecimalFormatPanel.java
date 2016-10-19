import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class DecimalFormatPanel extends JPanel {

	private JComboBox<String> placePicker;
	
	public DecimalFormatPanel() {

		String[] placeArray = new String[] {"0", "1", "2", "3", "4", "5"};
		placePicker = new JComboBox<String>(placeArray);
		int currentPlaces = Home.project.currentDecimalPlaces;
		placePicker.setSelectedIndex(currentPlaces);
		placePicker.setFont(Home.project.lblFont);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel titleLbl = new JLabel("Significant Decimal Places: ");
		titleLbl.setFont(Home.project.lblFont);
		
		int boxHeight = (int) (Home.project.screenHeight / 22);
		int boxWidth = (int)(Home.project.screenWidth / 22);
		Dimension boxDim = new Dimension(boxWidth, boxHeight);
		placePicker.setPreferredSize(boxDim);
		
		this.add(titleLbl);
		this.add(placePicker);
		
	}
	
	public int getDecimalPlace() {
		return placePicker.getSelectedIndex();
	}
	
	
	
}
