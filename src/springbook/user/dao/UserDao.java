package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDao {
	private ConnectionMaker connectionMaker;
	
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}
    public void add(User user) throws ClassNotFoundException, SQLException {
    	
        Connection c = null;
        PreparedStatement ps = null;
        
    	c = connectionMaker.getConnection();
    	
    	ps = c.prepareStatement(
    			"insert into users(id, name, password) values (?, ?, ?)");
    	ps.setString(1, user.getId());
    	ps.setString(2, user.getName());
    	ps.setString(3, user.getPassword());
    	
    	ps.executeUpdate();
    	
    	ps.close();
    	c.close();
    }
    
    public User get(String id) throws ClassNotFoundException, SQLException {
    	
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        c = connectionMaker.getConnection();
    	
    	ps = c.prepareStatement(
    			"SELECT id, name, password FROM users WHERE id = ?");
    	ps.setString(1, id);
    	
    	rs = ps.executeQuery();
    	rs.next();
    	User user = new User();
    	user.setId(rs.getString("id"));
    	user.setName(rs.getString("name"));
    	user.setPassword(rs.getString("password"));
    	
    	rs.close();
    	ps.close();
    	c.close();
    	
    	return user;
    }
     
    
}
