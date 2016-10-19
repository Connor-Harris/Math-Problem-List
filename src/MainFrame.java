import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

	public JPanel startPanel;
	public JPanel mainPanel;
	private JButton newListBtn;
	private JButton openListBtn;
	public JPanel menuPanel;
	private JButton menuBtn;
	public JPanel menuBtnPanel;
	
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem decimalItem;
	
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
	
	public MainFrame() {
		
		/***PROJECT PORT***/
		Project project = new Project();
		Home home = new Home(project);
		home.project.mainFrame = this;
		
		double screenWidth = Home.project.screenWidth;
		double screenHeight = Home.project.screenHeight;
		int thisW = (int) (screenWidth / 1.4);
		int thisH = (int) (screenHeight / 1.2);
		int startPosInt = (int) (screenHeight / 25);
		this.setBounds(startPosInt, startPosInt, thisW, thisH);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new CloseListener());
		this.setTitle("Problem List");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setMenuPanel();
		setStartPanel();
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(startPanel, BorderLayout.CENTER);
		mainPanel.add(menuPanel, BorderLayout.NORTH);
		this.add(mainPanel);
		
	}
	
	private void setStartPanel() {
		
		startPanel = new JPanel();
		
		StartButtonsListener sbl = new StartButtonsListener();
		int btnW = (int) (Home.project.screenWidth / 6);
		int btnH = (int) (Home.project.screenHeight / 12);
		Dimension btnDim = new Dimension(btnW, btnH);
		newListBtn = new JButton("Create New List");
		openListBtn =  new JButton("Open List");
		newListBtn.addActionListener(sbl);
		openListBtn.addActionListener(sbl);
		newListBtn.setFont(Home.project.titleFont);
		openListBtn.setFont(Home.project.titleFont);
		newListBtn.setPreferredSize(btnDim);
		openListBtn.setPreferredSize(btnDim);
		newListBtn.setBackground(Home.project.btnColor);
		openListBtn.setBackground(Home.project.btnColor);
		newListBtn.setForeground(Color.WHITE);
		openListBtn.setForeground(Color.WHITE);
		
		
		startPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		startPanel.setBackground(Home.project.backgroundColor);
		int topPadding = (int) (Home.project.screenHeight / 3);
		EmptyBorder homePadding = new EmptyBorder(topPadding, 0,0,0);
		startPanel.setBorder(homePadding);
		
		startPanel.add(openListBtn);
		startPanel.add(newListBtn);
		
		NewListPanel newListPanel = new NewListPanel();
		WorkListPanel workPanel = new WorkListPanel();
		Home.project.newListPanel = newListPanel;
		Home.project.workPanel = workPanel;
		
	}
	
	private void setMenuPanel() {
		
		menuPanel = new JPanel(new GridLayout(2,1));
		menuPanel.setBackground(Home.project.backgroundColor);
		menuBtn = new JButton("Main Menu");
		menuBtn.setBackground(Home.project.btnColor);
		menuBtn.setFont(Home.project.fieldFont);
		menuBtn.setForeground(Color.WHITE);
		menuBtn.addActionListener(new MainMenuListener());
		menuBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		menuBtnPanel.setBackground(Home.project.backgroundColor);
		menuBtnPanel.add(menuBtn);
		
		JMenuBar menuBar = new JMenuBar();
		int menuBarHeight = (int) (Home.project.screenWidth / 40);
		menuBar.setPreferredSize(new Dimension(100,menuBarHeight));
		menuBar.setBackground(Home.project.btnColor);
		
		int menuWidth = (int) (Home.project.screenWidth / 16);
		Dimension menuDim = new Dimension(menuWidth, menuBarHeight);
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(Home.project.fieldFont);
		fileMenu.setForeground(Color.WHITE);
		JMenu settingsMenu = new JMenu("Settings");
		settingsMenu.setFont(Home.project.fieldFont);
		settingsMenu.setForeground(Color.WHITE);
		fileMenu.setPreferredSize(menuDim);
		settingsMenu.setPreferredSize(menuDim);
		
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		saveAsItem = new JMenuItem("Save As");
		openItem.setFont(Home.project.fieldFont);
		saveItem.setFont(Home.project.fieldFont);
		saveAsItem.setFont(Home.project.fieldFont);
		MenuListener ml = new MenuListener();
		openItem.addActionListener(ml);
		saveItem.addActionListener(ml);
		saveAsItem.addActionListener(ml);
		openItem.setAccelerator(KeyStroke.getKeyStroke
				('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveItem.setAccelerator(KeyStroke.getKeyStroke
				('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		
		decimalItem = new JMenuItem("Set Decimal Value");
		decimalItem.setFont(Home.project.fieldFont);
		decimalItem.addActionListener(new DecimalListener());
		settingsMenu.add(decimalItem);
		
		menuBar.add(fileMenu);
		menuBar.add(settingsMenu);
		menuPanel.add(menuBar);
		
		
	}
	
	private class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == openItem) {
				Open open = new Open();
			} else if(e.getSource() == saveItem) {
				Save save = new Save();
				
			} else if(e.getSource() == saveAsItem) {
				SaveAs saveAs = new SaveAs();
			}
		}
	}
	
	private class StartButtonsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == openListBtn) {
				Open open = new Open();
			} else if(e.getSource() == newListBtn) {
				Home.project.setCenterPanel(Home.project.newListPanel);
				Home.project.setMainMenu(false);
				menuBtn.setText("Work List");
			}
		}
	}
	
	private class MainMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(menuBtn.getText().equals("Work List")) {
				setWorkPanel();
			} else if(menuBtn.getText().equals("Edit List")) {
				setEditList();
			}
		}
	}
	private class DecimalListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == decimalItem) {
				DecimalFormatPanel dfp = new DecimalFormatPanel();
				int result = JOptionPane.showConfirmDialog
					(null, dfp, "Edit Significant Place Value", 
					JOptionPane.INFORMATION_MESSAGE);
				if(result == JOptionPane.OK_OPTION) {
					int places = dfp.getDecimalPlace();
					Home.project.setDecimalFormat(places);
				}
			}
		}
	}
	public void setEditList() {
		menuBtn.setText("Work List");
		Home.project.setCenterPanel(Home.project.newListPanel);
	}
	public void setWorkPanel() {
		menuBtn.setText("Edit List");
		Home.project.setCenterPanel(Home.project.workPanel);
	}

}



