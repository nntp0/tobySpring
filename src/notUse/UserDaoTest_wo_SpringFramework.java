//package notUse;
//
//import java.sql.SQLException;
//
//import javax.sql.DataSource;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertThat;
//import static org.hamcrest.CoreMatchers.is;
//
////import org.springframework.context.annotation.AnnotationConfigApplicationContext;
////import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.datasource.SingleConnectionDataSource;
//
////import springbook.user.dao.DaoFactory;
//import springbook.user.dao.UserDaoJdbc;
//import springbook.user.domain.User;
//
//public class UserDaoTest_wo_SpringFramework {
//	
//	private User user1;
//	private User user2;
//	private User user3;
//	
//	private UserDaoJdbc dao;
//	@Before
//	public void setUp() {
//		//context = new GenericXmlApplicationContext("resources/applicationContext.xml");
//		//ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class); 
//		dao = new UserDaoJdbc();
//		DataSource dataSource = new SingleConnectionDataSource("jdbc:oracle:thin:@localhost:1521:xe", "test", "test", true);
//		dao.setDataSource(dataSource);
//		
//		user1 = new User("nntp", "±Ë«ˆ∫Û", "1234");
//		user2 = new User("2jinsung", "¿Ã¡¯º∫", "4567");
//		user3 = new User("fedss2", "¿Ã∞«", "1357");
////		
//	}
//	@Test
//	public void addAndGet() throws SQLException {
//		dao.deleteAll();
//		assertThat(dao.getCount(), is(0));
//		
//		
//		
//		dao.add(user1);
//		dao.add(user2);
//		assertThat(dao.getCount(), is(2));
//		
//		user3 = dao.get(user1.getId());
//		assertThat(user1.getName(), is(user3.getName()));
//		assertThat(user1.getPassword(), is(user3.getPassword()));
//		
//		user3 = dao.get(user2.getId());
//		assertThat(user2.getName(), is(user3.getName()));
//		assertThat(user2.getPassword(), is(user3.getPassword()));
//	}
//	
//	@Test
//	public void getCount() throws SQLException {
//		dao.deleteAll();
//		assertThat(dao.getCount(), is(0));
//		
//		dao.add(user1);
//		assertThat(dao.getCount(), is(1));
//		
//		dao.add(user2);
//		assertThat(dao.getCount(), is(2));
//		
//		dao.add(user3);
//		assertThat(dao.getCount(), is(3));
//	}
//	
//	@Test(expected=EmptyResultDataAccessException.class)
//	public void getUserFailure() throws SQLException {		
//		dao.deleteAll();
//		assertThat(dao.getCount(), is(0));
//		
//		dao.get("unknown_id");
//	}
//	
////	@After
////	public void tearDown() {
////		((ConfigurableApplicationContext)context).close();
////	}
//}
