package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {
	@Bean
	public UserDao userDao() {
		UserDao dao = new UserDao();
		
		dao.setConnectionMaker(createConnection());
		
		return dao;		
	}
	@Bean
	public ConnectionMaker createConnection() {
		return new CountingConnectionMaker(realConnectionMaker());
	}
	@Bean
	public ConnectionMaker realConnectionMaker() {		
		return new DConnectionMaker();
	}
}