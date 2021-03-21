package springbook.learningtest.factorybean;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class FactoryBeanTest {
	@Autowired ApplicationContext context;
	@Test
	public void getMessageFromFactoryBean() {
		Object message = context.getBean("message");
		assertEquals(message.getClass(), Message.class);
		assertEquals( ((Message)message).getText(), "Factory Bean" );		
	}
	@Test
	public void getFactoryBean() {
		Object message = context.getBean("&message");
		assertEquals(message.getClass(), MessageFactoryBean.class);
	}
}
