package MVC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.leapmotion.leap.Vector;

import JDBC.JdbcUtil;
import MVC.model.HandGesture;
import MVC.model.TransHandGesture;


public class TransHandGestureDao {
	final String DATA_DB_NAME = "handsign_info";		// DB이름
	
	final int THUMB = 0;					// 엄지
	final int INDEX = 1;					// 검지
	final int MIDDLE = 2;					// 중지
	final int RING = 3;						// 약지
	final int LITTLE = 4;					// 소지
	
	private static TransHandGestureDao instance = new TransHandGestureDao();
	public static TransHandGestureDao getInstance()	{
		return instance;
	}
	public TransHandGestureDao() {}
	
	public List<TransHandGesture> selectAllTransHandData(Connection conn, String userId) throws SQLException	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from " + DATA_DB_NAME + " where user_id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(!rs.next())	{
				return Collections.emptyList();
			}
			List<TransHandGesture> transHandList = new ArrayList<TransHandGesture>();
			
			do{
				TransHandGesture transHand = makeTransHandFromResultSet(rs);
				transHandList.add(transHand);
			} while(rs.next());         
			
			
			return transHandList;
		} finally	{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	public List<TransHandGesture> selectTransHandDataByType(Connection conn, String userId, int type) throws SQLException	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "select * from (select * from " + DATA_DB_NAME + " where user_id=?) where type=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setInt(2, type);
			rs = pstmt.executeQuery();
			
			if(!rs.next())	{
				return Collections.emptyList();
			}
			List<TransHandGesture> transHandList = new ArrayList<TransHandGesture>();
			
			do{
				TransHandGesture transHand = makeTransHandFromResultSet(rs);
				transHandList.add(transHand);
			} while(rs.next());         
			
			
			return transHandList;
		} finally	{
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	private TransHandGesture makeTransHandFromResultSet(ResultSet rs) throws SQLException {
		TransHandGesture transHandGesture = new TransHandGesture();
		
		transHandGesture.setUserId(rs.getString("user_id"));					// 지점 아이디
		transHandGesture.setRPalmPositionX(rs.getDouble("r_palmposition_x"));	// 오른 손바닥중앙 X좌표
		transHandGesture.setRPalmPositionY(rs.getDouble("r_palmposition_y"));	// 오른 손바닥중앙 Y좌표
		transHandGesture.setRPalmPositionZ(rs.getDouble("r_palmposition_z"));	// 오른 손바닥중앙 Z좌표
		transHandGesture.setLPalmPositionX(rs.getDouble("l_palmposition_x"));	// 왼손 손바닥중앙 X좌표
		transHandGesture.setLPalmPositionY(rs.getDouble("l_palmposition_y"));	// 왼손 손바닥중앙 Y좌표
		transHandGesture.setLPalmPositionZ(rs.getDouble("l_palmposition_z"));	// 왼손 손바닥중앙 Z좌표
		transHandGesture.setContent(rs.getString("content"));					// 번역 내용
		transHandGesture.setType(rs.getInt("type"));							// 타입(메뉴선택/수량 등등)	
		
		for(int i=0; i<5; i++)	{
			String R_nameX = "";
			String R_nameY = "";
			String R_nameZ = "";
			String L_nameX = "";
			String L_nameY = "";
			String L_nameZ = "";
			
			switch(i)	{
			// 손가락별 분류
				case THUMB:
					R_nameX = "r_thumb_x_";
					R_nameY = "r_thumb_y_";
					R_nameZ = "r_thumb_z_";

					L_nameX = "l_thumb_x_";
					L_nameY = "l_thumb_y_";
					L_nameZ = "l_thumb_z_";
					break;
				case INDEX:
					R_nameX = "r_index_x_";
					R_nameY = "r_index_y_";
					R_nameZ = "r_index_z_";
					
					L_nameX = "l_index_x_";
					L_nameY = "l_index_y_";
					L_nameZ = "l_index_z_";
					break;
				case MIDDLE:
					R_nameX = "r_middle_x_";
					R_nameY = "r_middle_y_";
					R_nameZ = "r_middle_z_";
					
					L_nameX = "l_middle_x_";
					L_nameY = "l_middle_y_";
					L_nameZ = "l_middle_z_";
					break;
				case RING:
					R_nameX = "r_ring_x_";
					R_nameY = "r_ring_y_";
					R_nameZ = "r_ring_z_";
					
					L_nameX = "l_ring_x_";
					L_nameY = "l_ring_y_";
					L_nameZ = "l_ring_z_";
					break;
				case LITTLE:
					R_nameX = "r_little_x_";
					R_nameY = "r_little_y_";
					R_nameZ = "r_little_z_";

					L_nameX = "l_little_x_";
					L_nameY = "l_little_y_";
					L_nameZ = "l_little_z_";
					break;
 			}	// 스위치문 끝
			for(int j=0; j<5; j++)	{
				String RnX = R_nameX+j;
				String RnY = R_nameY+j;
				String RnZ = R_nameZ+j;
				String LnX = L_nameX+j;
				String LnY = L_nameY+j;
				String LnZ = L_nameZ+j;
							
				transHandGesture.setTransHandGesture(i, j, rs.getDouble(RnX), rs.getDouble(RnY), rs.getDouble(RnZ),
														   rs.getDouble(LnX), rs.getDouble(LnY), rs.getDouble(LnZ));
			}	// for(int j=0; j<5; j++) 의 끝
		}	// for(int i=0; i<5; i++) 의 끝
		
		return transHandGesture;
	}
	public void insert(Connection conn, String userId, int type, HandGesture handGesture, String content) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try	{
			String query = "insert into " + DATA_DB_NAME + "(user_id, type, content) values(?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setInt(2, type);
			pstmt.setString(3, content);
			pstmt.executeUpdate();
			
			for(int i=0; i<5; i++)	{
				String R_nameX = "";
				String R_nameY = "";
				String R_nameZ = "";
				String L_nameX = "";
				String L_nameY = "";
				String L_nameZ = "";
				
				switch(i)	{
				// 손가락별 분류
					case THUMB:
						R_nameX = "r_thumb_x_";
						R_nameY = "r_thumb_y_";
						R_nameZ = "r_thumb_z_";

						L_nameX = "l_thumb_x_";
						L_nameY = "l_thumb_y_";
						L_nameZ = "l_thumb_z_";
						break;
					case INDEX:
						R_nameX = "r_index_x_";
						R_nameY = "r_index_y_";
						R_nameZ = "r_index_z_";
						
						L_nameX = "l_index_x_";
						L_nameY = "l_index_y_";
						L_nameZ = "l_index_z_";
						break;
					case MIDDLE:
						R_nameX = "r_middle_x_";
						R_nameY = "r_middle_y_";
						R_nameZ = "r_middle_z_";
						
						L_nameX = "l_middle_x_";
						L_nameY = "l_middle_y_";
						L_nameZ = "l_middle_z_";
						break;
					case RING:
						R_nameX = "r_ring_x_";
						R_nameY = "r_ring_y_";
						R_nameZ = "r_ring_z_";
						
						L_nameX = "l_ring_x_";
						L_nameY = "l_ring_y_";
						L_nameZ = "l_ring_z_";
						break;
					case LITTLE:
						R_nameX = "r_little_x_";
						R_nameY = "r_little_y_";
						R_nameZ = "r_little_z_";
	
						L_nameX = "l_little_x_";
						L_nameY = "l_little_y_";
						L_nameZ = "l_little_z_";
						break;
	 			}	// 스위치문 끝
				for(int j=0; j<5; j++)	{
					String RnX = R_nameX+j;
					String RnY = R_nameY+j;
					String RnZ = R_nameZ+j;
					String LnX = L_nameX+j;
					String LnY = L_nameY+j;
					String LnZ = L_nameZ+j;
					
					query = "update " + DATA_DB_NAME + " set " + RnX + "=?," + RnY + "=?," + RnZ + "=?, " +
																 LnX + "=?," + LnY + "=?," + LnZ + "=? where user_id=? and type=? and content=?";
					pstmt = conn.prepareStatement(query);
					pstmt.setDouble(1, handGesture.getRightX(i, j));
					pstmt.setDouble(2, handGesture.getRightY(i, j));
					pstmt.setDouble(3, handGesture.getRightZ(i, j));
					pstmt.setDouble(4, handGesture.getLeftX(i, j));
					pstmt.setDouble(5, handGesture.getLeftY(i, j));
					pstmt.setDouble(6, handGesture.getLeftZ(i, j));
					pstmt.setString(7, userId);
					pstmt.setInt(8, type);
					pstmt.setString(9, content);
					pstmt.executeUpdate();
					
				}	// for(int j=0; j<5; j++) 의 끝
			}	// for(int i=0; i<5; i++) 의 끝
			
			String RpX = "r_palmposition_x";
			String RpY = "r_palmposition_y";
			String RpZ = "r_palmposition_z";
			String LpX = "l_palmposition_x";
			String LpY = "l_palmposition_y";
			String LpZ = "l_palmposition_z";
			
			query = "update " + DATA_DB_NAME + " set "+ RpX + "=?," + RpY + "=?," + RpZ + "=?, " +
														LpX + "=?," + LpY + "=?," + LpZ + "=? where user_id=? and type=? and content=?";
			pstmt = conn.prepareStatement(query);
			
			pstmt.setDouble(1, handGesture.getRightPalmPosition().getX());
			pstmt.setDouble(2, handGesture.getRightPalmPosition().getY());
			pstmt.setDouble(3, handGesture.getRightPalmPosition().getZ());
			pstmt.setDouble(4, handGesture.getLeftPalmPosition().getX());
			pstmt.setDouble(5, handGesture.getLeftPalmPosition().getY());
			pstmt.setDouble(6, handGesture.getLeftPalmPosition().getZ());
			pstmt.setString(7, userId);
			pstmt.setInt(8, type);
			pstmt.setString(9, content);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}	
}
