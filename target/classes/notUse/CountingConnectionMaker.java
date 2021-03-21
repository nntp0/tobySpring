package notUse;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
	int counter = 0;
	private ConnectionMaker realConnectionMaker;
	
	public void setCountingConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		this.counter++;
    	return realConnectionMaker.getConnection();
	}
	
	public int getCount() {
		return this.counter;
	}
}
