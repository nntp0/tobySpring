package notUse;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import springbook.user.dao.UserDaoJdbc;

@Configuration
public class CountingDaoFactory {
	@Bean
	public UserDaoJdbc userDao() {
		UserDaoJdbc dao = new UserDaoJdbc();
		dao.setDataSource(dataSource());
		
		return dao;		
	}
	@Bean
	public DataSource dataSource() {
		CountingDataSource cds = new CountingDataSource();
		cds.setDataSource(realDataSource());
		
		return cds;
	}
	@Bean
	public DataSource realDataSource() {		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("hr");
		dataSource.setPassword("hr");
		
		return dataSource;
	}
}