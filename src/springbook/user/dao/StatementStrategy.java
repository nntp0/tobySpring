package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
	// Connection C�� �����ϸ�, �׿� ���� �����ؾ��ϴ� SQL���� ���� PreparedStatement�� ��ȯ�ϴ� �Լ� 
	PreparedStatement makePreparedStatement(Connection c) throws SQLException;
}
