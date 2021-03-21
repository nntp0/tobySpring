package springbook.learningtest.jdk;

public class HelloUppercase implements Hello {

	private Hello hello;
	public void setHello (Hello hello) {
		this.hello = hello;
	}
	
	@Override
	public String sayHello(String name) {
		return hello.sayHello(name).toUpperCase();
	}

	@Override
	public String sayHi(String name) {
		return hello.sayHi(name).toUpperCase();
	}

	@Override
	public String sayThankYou(String name) {
		return hello.sayThankYou(name).toUpperCase();
	}

	@Override
	public int methodCount() {
		return 3;
	}
	
	@Override
	public String talkGoodBye(String name) {
		return hello.talkGoodBye(name).toUpperCase();
	}

}
