package springbook.test;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.service.UserService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="../../resources/test-applicationContext.xml")
public class UserServiceTest {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;
	
	List<User> users;
	
	@Before
	public void setUp() {
		users = Arrays.asList(new User("nntp", "김현빈", "1234", Level.BASIC, 49, 0),
				new User("jinsung", "이진성", "4567", Level.BASIC, 55, 10),
				new User("fedss2", "이건", "1357", Level.GOLD, 100, 40),
				new User("kaiba", "진용진", "3333", Level.BASIC, 100, 40),
				new User("ezio", "에지오", "0622", Level.SILVER, 60, 40),
				new User("caludea", "클라우데아", "0626", Level.SILVER, 60, 30),
				new User("draven", "드레이븐", "0623", Level.SILVER, 60, 29)
				);
	}
	
	@Test
	public void bean() {
		assertThat(this.userDao, is(notNullValue()));
		assertThat(this.userService, is(notNullValue()));
	}
	
	@Test
	public void upgradeLevels() {
		userDao.deleteAll();
		for(User user : users) userDao.add(user);
		userService.upgradeLevels();
		
		checkLevel(users.get(0), Level.BASIC);
		checkLevel(users.get(1), Level.SILVER);
		checkLevel(users.get(2), Level.GOLD);
		checkLevel(users.get(3), Level.GOLD);
		checkLevel(users.get(4), Level.GOLD);
		checkLevel(users.get(5), Level.GOLD);
		checkLevel(users.get(6), Level.SILVER);
	}
	
	@Test
	public void add() {
		userDao.deleteAll();
		
		User userWithLevel = users.get(2);
		User userWithoutLevel = users.get(4);
		userWithoutLevel.setLevel(null);
		
		userService.add(userWithLevel);
		userService.add(userWithoutLevel);
		
		checkLevel(userWithLevel, Level.GOLD);
		checkLevel(userWithoutLevel, Level.BASIC);
	}
	
	private void checkLevel(User user, Level expectedLevel) {
		User userRead = userDao.get(user.getId());
		assertThat(userRead.getLevel(), is(expectedLevel));
	}
}
