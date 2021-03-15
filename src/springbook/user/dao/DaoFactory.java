package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	@Bean
	public UserDao userDao() {
		UserDao dao = new UserDao();
		
		dao.setConnectionMaker(createConnection());
		
		return dao;		
	}
	@Bean
	public ConnectionMaker createConnection() {
		ConnectionMaker connectionMaker = new DConnectionMaker(); 
		
		return connectionMaker;
	}
}