import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class OpenPackage implements Serializable {

	private ArrayList<Problem> problemList;
	private DecimalFormat decimalFormat;
	private int decimalPlaces;
	
	public OpenPackage() {
		problemList = Home.project.problemList;
		decimalFormat = Home.project.decimalFormat;
		decimalPlaces = Home.project.currentDecimalPlaces;
	}
	
	public ArrayList<Problem> getProblemList() {
		return problemList;
	}
	public DecimalFormat getDecimalFormat() {
		return decimalFormat;
	}
	public int getDecimalPlacesInt() {
		return decimalPlaces;
	}
}
