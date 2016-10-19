import java.util.*;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.io.*;

public class CloseListener implements WindowListener {

	public void windowClosing(WindowEvent e) {
        
		int result = JOptionPane.showOptionDialog
				(null, "Save this list?", "All progress will be lost.",  
				JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE, 
				null, new Object[]{"Yes","No","Cancel"},null);
		if(result == JOptionPane.OK_OPTION) {
			Save save = new Save();
			System.exit(0);
		} else if(result == JOptionPane.NO_OPTION) {
			System.exit(0);
		} else if(result == JOptionPane.CANCEL_OPTION) {
			//Nothing happens
		}
		
    }
	public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
	
	
	
	
	
}
