package springbook.learningtest;

import static org.junit.Assert.assertNotEquals;
import static org.hamcrest.CoreMatchers.hasItem;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class JUnitTest {
	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
	
	@Test public void test1() {
		assertNotEquals(testObjects, hasItem(this));
		testObjects.add(this);
	}
	
	@Test public void test2() {
		assertNotEquals(testObjects, hasItem(this));
		testObjects.add(this);
	}
	
	@Test public void test3() {
		assertNotEquals(testObjects, hasItem(this));
		testObjects.add(this);
	}
}
