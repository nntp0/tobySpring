package springbook.learningtest;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
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
		assertThat(testObjects, CoreMatchers.not(JUnitMatchers.hasItem(this)));
		testObjects.add(this);
		
		assertThat(contextObject == null || contextObject == this.context, CoreMatchers.is(true));
		contextObject = this.context;
	}
	
	@Test public void test2() {
		assertThat(testObjects, CoreMatchers.not(JUnitMatchers.hasItem(this)));
		testObjects.add(this);
		
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = this.context;
	}
	
	@Test public void test3() {
		assertThat(testObjects, CoreMatchers.not(JUnitMatchers.hasItem(this)));
		testObjects.add(this);
		
		assertThat(contextObject, JUnitMatchers.either(CoreMatchers.is(CoreMatchers.nullValue())).or(CoreMatchers.is(this.context)));
		contextObject = this.context;
	}
}
