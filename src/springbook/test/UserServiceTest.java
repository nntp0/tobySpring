package springbook.test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static springbook.user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static springbook.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;

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
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.PlatformTransactionManager;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.UserService;

import static org.hamcrest.CoreMatchers.is;

@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
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
		userService.upgradeLevels();
		
		checkLevel(users.get(0), false);
		checkLevel(users.get(1), true);
		checkLevel(users.get(2), false);
		checkLevel(users.get(3), true);
		checkLevel(users.get(4), true);
		checkLevel(users.get(5), true);
		checkLevel(users.get(6), false);
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
		
		assertThat(userWithLevelRead.getLevel(), is(Level.GOLD));
		assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception {
		UserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
		testUserService.setTransactionManager(this.transactionManager);
		testUserService.setMailSender(mailSender);
		
		
		
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
		userService.setMailSender(mailSender);

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
		assertThat(request.size(), is(4));
		assertThat(request.contains(users.get(0).getEmail()), is(false));
		assertThat(request.contains(users.get(1).getEmail()), is(true));
		assertThat(request.contains(users.get(2).getEmail()), is(false));
		assertThat(request.contains(users.get(3).getEmail()), is(true));
		assertThat(request.contains(users.get(4).getEmail()), is(true));
		assertThat(request.contains(users.get(5).getEmail()), is(true));
		assertThat(request.contains(users.get(6).getEmail()), is(false));
	}
	
	
	
	private void checkLevel(User user, boolean upgraded) {
		User userRead = userDao.get(user.getId());
		if(upgraded) {
			assertThat(userRead.getLevel(), is(user.getLevel().nextLevel()));
		}
		else {
			assertThat(userRead.getLevel(), is(user.getLevel()));
		}
	}
	
	static class TestUserServiceException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	
	static class TestUserService extends UserService {
		private String id;
		
		private TestUserService(String id) {
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
}
