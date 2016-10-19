import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import java.io.*;

public class NewListPanel extends JPanel {
	
	private JTable table;
	private DefaultTableModel model;
	private JButton addBtn;
	private JButton editBtn;
	private JLabel errorLbl;
	
	public NewListPanel() {
	
		this.setBackground(Home.project.backgroundColor);
		
		model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		table.setFont(Home.project.fieldFont);
		table.getTableHeader().setFont(Home.project.titleFont);
		table.setRowHeight(Home.project.tableRowHeight);
		model.addColumn("#");
		model.addColumn("Problem");
		model.addColumn("Solution");
		
		JScrollPane scroll = new JScrollPane(table);
		
		this.setLayout(new BorderLayout());
		this.add(scroll, BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnPanel.setBackground(Home.project.backgroundColor);
		
		addBtn = new JButton("Add Problem");
		editBtn = new JButton("Edit Problem");
		addBtn.setFont(Home.project.fieldFont);
		editBtn.setFont(Home.project.fieldFont);
		addBtn.setForeground(Color.WHITE);
		editBtn.setForeground(Color.WHITE);
		addBtn.setBackground(Home.project.btnColor);
		editBtn.setBackground(Home.project.btnColor);
		ButtonListener bl = new ButtonListener();
		addBtn.addActionListener(bl);
		editBtn.addActionListener(bl);
		btnPanel.add(addBtn); 
		btnPanel.add(editBtn);
		
		errorLbl = new JLabel("");
		errorLbl.setForeground(Color.RED);
		errorLbl.setFont(Home.project.lblFont);
		errorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel controlPanel = new JPanel(new GridLayout(2,1));
		controlPanel.setBackground(Home.project.backgroundColor);
		controlPanel.add(btnPanel);
		controlPanel.add(errorLbl);
		
		this.add(controlPanel, BorderLayout.SOUTH);
		
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addBtn) {
				errorLbl.setText("");
				AddProblemPanel addPan = new 
							AddProblemPanel(false,"", Double.MIN_VALUE, Integer.MIN_VALUE);
				int result = JOptionPane.showConfirmDialog
					(null, addPan, "Add a Problem", JOptionPane.INFORMATION_MESSAGE);
				
				if(result == JOptionPane.OK_OPTION) {
					String problem = addPan.getProblem();
					String solutionString = addPan.getSolutionString();
					InputCheck problemCheck = checkProblemInput(problem,solutionString);
					
					if(problemCheck == InputCheck.CHECK_VALID) {
						addProblem(problem, solutionString);
					} else if(problemCheck == InputCheck.PROBLEM_ERROR){
						errorLbl.setText("You must define the problem.");
					} else if(problemCheck == InputCheck.SOLUTION_ERROR) {
						errorLbl.setText("Your solution must be a number.");
					}
				}
				
			} else if(e.getSource() == editBtn) {
				int row = table.getSelectedRow();
				if(row >= 0) {
					errorLbl.setText("");
					Problem p = Home.project.problemList.get(row);
					String oldProblem = p.getProblem();
					double oldSolution = p.getSolution();
					AddProblemPanel editPan = new 
							AddProblemPanel(true,oldProblem,oldSolution, row);
					int result = JOptionPane.showConfirmDialog
					(null, editPan, "Edit Problem", JOptionPane.INFORMATION_MESSAGE);
					if(result == JOptionPane.OK_OPTION) {
						String problem = editPan.getProblem();
						String solutionString = editPan.getSolutionString();
						InputCheck problemCheck = checkProblemInput(problem,solutionString);
						if(problemCheck == InputCheck.PROBLEM_ERROR) {
							errorLbl.setText("Define the problem.");
						} else if(problemCheck == InputCheck.SOLUTION_ERROR) {
							errorLbl.setText("The solution must be a number.");
						} else if(problemCheck == InputCheck.CHECK_VALID){
							editRow(row, problem, solutionString);
						}
					}
					
				} else if(Home.project.problemList.size() == 0) {
					errorLbl.setText("Add a Problem.");
					
				} else {
					errorLbl.setText("Select a Problem to Edit.");
				}
				
			}
		}
	}
	
	private InputCheck checkProblemInput(String problem, String solution) {
		if(problem.equals("")) {
			return InputCheck.PROBLEM_ERROR;
		} else if(!isNumeric(solution)) {
			return InputCheck.SOLUTION_ERROR;
		} else {
			return InputCheck.CHECK_VALID;
		}
	}
	
	private boolean isNumeric(String numString) {		
		try {
			double num = Double.parseDouble(numString);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private void addProblem(String problem, String solutionString) {
		double solution = Double.parseDouble(solutionString);
		Problem p = new Problem(problem, solution);
		Home.project.problemList.add(p);
		int problemNum = Home.project.problemList.size();
		String problemNumS = Integer.toString(problemNum);
		Object[] row = new Object[] {problemNumS, problem, solutionString};
		model.addRow(row);	
		updateData();
	}
	
	//Inputs row number (array), new problem, solution string
	private void editRow(int row, String problem, String solutionString) {
		double solution = Double.parseDouble(solutionString);
		Problem p = new Problem(problem, solution);
		Home.project.problemList.set(row,  p);
		int problemNum = row + 1;
		String problemNumS = Integer.toString(problemNum);
		Object[] rowData = new Object[] {problemNumS, problem, solutionString};
		model.removeRow(row);
		model.insertRow(row, rowData);
		updateData();
	}
	
	public void deleteRow(int row) {
		Home.project.problemList.remove(row);	
		Home.project.workPanel.updateIndexDelete(row);
		resetTable();
		updateData();
	}
	
	public void resetTable() {
		while(table.getRowCount() > 0) {
			model.removeRow(0);
		}
		for(int i = 0; i < Home.project.problemList.size(); i++) {
			String index = Integer.toString(i + 1);
			String problem = Home.project.problemList.get(i).getProblem();
			String solution = Double.toString(Home.project.problemList.get(i).getSolution());					
			model.addRow(new Object[] {index,problem, solution});
		}
	}
	
	private void updateData() {
		Home.project.UPDATE_APPLICATION();
	}
	
}
