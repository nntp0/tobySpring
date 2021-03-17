package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import springbook.user.domain.User;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {
	private JdbcTemplate jdbcTemplate;
	private RowMapper<User> userMapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	};
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
    public void add(User user) throws SQLException {
    	this.jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?)",
    			user.getId(),
    			user.getName(),
    			user.getPassword());
    }
    
    public void deleteAll() throws SQLException {
    	this.jdbcTemplate.update("truncate table users");
    }

    public User get(String id) throws SQLException {
//    	return this.jdbcTemplate.query(
//    			new PreparedStatementCreator() {
//					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//						PreparedStatement ps = con.prepareStatement("select * from users where id = ?");
//						ps.setString(1, id);
//						
//						return ps;
//					}
//				}, new ResultSetExtractor<User>() {
//					public User extractData(ResultSet rs) throws SQLException, DataAccessException {
//						User user = null;
//						if (rs.next()) {
//							user = new User();
//							user.setId(rs.getString("id"));
//							user.setName(rs.getString("name"));
//							user.setPassword(rs.getString("password"));
//						} else {
//							throw new EmptyResultDataAccessException(1);
//						}
//						return user;
//					}
//				});
    	return this.jdbcTemplate.queryForObject("select * from users where id = ?",
    			new Object[] {id}, this.userMapper);
    }
    
    public int getCount() throws SQLException {
    	return this.jdbcTemplate.query(
    			new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						return con.prepareStatement("select count(*) from users");
					}
				}, new ResultSetExtractor<Integer>() {
					public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
						rs.next();
						return rs.getInt(1);
					}
				});
    }
    
    public List<User> getAll() {
    	return this.jdbcTemplate.query(
    			new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						return con.prepareStatement("select * from users ORDER BY id");
					}
    			}, this.userMapper);
    }
    
    
}
