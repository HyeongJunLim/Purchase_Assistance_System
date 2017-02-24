package MVC.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import JDBC.JdbcUtil;
import JDBC.connection.ConnectionProvider;
import MVC.dao.TransHandGestureDao;
import MVC.model.TransHandGesture;
import MVC.model.TransHandGestureListModel;

public class ListTransHandGestureService {
	private static ListTransHandGestureService instance = new ListTransHandGestureService();
	public static ListTransHandGestureService getInstance()	{
		return instance;
	}
	
	public TransHandGestureListModel getAllTransHandGestureListModel(String userId)	{
		TransHandGestureDao transHandGestureDao = TransHandGestureDao.getInstance();
		Connection conn = null;
		try	{
			conn = ConnectionProvider.getConnection();
			
			List<TransHandGesture> transHandList = transHandGestureDao.selectAllTransHandData(conn, userId);
			TransHandGestureListModel transHandGestureListView = new TransHandGestureListModel(transHandList, userId);
			return transHandGestureListView;
		} catch(SQLException e)	{
			throw new RuntimeException("DB에러발생: "+e.getMessage());
		} finally	{
			JdbcUtil.close(conn);
		}
	}
	
	public TransHandGestureListModel getTransHandGestureListModelByType(String userId, int type)	{
		TransHandGestureDao transHandGestureDao = TransHandGestureDao.getInstance();
		Connection conn = null;
		try	{
			conn = ConnectionProvider.getConnection();
			
			List<TransHandGesture> transHandList = transHandGestureDao.selectTransHandDataByType(conn, userId, type);
			TransHandGestureListModel transHandGestureListView = new TransHandGestureListModel(transHandList, userId);

			return transHandGestureListView;
		} catch(SQLException e)	{
			throw new RuntimeException("DB에러발생: "+e.getMessage());
		} finally	{
			JdbcUtil.close(conn);
		}
	}
}
