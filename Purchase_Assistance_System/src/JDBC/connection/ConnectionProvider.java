package JDBC.connection;

import java.sql.*;

public class ConnectionProvider {
	public static Connection getConnection() throws SQLException	{
		String url = "jdbc:mysql://210.93.52.135:3306/leap_motion";
		String userId = "root";
		String pw = "1234";
		
		return DriverManager.getConnection(url, userId, pw);	
	}
}