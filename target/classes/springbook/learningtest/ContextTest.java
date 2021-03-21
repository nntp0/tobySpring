package springbook.learningtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.either;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../../resources/testContext-applicationContext.xml")
public class ContextTest {
	@Autowired ApplicationContext context;
	
	static Set<ContextTest> testObjects = new HashSet<ContextTest>();
	static ApplicationContext contextObject = null;
	
	@Test public void test1() {
		assertEquals(testObjects, CoreMatchers.not(hasItem(this)));
		testObjects.add(this);
		
		assertEquals(contextObject == null || contextObject == this.context, CoreMatchers.is(true));
		contextObject = this.context;
	}
	
	@Test public void test2() {
		assertEquals(testObjects, CoreMatchers.not(hasItem(this)));
		testObjects.add(this);
		
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}
	
	@Test public void test3() {
		assertEquals(testObjects, CoreMatchers.not(hasItem(this)));
		testObjects.add(this);
		
		assertEquals(contextObject, either(CoreMatchers.is(CoreMatchers.nullValue())).or(CoreMatchers.is(this.context)));
		contextObject = this.context;
	}
}
