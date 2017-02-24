package MVC.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import JDBC.JdbcUtil;
import MVC.model.User;

public class UserDao {
	final String USER_DB_NAME = "user_info";
	
	private static UserDao instance = new UserDao();
	public static UserDao getInstance()	{
		return instance;
	}
	public UserDao()	{}
	
	public User selectUser(Connection conn, String userId) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try	{
			pstmt = conn.prepareStatement("select * from " + USER_DB_NAME + " where user_id=?");
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();
			if(!rs.next())	{
				return null;
			}
			
			User user = makeUserFromResultSet(rs);
			
			return user;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	private User makeUserFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		
		user.setUserId(rs.getString("user_id"));
		user.setUserPw(rs.getString("user_pw"));
		user.setUserName(rs.getString("user_name"));
		user.setBranchName(rs.getString("branch_name"));
		user.setPhoneNumber(rs.getString("phone_number"));
		
		return user;
	}
	
	public boolean regedit(Connection conn, User user) throws SQLException	{
 		PreparedStatement pstmt = null;
 		ResultSet rs = null;
 		
 		try {
 			String query = "insert into " + USER_DB_NAME + "(user_id, user_pw, user_name, branch_name, phone_number) " +
 						   "values(?, ?, ?, ?, ?)";
 			pstmt = conn.prepareStatement(query);
 			pstmt.setString(1, user.getUserId());
 			pstmt.setString(2, user.getUserPw());
 			pstmt.setString(3, user.getUserName());
 			pstmt.setString(4, user.getBranchName());
 			pstmt.setString(5, user.getPhoneNumber());
 			
 			int result = pstmt.executeUpdate();
 			if(result>0)	{
 				return true;
 			}
 	
			return false;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
}
