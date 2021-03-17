package springbook.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;

@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../../resources/test-applicationContext.xml")
public class UserDaoTest {
	
	private User user1;
	private User user2;
	private User user3;
	
	@Autowired
	private UserDao dao;
	@Before
	public void setUp() {
		
		user1 = new User("nntp", "±Ë«ˆ∫Û", "1234");
		user2 = new User("jinsung", "¿Ã¡¯º∫", "4567");
		user3 = new User("fedss2", "¿Ã∞«", "1357");
		
	}
	@Test
	public void addAndGet() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		
		
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		user3 = dao.get(user1.getId());
		assertThat(user1.getName(), is(user3.getName()));
		assertThat(user1.getPassword(), is(user3.getPassword()));
		
		user3 = dao.get(user2.getId());
		assertThat(user2.getName(), is(user3.getName()));
		assertThat(user2.getPassword(), is(user3.getPassword()));
	}
	
	@Test
	public void getCount() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
	}
	
	@Test
	public void getAllUsers() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
		
		List<User> userList = dao.getAll();
		assertThat(userList.size(), is(3));
		checkSameUser(userList.get(0), user3);
		checkSameUser(userList.get(1), user2);
		checkSameUser(userList.get(2), user1);
	}
	
	@Test
	public void getAllUsers_Zero() throws SQLException {		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		List<User> userList = dao.getAll();
		assertThat(userList.size(), is(0));
	}
	
	private void checkSameUser(User user1, User user2) {
		assertThat(user1.getId(), is(user2.getId()));
		assertThat(user1.getName(), is(user2.getName()));
		assertThat(user1.getPassword(), is(user2.getPassword()));
	}
	
//	@After
//	public void tearDown() {
//		((ConfigurableApplicationContext)context).close();
//	}
}
