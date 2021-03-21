package repository;

import springbook.user.domain.User;

public interface UserServiceUpgradePolicy {
	public boolean canUpgradeLevel(User user);
	public void upgradeLevel(User user);
	public int getMinLogcount();
	public int getMinRecommend();
}
