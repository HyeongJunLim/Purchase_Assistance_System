package MVC.service;

import java.sql.Connection;
import java.sql.SQLException;

import JDBC.JdbcUtil;
import JDBC.connection.ConnectionProvider;
import MVC.dao.TransHandGestureDao;
import MVC.model.HandGesture;

public class InsertTransHandGestureService {
	private static InsertTransHandGestureService instance = new InsertTransHandGestureService();
	public static InsertTransHandGestureService getInstance()	{
		return instance;
	}
	private InsertTransHandGestureService()	{}
	
	public void insert(String userId, HandGesture handGesture, int type, String content)	{
		Connection conn = null;
		try	{
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			TransHandGestureDao.getInstance().insert(conn, userId, type, handGesture, content);
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB ¿¡·¯: "+ e.getMessage(), e);
		} finally {
			if(conn != null)	{
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
				}
			}
			JdbcUtil.close(conn);
		}
 
	}
}
