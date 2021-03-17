package springbook.learningtest.template;

import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.hamcrest.CoreMatchers;
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
		
		assertThat(sum, CoreMatchers.is(10));
	}
	@Test
	public void multipleOfNumbers() throws IOException {
		int sum = calculator.calcIntMultiple(filepath);
		
		assertThat(sum, CoreMatchers.is(24));
	}
	@Test
	public void sumOfStrings() throws IOException {
		String sum = calculator.calcStringSum(filepath);
		
		assertThat(sum, CoreMatchers.is("1234"));
	}
}
