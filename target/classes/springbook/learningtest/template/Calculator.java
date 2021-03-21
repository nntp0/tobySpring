package springbook.learningtest.template;

import java.io.IOException;

public class Calculator {
	private CalcContext calcContext;
	
	public void setCalcContext(CalcContext calcContext) {
		this.calcContext = calcContext;
	}
	
	public int calcIntSum(String filepath) throws IOException {
		
		return this.calcContext.lineReadTemplate(new LineCallback<Integer>() {
			public Integer doSomethingWithLine(String line, Integer value) {
				return value + Integer.valueOf(line);
			}
		}, filepath, 0);
	}
	public int calcIntMultiple(String filepath) throws IOException {
		
		return this.calcContext.lineReadTemplate(new LineCallback<Integer>() {
			public Integer doSomethingWithLine(String line, Integer value) {
				return value * Integer.valueOf(line);
			}
		}, filepath, 1);
	}
	public String calcStringSum(String filepath) throws IOException {
		
		return this.calcContext.lineReadTemplate(new LineCallback<String>() {
			public String doSomethingWithLine(String line, String value) {
				return value + line;
			}
		}, filepath, "");
	}
}
