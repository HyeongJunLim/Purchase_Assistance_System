package MVC.service;

import java.sql.Connection;
import java.sql.SQLException;



import JDBC.JdbcUtil;
import JDBC.connection.ConnectionProvider;
import MVC.dao.UserDao;
import MVC.model.User;

public class RegeditUserService {
	private static RegeditUserService instance = new RegeditUserService();
	public static RegeditUserService getInstance()	{
		return instance;
	}
	private RegeditUserService()	{}
	
	public User regedit(User user) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			boolean isInsert = UserDao.getInstance().regedit(conn, user);
			if (isInsert == false) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException("DB »ðÀÔ ¾È µÊ");
			}
			conn.commit();
			
			return user;
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
