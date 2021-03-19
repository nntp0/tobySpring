package springbook.learningtest;

import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import springbook.user.dao.UserDaoJdbc;
import springbook.user.domain.User;

@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../../resources/testDI-applicationContext.xml")
public class DITest {	
	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserDaoJdbc dao;
	@Autowired
	private User user;
	@Before
	public void setUp() {
		//context = new GenericXmlApplicationContext("resources/applicationContext.xml");
		//ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class); 
		//dao = context.getBean("userDao", UserDao.class);
	}
	@Test
	public void isSingletonObject() {
		UserDaoJdbc dao1 = context.getBean("userDao", UserDaoJdbc.class);
		UserDaoJdbc dao2 = context.getBean("userDao", UserDaoJdbc.class);
		
		assertThat(dao1, CoreMatchers.is(CoreMatchers.sameInstance(dao2)));
	}
	@Test
	public void isSameAutowiredAndGetBean() throws SQLException {
		
		UserDaoJdbc dao2 = context.getBean("userDao", UserDaoJdbc.class);
		
		assertThat(dao, CoreMatchers.is(CoreMatchers.sameInstance(dao2)));
	}
	@Test
	public void isStringInjected() {
		assertThat(user.getId(), CoreMatchers.is("qweshjkd"));
		assertThat(user.getName(), CoreMatchers.is("저기에요"));
		assertThat(user.getPassword(), CoreMatchers.is("2255"));
	}
}