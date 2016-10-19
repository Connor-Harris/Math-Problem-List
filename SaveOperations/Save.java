import java.util.*;

import javax.swing.JOptionPane;

import java.io.*;

public class Save {
	
	public Save() {
		
		if(Home.project.isSaved) {
		
			try {
				File savedFile = Home.project.savedFile;
				FileOutputStream fileOut = new FileOutputStream(savedFile);
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
				OpenPackage openPackage = new OpenPackage();
				objOut.writeObject(openPackage);
				objOut.close();
			} catch(IOException e) {
				JOptionPane.showMessageDialog(null,
						"Unable to save project.",
						"Error.",
					    JOptionPane.ERROR_MESSAGE);
			}
		} else {
			SaveAs saveAs = new SaveAs();
		}
		
		
	}
	
}
