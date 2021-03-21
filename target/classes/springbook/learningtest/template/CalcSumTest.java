package springbook.learningtest.template;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class CalcSumTest {
	Calculator calculator;
	String filepath;
	
	@Before
	public void setUp() {
		filepath = getClass().getResource("../../../resources/numbers.txt").getPath();

		calculator = new Calculator();
		calculator.setCalcContext(new CalcContext());
	}
	
	@Test
	public void sumOfNumbers() throws IOException {
		int sum = calculator.calcIntSum(filepath);
		
		assertEquals(sum, 10);
	}
	@Test
	public void multipleOfNumbers() throws IOException {
		int sum = calculator.calcIntMultiple(filepath);
		
		assertEquals(sum, 24);
	}
	@Test
	public void sumOfStrings() throws IOException {
		String sum = calculator.calcStringSum(filepath);
		
		assertEquals(sum, "1234");
	}
}
