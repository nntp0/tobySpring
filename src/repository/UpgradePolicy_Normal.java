package repository;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

public class UpgradePolicy_Normal implements UserServiceUpgradePolicy {
	public int MIN_LOGCOUNT_FOR_SILVER = 50;
	public int MIN_RECOMMEND_FOR_GOLD = 30;
	
	UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch (currentLevel) {
			case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
			case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD: return false;
			
			default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
		}
	}
	public void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
	}
	@Override
	public int getMinLogcount() {
		return this.MIN_LOGCOUNT_FOR_SILVER;
	}
	@Override
	public int getMinRecommend() {
		return this.MIN_RECOMMEND_FOR_GOLD;
	}
}
