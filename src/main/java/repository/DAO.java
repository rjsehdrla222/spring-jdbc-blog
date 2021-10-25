package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public interface DAO {
	Connection getConnection();
	void closeResources(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs);
}
