import java.io.*;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Open {

	private ArrayList<Problem> problemList;
	public Open() {
		
		//JFileChooser
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".dat File","dat");
		chooser.setFileFilter(fileFilter);
		int returnValue = chooser.showOpenDialog(null);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			try {
				File file = chooser.getSelectedFile();
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream objIn = new ObjectInputStream(fileIn);
				OpenPackage openPackage = (OpenPackage) objIn.readObject();
				Home.project.savedFile = file;
				Home.project.isSaved = true;
				Home.project.openProject(openPackage);
			} catch(IOException ioE) {
				JOptionPane.showMessageDialog(null,
					"Unable to open file.",
					"Error.",
				    JOptionPane.ERROR_MESSAGE);
			} catch(ClassNotFoundException cE) {
				JOptionPane.showMessageDialog(null,
					"ClassNotFoundException: " + cE.getMessage(),
					"Error.",
				    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public ArrayList<Problem> getProblemList() {
		if(problemList != null) 
			return problemList;
		else 
			return null;
	}
	
	
}
