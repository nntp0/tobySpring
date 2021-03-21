package springbook.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.dao.UserDaoJdbc;
import springbook.user.domain.User;
import springbook.user.domain.Level;

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
		assertEquals(dao.getCount(), 0);

		dao.add(user1);
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		user3 = dao.get(user1.getId());
		assertEquals(user1.getName(), user3.getName());
		assertEquals(user1.getPassword(), user3.getPassword());
		
		user3 = dao.get(user2.getId());
		assertEquals(user2.getName(), user3.getName());
		assertEquals(user2.getPassword(), user3.getPassword());
	}
	
	@Test
	public void getCount() throws SQLException {
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
		dao.add(user1);
		assertEquals(dao.getCount(), 1);
		
		dao.add(user2);
		assertEquals(dao.getCount(), 2);
		
		dao.add(user3);
		assertEquals(dao.getCount(), 3);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {		
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
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
		assertEquals(dao.getCount(), 0);
		
		dao.add(user1);
		dao.add(user2);
		dao.add(user3);
		assertEquals(dao.getCount(), 3);
		
		List<User> userList = dao.getAll();
		assertEquals(userList.size(), 3);
		checkSameUser(userList.get(0), user3);
		checkSameUser(userList.get(1), user2);
		checkSameUser(userList.get(2), user1);
	}
	
	@Test
	public void getAllUsers_Zero() throws SQLException {		
		dao.deleteAll();
		assertEquals(dao.getCount(), 0);
		
		List<User> userList = dao.getAll();
		assertEquals(userList.size(), 0);
	}
	
	private void checkSameUser(User user1, User user2) {
		assertEquals(user1.getId(), user2.getId());
		assertEquals(user1.getName(), user2.getName());
		assertEquals(user1.getPassword(), user2.getPassword());
		assertEquals(user1.getLevel(), user2.getLevel());
		assertEquals(user1.getLogin(), user2.getLogin());
		assertEquals(user1.getRecommend(), user2.getRecommend());
		assertEquals(user1.getEmail(), user2.getEmail());
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
		
		assertEquals(updatedRow, 1);
		
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
