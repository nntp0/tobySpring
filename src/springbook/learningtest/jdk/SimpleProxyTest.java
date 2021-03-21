package springbook.learningtest.jdk;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

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
	
	@Test
	public void proxyFactoryBean() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		pfBean.addAdvice(new UppercaseAdvice());
		
		Hello proxiedHello = (Hello) pfBean.getObject();
		
		assertEquals(proxiedHello.sayHello("nntp"), "HELLO NNTP");
		assertEquals(proxiedHello.sayHi("nntp"), "HI NNTP");
		assertEquals(proxiedHello.sayThankYou("nntp"), "THANK YOU NNTP");
		assertEquals(proxiedHello.talkGoodBye("nntp"), "GOOD BYE NNTP");
	}
	
	@Test
	public void pointcutAdvisor() {
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("sayH*");
		
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
		Hello proxiedHello = (Hello) pfBean.getObject();
		
		assertEquals(proxiedHello.sayHello("nntp"), "HELLO NNTP");
		assertEquals(proxiedHello.sayHi("nntp"), "HI NNTP");
		assertEquals(proxiedHello.sayThankYou("nntp"), "Thank you nntp");
		assertEquals(proxiedHello.talkGoodBye("nntp"), "Good Bye nntp");
	}
	
	static class UppercaseAdvice implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			String ret = (String)invocation.proceed();
			return ret.toUpperCase();
		}
		
	}
}
