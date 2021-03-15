package springbook.test;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springbook.user.dao.CountingConnectionMaker;
import springbook.user.dao.CountingDaoFactory;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

public class CountingUserDaoTest {
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
		UserDao dao = null;
		User user = null;
		
		for (int i = 0; i < 10; i++) {
			dao = context.getBean("userDao", UserDao.class);
			
			user = new User();
			user.setId("nntp"+i);
			user.setName("김현빈"+i);
			user.setPassword("1234"+i);
			
			dao.add(user);
			
			System.out.println("생성 완료");
		}
		
		CountingConnectionMaker ccm = context.getBean("createConnection", CountingConnectionMaker.class);
		System.out.println(ccm.getCount());
//		User user2 = dao.get(user.getId());
//		System.out.println(user2.getId());
//		System.out.println(user2.getName());
//		System.out.println(user2.getPassword());
//		System.out.println("조회 완료");
		
		((ConfigurableApplicationContext)context).close();
	}
}
