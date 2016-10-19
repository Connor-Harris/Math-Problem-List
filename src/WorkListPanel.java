import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.awt.*;

public class WorkListPanel extends JPanel {

	private JButton previousBtn, nextBtn;
	private JLabel pageLbl;
	private int problemIndex;
	private JPanel mainPanel;
	
	private JTextPane problemText;
	private JTextField solutionField;
	private JButton submitBtn;
	private JLabel errorLbl;
	private JLabel resultLbl;
	
	private SolutionParser parser;
	private final Color DARK_GREEN = new Color(0,150,0);
	private final Color ERROR_ORANGE = new Color(255,165,0);
	
	public WorkListPanel() {
		
		problemIndex = 0;
		int ebInt = (int) (Home.project.screenHeight / 40);
		parser = new SolutionParser(ParseType.WORK_PROBLEM);
		double d = parser.parseSolution("0");
		
		this.setLayout(new BorderLayout());
		
		previousBtn = new JButton("<");
		nextBtn = new JButton(">");
		int btnFontInt = (int) (Home.project.screenHeight / 50);
		Font switchBtnFont = new Font("Georgia", Font.BOLD, btnFontInt);
		previousBtn.setFont(switchBtnFont);
		nextBtn.setFont(switchBtnFont);
		int switchBtnLength = (int) (Home.project.screenHeight / 20);
		Dimension switchBtnDim = new Dimension(switchBtnLength, switchBtnLength);
		previousBtn.setPreferredSize(switchBtnDim);
		nextBtn.setPreferredSize(switchBtnDim);
		previousBtn.setForeground(Color.WHITE);
		nextBtn.setForeground(Color.WHITE);
		previousBtn.setBackground(Home.project.btnColor);
		nextBtn.setBackground(Home.project.btnColor);
		JPanel switchBtnPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		switchBtnPan.setBackground(Home.project.workBackgroundColor);
		switchBtnPan.add(previousBtn);
		switchBtnPan.add(nextBtn);
		SwitchListener sl = new SwitchListener();
		previousBtn.addActionListener(sl);
		nextBtn.addActionListener(sl);
		pageLbl = new JLabel("(0 out of 0)");
		pageLbl.setFont(Home.project.lblFont);
		pageLbl.setPreferredSize(new Dimension((int)(Home.project.screenWidth/10), switchBtnLength));
		pageLbl.setHorizontalAlignment(SwingConstants.CENTER);
		switchBtnPan.add(pageLbl);
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Home.project.workBackgroundColor);
		problemText = new JTextPane();
		problemText.setFont(Home.project.lblFont);
		problemText.setEditable(false);
		problemText.setBackground(Home.project.workBackgroundColor);
		problemText.setAlignmentX(SwingConstants.CENTER);
		problemText.setAlignmentY(SwingConstants.CENTER);
		problemText.setBorder(new EmptyBorder(2*switchBtnLength,0,0,0));
		
		//Center align text
		StyledDocument doc = problemText.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		solutionField = new JTextField();
		solutionField.setFont(Home.project.fieldFont);
		int fieldDimW = (int) (Home.project.screenWidth / 3);
		int fieldDimH = (int) (Home.project.screenWidth / 27);
		solutionField.setBorder(new EmptyBorder(0,20,0,0));
		Dimension fieldDim = new Dimension(fieldDimW, fieldDimH);
		solutionField.setPreferredSize(fieldDim);
		JPanel fieldPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		fieldPan.setBorder(new EmptyBorder((int)(Home.project.screenHeight/35), 0,0,0));
		fieldPan.setBackground(Home.project.workBackgroundColor);
		fieldPan.add(solutionField);
		
		submitBtn = new JButton("Submit");
		submitBtn.setForeground(Color.WHITE);
		submitBtn.setBackground(Home.project.btnColor);
		submitBtn.addActionListener(new SubmitListener());
		submitBtn.setFont(Home.project.fieldFont);
		int sBtnW = (int) (Home.project.screenWidth / 12);
		int sBtnH = (int) (Home.project.screenHeight / 20);
		submitBtn.setPreferredSize(new Dimension(sBtnW, sBtnH));
		JPanel btnPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnPan.setBackground(Home.project.workBackgroundColor);
		btnPan.add(submitBtn);
		errorLbl = new JLabel("");
		errorLbl.setFont(Home.project.lblFont);
		errorLbl.setForeground(ERROR_ORANGE);
		errorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		resultLbl = new JLabel("");
		resultLbl.setFont(Home.project.lblFont);
		resultLbl.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel btnHandlePan = new JPanel(new GridLayout(4,1));
		btnHandlePan.setBackground(Home.project.workBackgroundColor);
		btnHandlePan.add(btnPan);
		btnHandlePan.add(new JLabel(" "));
		btnHandlePan.add(errorLbl);
		btnHandlePan.add(resultLbl);
		
		JPanel centerMainPanel = new JPanel(new GridLayout(2,1));
		centerMainPanel.add(problemText);
		centerMainPanel.add(fieldPan);
		
		mainPanel.setBorder(new EmptyBorder(ebInt,ebInt,5*ebInt,ebInt));
		
		mainPanel.add(centerMainPanel, BorderLayout.CENTER);
		mainPanel.add(btnHandlePan, BorderLayout.SOUTH);

		this.add(mainPanel, BorderLayout.CENTER);
		this.add(switchBtnPan, BorderLayout.NORTH);
		
		
		
	}
	
	private class SwitchListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == previousBtn) {
				errorLbl.setText("");
				problemIndex--;
				if(problemIndex < 0) {
					if(Home.project.problemList.size() != 0)
						problemIndex = Home.project.problemList.size() - 1;
					else
						problemIndex = 0;
				}
				setPageLbl();
				setMainPanel();
			} else if(e.getSource() == nextBtn) {
				errorLbl.setText("");
				problemIndex++;
				if(problemIndex >= Home.project.problemList.size()) {
					problemIndex = 0;
				}
				setPageLbl();
				setMainPanel();	
			}
		}
	}
	
	public void setPageLbl() {
		String outOf = Integer.toString(Home.project.problemList.size());
		String on = "0";
		if(Home.project.problemList.size() != 0) {
			on = Integer.toString(problemIndex+1);
		} 
		String pageLblString = "(" + on + " out of " + outOf + ")";
		pageLbl.setText(pageLblString);
	}
	
	public void setMainPanel() {
		String problem = "";
		String fieldText = "";
		String resultText = "";
		if(Home.project.problemList.size() != 0) {
			problem = Home.project.problemList.get(problemIndex).getProblem();
			fieldText = Home.project.problemList.get(problemIndex).getInputString();
			resultText = Home.project.problemList.get(problemIndex).getResponseString();
			if(resultText.equals("Correct."))
				resultLbl.setForeground(DARK_GREEN);
			else if(resultText.equals("Incorrect."))
				resultLbl.setForeground(Color.RED);
		}
		problemText.setText(problem);
		solutionField.setText(fieldText);
		resultLbl.setText(resultText);
		
	}
	
	private class SubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String answerString = solutionField.getText();
			if(Home.project.problemList.size() == 0) {
				errorLbl.setText("Add Some Problems.");
			}
			
			else if(answerString.equals(""))
				errorLbl.setText("You gave no solution.");
			else if(!validInput(answerString)) {
				errorLbl.setText("Your solution must be numeric.");
				resultLbl.setText("");
			}
			else {
				errorLbl.setText("");
				double d = parser.parseSolution(answerString);
				double solution = Home.project.problemList.get(problemIndex).getSolution();
				DecimalFormat decimalFormat = Home.project.decimalFormat;
				d = Double.parseDouble(decimalFormat.format(d));
				solution = Double.parseDouble(decimalFormat.format(solution));
				boolean CORRECT = d == solution;
				if(CORRECT) {
					Home.project.problemList.get(problemIndex).setResponseString("Correct.");
					Home.project.problemList.get(problemIndex).setInputString(Double.toString(d));
					resultLbl.setText("Correct.");
					resultLbl.setForeground(DARK_GREEN);
				} else {
					Home.project.problemList.get(problemIndex).setResponseString("Incorrect.");
					Home.project.problemList.get(problemIndex).setInputString(Double.toString(d));
					resultLbl.setText("Incorrect.");
					resultLbl.setForeground(Color.RED);
				}
			}
		}
	}
	
	private boolean validInput(String numString) {
		String editedString = numString;
		editedString = editedString.replace("*","");
		editedString = editedString.replace("/","");
		editedString = editedString.replace("+","");
		editedString = editedString.replace("-","");
		editedString = editedString.replace("^","");
		editedString = editedString.replace("(","");
		editedString = editedString.replace(")","");
		editedString = editedString.replace(" ","");
		editedString = editedString.replace(".","");
		try {
			double n = Double.parseDouble(editedString);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public void updateIndexDelete(int row) {
		if(problemIndex >= row) {
			problemIndex--;
		} 
	}
	
	public void update() {
		if(problemIndex >= Home.project.problemList.size() && problemIndex != 0)
			problemIndex = Home.project.problemList.size() - 1;
		if(problemIndex < 0)
			problemIndex = 0;
		setPageLbl();
		setMainPanel();
		errorLbl.setText("");
	}
	
	public void setErrorLbl(String message) {
		errorLbl.setText(message);
	}
	
}
