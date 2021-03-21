//package repository;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javax.sql.DataSource;
//
//import springbook.user.domain.User;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//public class UserDao {
//	private DataSource dataSource;
//	private JdbcContext jdbcContext;
//	
//	private JdbcTemplate jdbcTemplate;
//	
//	public void setDataSource(DataSource dataSource) {
//		this.jdbcTemplate = new JdbcTemplate(dataSource);
//		
//		this.jdbcContext = new JdbcContext();
//		this.jdbcContext.setDataSource(dataSource);
//		this.dataSource = dataSource;
//	}
//	
//    public void add(User user) throws SQLException {
////    	this.jdbcContext.executeSql("insert into users(id, name, password) values (?, ?, ?)"
////    			,user.getId()
////    			,user.getName()
////    			,user.getPassword());
//    	this.jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?)"
//    			,user.getId()
//    			,user.getName()
//    			,user.getPassword());
//    }
//    
//    public void deleteAll() throws SQLException {
////    	this.jdbcTemplate.update(
////    			new PreparedStatementCreator() {
////					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
////						return con.prepareStatement("truncate table users");
////					}
////				}
////		);
//    	this.jdbcTemplate.update("truncate table users");
//    }
//    
//    
//    
//    
//    public User get(String id) throws SQLException {
//    	
//        Connection c = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        
//        try {
//        	c = dataSource.getConnection();
//        	
//        	ps = c.prepareStatement(
//        			"SELECT id, name, password FROM users WHERE id = ?");
//        	ps.setString(1, id);
//        	
//        	rs = ps.executeQuery();
//        	User user = null;
//        	if (rs.next()) {
//        		user = new User();
//        		user.setId(rs.getString("id"));
//        		user.setName(rs.getString("name"));
//        		user.setPassword(rs.getString("password"));
//        		
//        		return user;
//        	} else {
//        		throw new EmptyResultDataAccessException(1);        	
//        	}
//        } catch (SQLException e) {
//        	throw e;
//        } finally {
//        	if (rs != null) {
//        		try {
//        			rs.close();
//        		} catch (SQLException e) {
//        		}
//    		}
//        	if (ps != null) {
//        		try {
//        			ps.close();
//        		} catch (SQLException e) {
//        		}
//    		}
//        	if (c != null) {
//        		try {
//        			c.close();
//        		} catch (SQLException e) {
//        		}
//        	}
//        }
//    	
//    }
//    
//    public int getCount() throws SQLException {
//    	Connection c = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        int count = 0;
//        
//        try {        	
//        	c = dataSource.getConnection();
//        	
//        	ps = c.prepareStatement(
//        			"SELECT id, name, password FROM users");
//        	
//        	
//        	rs = ps.executeQuery();
//        	
//        	while(rs.next()) {
//        		count++;
//        	}
//        } catch (Exception e) {
//        	throw e;
//        } finally {
//        	if (rs != null) {
//        		try {
//        			rs.close();
//        		} catch (SQLException e) {
//        		}
//    		}
//        	if (ps != null) {
//        		try {
//        			ps.close();
//        		} catch (SQLException e) {
//        		}
//    		}
//        	if (c != null) {
//        		try {
//        			c.close();
//        		} catch (SQLException e) {
//        		}
//        	}
//        }
//    	
//    	return count;
//    }
//    
//    public void jdbcContextWithStatementStrategy(StatementStrategy strategy) throws SQLException {
//    	Connection c = null;
//        PreparedStatement ps = null;
//        
//        try {
//	    	c = dataSource.getConnection();
//	    	
//	    	ps = strategy.makePreparedStatement(c);
//	    	
//	    	ps.executeUpdate();
//        } catch (SQLException e) {
//        	throw e;
//        } finally {
//        	if (ps != null) {
//        		try {
//        			ps.close();
//        		} catch (SQLException e) {
//        		}
//    		}
//        	if (c != null) {
//        		try {
//        			c.close();
//        		} catch (SQLException e) {
//        		}
//        	}
//        }
//    }
//}
