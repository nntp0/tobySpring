package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
	// Connection C를 제공하면, 그에 대한 실행해야하는 SQL문을 담은 PreparedStatement를 반환하는 함수 
	PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
