import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class AddProblemPanel extends JPanel {

	private JTextField problemField;
	private JTextField solutionField;
	private JButton deleteBtn;
	
	private int rowNum;
	
	public AddProblemPanel(boolean edit, String problem, double solution, int row) {
		
		rowNum = Integer.MIN_VALUE;
		
		problemField = new JTextField();
		solutionField = new JTextField();
		problemField.setPreferredSize(Home.project.fieldDim);
		solutionField.setPreferredSize(Home.project.fieldDim);
		problemField.setFont(Home.project.fieldFont);
		solutionField.setFont(Home.project.fieldFont);
		
		JLabel probLbl = new JLabel("Problem");
		probLbl.setFont(Home.project.lblFont);
		JLabel solutionLbl = new JLabel("Solution");
		solutionLbl.setFont(Home.project.lblFont);
		
		JPanel problemFieldPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel solutionFieldPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		problemFieldPan.add(probLbl);
		problemFieldPan.add(problemField);
		solutionFieldPan.add(solutionLbl);
		solutionFieldPan.add(solutionField);
		
		JPanel fieldPan = new JPanel(new GridLayout(2,1));
		fieldPan.add(problemFieldPan);
		fieldPan.add(solutionFieldPan);
		
		this.setLayout(new BorderLayout());
		this.add(fieldPan, BorderLayout.CENTER);
		
		deleteBtn = new JButton("Delete Problem");
		
		if(edit) {
			rowNum = row;
			
			problemField.setText(problem);
			String solutionString = Double.toString(solution);
			solutionField.setText(solutionString);
			
			deleteBtn.addActionListener(new DeleteListener());
			
			JPanel btnPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
			btnPan.add(deleteBtn);
			this.add(btnPan, BorderLayout.SOUTH);
		} 		
		
	}
	
	public String getProblem() {
		return problemField.getText();
	}
	
	public String getSolutionString() {
		return solutionField.getText();
	}
	
	private class DeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure? "
												+ "This will be permanent.");
			if(confirmation == JOptionPane.OK_OPTION) {
				Home.project.newListPanel.deleteRow(rowNum);
				//Close the edit j-option pane
				 Window editPane = SwingUtilities.getWindowAncestor(deleteBtn);
				 if (editPane != null) {
					 editPane.setVisible(false);
				 }
			}
		}
	}
}
