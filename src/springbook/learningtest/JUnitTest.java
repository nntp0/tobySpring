package springbook.learningtest;

import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.matchers.JUnitMatchers;
import org.junit.Test;

public class JUnitTest {
	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
	
	@Test public void test1() {
		assertThat(testObjects, CoreMatchers.not(JUnitMatchers.hasItem(this)));
		testObjects.add(this);
	}
	
	@Test public void test2() {
		assertThat(testObjects, CoreMatchers.not(JUnitMatchers.hasItem(this)));
		testObjects.add(this);
	}
	
	@Test public void test3() {
		assertThat(testObjects, CoreMatchers.not(JUnitMatchers.hasItem(this)));
		testObjects.add(this);
	}
}
