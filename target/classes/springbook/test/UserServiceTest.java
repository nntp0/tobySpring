package springbook.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static springbook.user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.TransactionHandler;
import springbook.user.service.UserService;
import springbook.user.service.UserServiceImpl;
import springbook.user.service.UserServiceTx;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../../resources/test-applicationContext.xml")
public class UserServiceTest {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private MailSender mailSender;
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	List<User> users;
	
	@Before
	public void setUp() {
				
		users = Arrays.asList(new User("nntp", "김현빈", "1234", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0, "em0"),
				new User("jinsung", "이진성", "4567", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 10, "em1"),
				new User("fedss2", "이건", "1357", Level.GOLD, 100, MIN_RECOMMEND_FOR_GOLD, "em2"),
				new User("kaiba", "진용진", "3333", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 40, "em3"),
				new User("ezio", "에지오", "0622", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD+1, "em4"),
				new User("caludea", "클라우데아", "0626", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD, "em5"),
				new User("draven", "드레이븐", "0623", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1, "em6")
				);
	}
	
	@Test
	public void upgradeLevels() throws Exception {
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		
		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);
		
		userService.upgradeLevels();
		
		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), true);
		checkLevel(users.get(5), true);
		checkLevel(users.get(6), false);
		
		List<String> request = mockMailSender.getRequests();
		assertEquals(request.size(), 4);
		assertEquals(request.contains(users.get(0).getEmail()), false);
		assertEquals(request.contains(users.get(1).getEmail()), true);
		assertEquals(request.contains(users.get(2).getEmail()), false);
		assertEquals(request.contains(users.get(3).getEmail()), true);
		assertEquals(request.contains(users.get(4).getEmail()), true);
		assertEquals(request.contains(users.get(5).getEmail()), true);
		assertEquals(request.contains(users.get(6).getEmail()), false);
	}
	
	@Test
	public void upgradeLevelsSeperated() throws Exception {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		
		MockUserDao mockUserDao = new MockUserDao(this.users);
		userServiceImpl.setUserDao(mockUserDao);
		
		MockMailSender mockMailSender = new MockMailSender();
		userServiceImpl.setMailSender(mockMailSender);
		
		userServiceImpl.upgradeLevels();
		
		List<User> updated = mockUserDao.getUpdated();
		assertEquals(updated.size(), 4);
		checkUserAndLevel(updated.get(0), "jinsung", Level.SILVER);
		checkUserAndLevel(updated.get(1), "kaiba", Level.SILVER);
		checkUserAndLevel(updated.get(2), "ezio", Level.GOLD);
		checkUserAndLevel(updated.get(3), "caludea", Level.GOLD);
		
		List<String> request = mockMailSender.getRequests();
		assertEquals(request.size(), 4);
		assertEquals(request.contains(users.get(0).getEmail()), false);
		assertEquals(request.contains(users.get(1).getEmail()), true);
		assertEquals(request.contains(users.get(2).getEmail()), false);
		assertEquals(request.contains(users.get(3).getEmail()), true);
		assertEquals(request.contains(users.get(4).getEmail()), true);
		assertEquals(request.contains(users.get(5).getEmail()), true);
		assertEquals(request.contains(users.get(6).getEmail()), false);
	}
	
	//Mockito Mock 생성이 안됨; 원인은 JDK가 기능을 지원하지 않는다.
//	@Test
//	public void upgradeLevelsWithMockito() throws Exception {
//		UserServiceImpl userServiceImpl = new UserServiceImpl();
//		
//		UserDao mockUserDao = mock(UserDao.class);
//		when(mockUserDao.getAll()).thenReturn(users);
//		userServiceImpl.setUserDao(mockUserDao);
//		
//		MailSender mockMailSender = mock(MailSender.class);
//		userServiceImpl.setMailSender(mockMailSender);
//		
//		userServiceImpl.upgradeLevels();
//		
//		verify(mockUserDao, times(2)).update(any(User.class));
//	}
	
	private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
		assertEquals(updated.getId(), expectedId);
		assertEquals(updated.getLevel(), expectedLevel);
	}
	
	@Test
	public void add() {
		userDao.deleteAll();
		
		User userWithLevel = users.get(2);
		User userWithoutLevel = users.get(4);
		userWithoutLevel.setLevel(null);
		
		userService.add(userWithLevel);
		userService.add(userWithoutLevel);
		
		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());
		
		assertEquals(userWithLevelRead.getLevel(), Level.GOLD);
		assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception {
		TestUserServiceImpl testUserServiceImpl = new TestUserServiceImpl(users.get(3).getId());
		testUserServiceImpl.setUserDao(this.userDao);
		testUserServiceImpl.setMailSender(this.mailSender);
		
		UserServiceTx testUserService = new UserServiceTx();
		testUserService.setUserService(testUserServiceImpl);
		testUserService.setTransactionManager(this.transactionManager);
		
		
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		try {
			testUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {
		}
		checkLevel(users.get(1), false);
	}
	
	@Test
	@DirtiesContext
	public void sendEMailList() throws Exception { 
		MockMailSender mailSender = new MockMailSender();
		userServiceImpl.setMailSender(mailSender);

		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		userService.upgradeLevels();
		
		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), true);
		checkLevel(users.get(5), true);
		checkLevel(users.get(6), false);
		
		List<String> request = mailSender.getRequests();
		assertEquals(request.size(), 4);
		assertEquals(request.contains(users.get(0).getEmail()), false);
		assertEquals(request.contains(users.get(1).getEmail()), true);
		assertEquals(request.contains(users.get(2).getEmail()), false);
		assertEquals(request.contains(users.get(3).getEmail()), true);
		assertEquals(request.contains(users.get(4).getEmail()), true);
		assertEquals(request.contains(users.get(5).getEmail()), true);
		assertEquals(request.contains(users.get(6).getEmail()), false);
	}
	
	@Test
	public void upgradeAllOrNothingByDynamicProxy() throws Exception {
		TestUserServiceImpl testUserServiceImpl = new TestUserServiceImpl(users.get(3).getId());
		testUserServiceImpl.setUserDao(this.userDao);
		testUserServiceImpl.setMailSender(this.mailSender);
		
		TransactionHandler transactionHandler = new TransactionHandler();
		transactionHandler.setTarget(testUserServiceImpl);
		transactionHandler.setPlatformTransactionManager(transactionManager);
		transactionHandler.setPattern("upgradeLevels");
		
		UserService txUserService = (UserService) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {UserService.class}, transactionHandler);
		
		
		
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		try {
			txUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {
		}
		checkLevel(users.get(1), false);
	}
	
	
	
	private void checkLevel(User user, boolean upgraded) {
		User userRead = userDao.get(user.getId());
		if(upgraded) {
			assertEquals(userRead.getLevel(), user.getLevel().nextLevel());
		}
		else {
			assertEquals(userRead.getLevel(), user.getLevel());
		}
	}
	static class TestUserServiceException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	static class TestUserServiceImpl extends UserServiceImpl {
		private String id;
		
		private TestUserServiceImpl(String id) {
			this.id = id;
		}
		
		protected void upgradeLevel(User user) {
			if (user.getId().equals(this.id)) throw new TestUserServiceException();
			super.upgradeLevel(user);
		}
	}
	
	
	static class MockMailSender implements MailSender {
		private List<String> requests = new ArrayList<String>();

		public List<String> getRequests() {
			return requests;
		}
		@Override
		public void send(SimpleMailMessage mailMessage) throws MailException {
			requests.add(mailMessage.getTo()[0]);
		}

		@Override
		public void send(SimpleMailMessage... arg0) throws MailException {
			
		}
	}
	static class MockUserDao implements UserDao {
		private List<User> users;
		private List<User> updated = new ArrayList<User>();
		
		private MockUserDao(List<User> users) {
			this.users = users;
		}
		public List<User> getUpdated() {
			return this.updated;
		}
		
		public List<User> getAll() {
			return this.users;
		}
		public int update(User user) {
			updated.add(user);
			return 1;
		}
		public int add(User user) {
			throw new UnsupportedOperationException();
		}
		public User get(String id) {
			throw new UnsupportedOperationException();
		}	
		public int deleteAll() {
			throw new UnsupportedOperationException();
		}
		public int getCount() {
			throw new UnsupportedOperationException();
		}
	}
}
