import java.io.*;

public class Problem implements Serializable {

	private String problem;
	private double solution;
	private String responseString;
	private String inputString;
	
	public Problem(String problem, double solution) {
		this.problem = problem;
		this.solution = solution;
		this.responseString = "";
		this.inputString = "";
	}
	
	public String getProblem() { return this.problem; }
	public double getSolution() { return this.solution; }
	public void setProblem(String problem) { this.problem = problem; }
	public void setSolution(double solution) { this.solution = solution; }
	
	public void setResponseString(String response) { this.responseString = response; }
	public void setInputString(String input) { this.inputString = input; }
	public String getResponseString() { return this.responseString; }
	public String getInputString() { return this.inputString; }
	
	
}

