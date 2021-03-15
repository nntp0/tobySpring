package springbook.test;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;

public class UserDaoTest {
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("resources/applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		User user = new User();
		user.setId("nntp");
		user.setName("������");
		user.setPassword("1234");
		
		dao.add(user);
		System.out.println("���� �Ϸ�");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		System.out.println("��ȸ �Ϸ�");
		
		((ConfigurableApplicationContext)context).close();
	}
}
