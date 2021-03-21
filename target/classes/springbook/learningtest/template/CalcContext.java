package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CalcContext {
	
	public <T> T lineReadTemplate(LineCallback<T> callback, String filepath, T initVal) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));			

			T result = initVal;
			
			String line=null;
			while((line = br.readLine()) != null) {
				result = callback.doSomethingWithLine(line, result);
			}
			
			return result;	
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if(br != null) {
				try { br.close(); }
				catch (Exception e) { System.out.println(e.getMessage()); }
			}
		}
	}
}
