package springbook.learningtest.jdk;

public class HelloTarget implements Hello {

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}

	@Override
	public String sayHi(String name) {
		return "Hi " + name;
	}

	@Override
	public String sayThankYou(String name) {
		return "Thank you " + name;
	}
	
	public int methodCount() {
		return 3;
	}

	@Override
	public String talkGoodBye(String name) {
		return "Good Bye " + name;
	}

}
