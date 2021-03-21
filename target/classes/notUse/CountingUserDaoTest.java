//package notUse;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//import java.sql.SQLException;
//
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ConfigurableApplicationContext;
////import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//
//import springbook.user.dao.UserDaoJdbc;
//import springbook.user.domain.User;
//
//public class CountingUserDaoTest {
//	@Test
//	public void countingUserDaoTest() throws SQLException {
//		ApplicationContext context = new GenericXmlApplicationContext("resources/applicationCountingContext.xml");
//		//ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class); 
//		
//		UserDaoJdbc dao = null;
//		User user = null;
//		
//		CountingDataSource cds = context.getBean("dataSource", CountingDataSource.class);
//		assertThat(cds.getCounter(), is(0));
//		
//		dao = context.getBean("userDao", UserDaoJdbc.class);
//		dao.deleteAll();
//		assertThat(dao.getCount(), is(0));
//		assertThat(cds.getCounter(), is(2));
//		
//		for (int i = 0; i < 10; i++) {			
//			user = new User("nntp"+i, "±èÇöºó"+i, "1234"+i);
//			
//			dao = context.getBean("userDao", UserDaoJdbc.class);
//			dao.add(user);
//			
//			assertThat(dao.getCount(), is(i+1));
//			assertThat(cds.getCounter(), is(2*(i+1)+2));
//		}
//		
//		((ConfigurableApplicationContext)context).close();
//	}
//}
