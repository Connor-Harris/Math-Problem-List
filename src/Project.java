import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.awt.event.*;

public class Project {

	//FONTS
	public Font fieldFont;
	public Font bigTitleFont;
	public Font titleFont;
	public Font lblFont;
	public DecimalFormat decimalFormat;
	public int currentDecimalPlaces = 3;
	//COLORS
	public final Color backgroundColor = Color.WHITE;
	public final Color btnColor = new Color(3,57,108);
	public final Color workBackgroundColor = new Color(240,240,240);
	//DIMENSIONS
	public final double screenWidth = Toolkit.getDefaultToolkit()
										.getScreenSize().getWidth();
	public final double screenHeight = Toolkit.getDefaultToolkit()
										.getScreenSize().getHeight();
	public int tableRowHeight;
	public Dimension fieldDim;
	
	//PANELS
	public MainFrame mainFrame;
	public NewListPanel newListPanel;
	public WorkListPanel workPanel;
	
	//DATA
	public ArrayList<Problem> problemList;
	
	//Save Data
	public File savedFile;
	public boolean isSaved;
	
	
	public Project() {
		
		int fieldFontInt = (int) (screenHeight / 40);
		int bigTitleFontInt = (int) (screenHeight / 25);
		int titleLblFontInt = (int) (screenHeight / 35);
		fieldFont = new Font("Georgia", Font.PLAIN, fieldFontInt);
		bigTitleFont = new Font("Georgia", Font.BOLD, bigTitleFontInt);
		lblFont = new Font("Georgia", Font.PLAIN, titleLblFontInt);
		titleFont = new Font("Georgia", Font.BOLD, titleLblFontInt);
		tableRowHeight = (int) (screenHeight / 15);
		//FieldDim
		int fieldDimW = (int) (screenWidth / 7);
		int fieldDimH = (int) (screenHeight / 25);
		fieldDim = new Dimension(fieldDimW, fieldDimH);
		
		//DATA
		problemList = new ArrayList<Problem>();
		
		//Save Data
		isSaved = false;
		savedFile = null;
		
		//DecimalFormat initializer
		setDecimalFormat(3);
		
	}
	
	public void setCenterPanel(JPanel panel) {
		BorderLayout layout = (BorderLayout) mainFrame.mainPanel.getLayout();
		mainFrame.mainPanel.remove
					(layout.getLayoutComponent(BorderLayout.CENTER));
		mainFrame.mainPanel.add(panel, BorderLayout.CENTER);
		mainFrame.mainPanel.repaint();
		mainFrame.mainPanel.revalidate();
				
	}
	public void setMainMenu(boolean menu) {
		if(menu) {
			mainFrame.menuBtnPanel.setVisible(false);
			mainFrame.menuPanel.remove(mainFrame.menuBtnPanel);
			setCenterPanel(mainFrame.startPanel);
			
		} else {
			mainFrame.menuBtnPanel.setVisible(true);
			mainFrame.menuPanel.add(mainFrame.menuBtnPanel);
		}
	}
	
	public void UPDATE_APPLICATION() {
		workPanel.update();
	}
	
	public void openProject(OpenPackage openPackage) {
		setMainMenu(false);
		mainFrame.setEditList();
		problemList = openPackage.getProblemList();
		decimalFormat = openPackage.getDecimalFormat();
		currentDecimalPlaces = openPackage.getDecimalPlacesInt();
		newListPanel.resetTable();
		workPanel.setPageLbl();
		workPanel.setMainPanel();
		int titleEnd = savedFile.getName().length() - 4;
		mainFrame.setTitle("Problem List  -  " 
					+ savedFile.getName().substring(0, titleEnd));
	}
	
	public void setDecimalFormat(int places) {
		PlaceValueEditor pv = new PlaceValueEditor();
		this.decimalFormat = pv.getDecimalFormat(places);
		this.currentDecimalPlaces = places;
	}
	
}
