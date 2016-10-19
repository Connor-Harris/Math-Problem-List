import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class PlaceValueEditor {

	public DecimalFormat getDecimalFormat(int places) {
		
		String formatString = "###.";
		for(int i = 0; i < places; i++) {
			formatString = formatString + "#";
		}
		DecimalFormat newFormat = new DecimalFormat(formatString);
		return newFormat;

	}
	
}
