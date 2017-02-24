package MVC.model;

/**
 * Created by user on 2016-08-01.
 */
public class User {
	private String userId;			// ���� ���̵�
	private String userPw;			// ���� ��й�ȣ
	private String userName;		// �̸�
	private String branchName;		// ������
	private String phoneNumber;		// ��ȭ��ȣ
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public void toUser(String userId, String userPw, String userName, String branchName, String phoneNumber)	{
		this.setUserId(userId);
		this.setUserPw(userPw);
		this.setUserName(userName);
		this.setBranchName(branchName);
		this.setPhoneNumber(phoneNumber);
	}
}
