package springbook.learningtest.jdk;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Proxy;

import org.junit.Test;

public class SimpleProxyTest {
	@Test
	public void helloChecker() {
		Hello helloTarget = new HelloTarget();
		
		assertEquals(helloTarget.sayHello("nntp"), "Hello nntp");
		assertEquals(helloTarget.sayHi("nntp"), "Hi nntp");
		assertEquals(helloTarget.sayThankYou("nntp"), "Thank you nntp");
	}
	
	@Test
	public void proxyHelloChecker() {
		Hello helloTarget = new HelloTarget();
		HelloUppercase helloProxy = new HelloUppercase();
		helloProxy.setHello(helloTarget);
		
		assertEquals(helloProxy.sayHello("nntp"), "HELLO NNTP");
		assertEquals(helloProxy.sayHi("nntp"), "HI NNTP");
		assertEquals(helloProxy.sayThankYou("nntp"), "THANK YOU NNTP");
		assertEquals(helloProxy.methodCount(), 3);
		assertEquals(helloProxy.talkGoodBye("nntp"), "GOOD BYE NNTP");
	}
	
	@Test
	public void dynamicProxyHelloChecker() {
		Hello helloTarget = new HelloTarget();
		UppercaseHandler helloHandler = new UppercaseHandler();
		helloHandler.setTarget(helloTarget);
		
		Hello helloProxy = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {Hello.class}, helloHandler);
		
		assertEquals(helloProxy.sayHello("nntp"), "HELLO NNTP");
		assertEquals(helloProxy.sayHi("nntp"), "HI NNTP");
		assertEquals(helloProxy.sayThankYou("nntp"), "THANK YOU NNTP");
		assertEquals(helloProxy.methodCount(), 3);
		assertEquals(helloTarget.talkGoodBye("nntp"), "Good Bye nntp");
	}
}
