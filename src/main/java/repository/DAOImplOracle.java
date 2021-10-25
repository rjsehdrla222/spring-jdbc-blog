package repository;

import java.sql.*;

public abstract class DAOImplOracle implements DAO {

	private Connection conn = null;

	public Connection getConnection() {
		String jdbcUrl = "jdbc:mysql://localhost/program?serverTimezone=UTC";
		String dbUser = "root";
		String dbPass = "ehdgus1";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;

	}

	public void closeResources(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs){
		if(rs!= null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(stmt!= null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if(pstmt!= null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		if(conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}