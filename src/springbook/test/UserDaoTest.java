package springbook.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import springbook.user.dao.UserDaoJdbc;
import springbook.user.domain.User;
import springbook.user.domain.Level;

@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../../resources/test-applicationContext.xml")
public class UserDaoTest {
	
	private User user1;
	private User user2;
	private User user3;
	
	@Autowired
	private UserDaoJdbc dao;
	@Before
	public void setUp() {
		
		user1 = new User("nntp", "김현빈", "1234", Level.BASIC, 1, 0, "nntp@gamil");
		user2 = new User("jinsung", "이진성", "4567", Level.SILVER, 55, 10, "jinsung@naver");
		user3 = new User("fedss2", "이건", "1357", Level.GOLD, 100, 40, "fedss2@daum");
		
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
	
	@Test(expected=DuplicateKeyException.class)
	public void duplicateKey() throws SQLException {		
		dao.deleteAll();
		
		dao.add(user1);
		dao.add(user1);
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
		assertThat(user1.getLevel(), is(user2.getLevel()));
		assertThat(user1.getLogin(), is(user2.getLogin()));
		assertThat(user1.getRecommend(), is(user2.getRecommend()));
		assertThat(user1.getEmail(), is(user2.getEmail()));
	}
	
	@Test
	public void update() {
		dao.deleteAll();
		
		dao.add(user1);
		dao.add(user2);
		
		user1.setName("이이이");
		user1.setPassword("9999");
		user1.setLevel(Level.GOLD);
		user1.setLogin(10000);
		user1.setRecommend(20000);
		user1.setEmail("이이이@gamil");
		int updatedRow = dao.update(user1);
		
		assertThat(updatedRow, is(1));
		
		User user1Update = dao.get(user1.getId());
		User user2Same = dao.get(user2.getId());
		checkSameUser(user1, user1Update);
		checkSameUser(user2, user2Same);
	}
	
	
//	@After
//	public void tearDown() {
//		((ConfigurableApplicationContext)context).close();
//	}
}
