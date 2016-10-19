import java.util.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;

public class SolutionParser {

	private ParseType parseType;
	
	public SolutionParser(ParseType parseType) {
		this.parseType = parseType;
	}
	
	public double parseSolution(String numString) {
		
		ScriptEngineManager engineManager = new ScriptEngineManager();
		ScriptEngine engine = engineManager.getEngineByName("JavaScript");
		
		//CORRECT SPACING...
		numString = numString.replace(" ", "");
		int j = 0; 
		while( j < numString.length() ) {
			if( !Character.isDigit(numString.charAt(j)) && numString.charAt(j) != '.') {
				if(j!=0 && j!=numString.length()-1 && numString.charAt(j-1) == ' ') {
					numString = new StringBuilder(numString).insert(j+1, " ").toString();
					j += 2;
				} else {
					numString = new StringBuilder(numString).insert(j, " ").toString();
					numString = new StringBuilder(numString).insert(j+2, " ").toString();
					j += 3;
				}
				
			} else 
				j++;
		}
		String[] eArray = numString.split(" ");
		ArrayList<String> e = fillArrayList(eArray);
		String expression = "";
		
		int i = 0;
		while(i < e.size()) {
			
			String term = e.get(i);
			
			if(term.equals("(")) {
				int openParenIndex = i;
				String parens = "";
				i++;
				term = e.get(i);
				int moreOpen = 0;
				while(!term.equals(")") && moreOpen>0) {						
					parens = parens + term;
					i++; 
					if(i >= e.size()) {
						if(parseType == ParseType.WORK_PROBLEM)
							Home.project.workPanel.
							setErrorLbl("Your Solution has an Unclosed Set of Parenthesis.");
						else {
							//Handle error for adding problems
						}
						return Double.MIN_VALUE;
					}
					term = e.get(i);
					if(term.equals("("))
						moreOpen++;
					else if(term.equals(")"))
						moreOpen--;
				}
				int closeParenIndex = i;
				String dString;
				if(parens.equals(""))
					dString = "";
				else {
					System.out.println(parens);
					double d = parseSolution(parens);
					dString = Double.toString(d);
				}
				if(openParenIndex > 0) {
					String beforeChar = e.get(openParenIndex-1);
					if(isNumeric(beforeChar)) {
						expression = expression + "*";
						e.add(openParenIndex-1, "*");
						openParenIndex++;
						closeParenIndex++;
					}
				}
				if(closeParenIndex < e.size() - 1) {
					String afterChar = e.get(closeParenIndex + 1);
					if(isNumeric(afterChar)) {
						dString = dString + "*";
						e.add(closeParenIndex+1, "*");
						closeParenIndex++;
					}
				}
				expression = expression + dString;
				int parenSlots = closeParenIndex - openParenIndex;
				e.set(openParenIndex, dString);
				for(int rIndex = 0; rIndex < parenSlots; rIndex++)
					e.remove(openParenIndex + 1);
				i = openParenIndex;
				
			} else if(term.equals("^")) {
				 
				if(i == 0 || i == e.size()-1) {
					if(parseType == ParseType.WORK_PROBLEM)
						Home.project.workPanel.setErrorLbl("Unable to parse solution.");
					else {
						//Handle error for adding problems
					}
					return Double.MIN_VALUE;
				}
			
				String base = e.get(i-1);
				String exp = e.get(i + 1);
				int expStart = i-1;
				int expEnd = i+1;
				if(exp.equals("(")) {
					String parens = "";
					int pIndex = i+2;
					String pTerm = e.get(pIndex);
					while(!pTerm.equals(")")) {
						parens = parens + pTerm;
						pIndex++;
						if(pIndex >= e.size()) {
							if(parseType == ParseType.WORK_PROBLEM) {
								Home.project.workPanel.setErrorLbl
								("You have an unclosed set of parenthesis.");
								return Double.MIN_VALUE;
							}
						}
						pTerm = e.get(pIndex);
					}
					if(pIndex+1 < e.size()) {
						String charAfterParen = e.get(pIndex + 1);
						if(isNumeric(charAfterParen)) {
							e.add(pIndex+1, "*");
						}
					}
					double d = 0;
					System.out.println("p: " + parens);
					if(!parens.equals(""))
						d = parseSolution(parens);
					exp = Double.toString(d);
					expEnd = pIndex;
				}
				
				String powerString = "Math.pow(" + base + "," + exp + ")";
				System.out.println(powerString);
				e.set(i-1, powerString);
				int expLength = expEnd - expStart;
				for(int rIndex = 0; rIndex < expLength; rIndex++)
					e.remove(i);
				i = i - 1;
				
				//Remove base from expression
				int baseLength = base.length();
				int expSubString = expression.length() - baseLength; //(expression sub start)
				expression = expression.substring(0,expSubString);
				//Add power string to expression
				expression = expression + powerString;				
				
			} else {
				if(!term.equals(")"))
					expression = expression + term;
			}
			
			i++;
			
		}
		
		Object solution;
		try {
			solution = engine.eval(expression);
		} catch (ScriptException e1) {
			if(parseType == ParseType.WORK_PROBLEM) {
				Home.project.workPanel.setErrorLbl("Unable to parse solution. [SCRIPT]");
			} else {
				//Handle error when adding problems
			}
			return Double.MIN_VALUE;
		}
		String solutionString = solution.toString();
		return Double.parseDouble(solutionString);
	}
	
	
	
	
	private boolean isNumeric(String numString) {
		try {
			double n = Double.parseDouble(numString);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private ArrayList<String> fillArrayList(String[] array) {
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < array.length; i++) 
			list.add(array[i]);
		return list;
	}
	
	
	
}

	
