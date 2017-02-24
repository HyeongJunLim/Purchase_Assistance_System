package MVC.service;

import java.sql.Connection;
import java.sql.SQLException;



import JDBC.JdbcUtil;
import JDBC.connection.ConnectionProvider;
import MVC.dao.UserDao;
import MVC.model.User;

public class LoginUserService {
	private static LoginUserService instance = new LoginUserService();
	public static LoginUserService getInstance()	{
		return instance;
	}
	private LoginUserService()	{}
	
	public User login(String userId, String inputPw) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			User user = UserDao.getInstance().selectUser(conn, userId);
		
			if(inputPw.equals(user.getUserPw().toString()))
				return user;
			
			return null;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB ¿¡·¯: "+ e.getMessage(), e);
		} finally {
			if(conn != null)	{
				try {
					conn.close();
					conn.setAutoCommit(true);
				} catch (SQLException e) {
				}
			}
			JdbcUtil.close(conn);
		}
	}
}
