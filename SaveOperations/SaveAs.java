import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;

public class SaveAs {
	
	private Home home;
	
	public SaveAs() {
	
		home = new Home(home.project);
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".dat File", "dat");
		chooser.setFileFilter(fileFilter);
		int returnValue = chooser.showSaveDialog(null);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			try {
				File savedFile = new File(chooser.getSelectedFile().getAbsolutePath() + ".dat");
				FileOutputStream fileOut = new FileOutputStream(savedFile);
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
				OpenPackage openPackage = new OpenPackage();
				objOut.writeObject(openPackage);
				home.project.savedFile = savedFile;
				home.project.isSaved = true;
				int titleEnd = savedFile.getName().length() - 4;
				home.project.mainFrame.setTitle("Problem List  -  " 
							+ savedFile.getName().substring(0, titleEnd));
			} catch(IOException e) {
				JOptionPane.showMessageDialog
				(null, "Unable to save project.", "Error.", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}
