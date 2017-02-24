package MVC.model;

import java.util.ArrayList;
import java.util.List;

public class TransHandGestureListModel {
	private List<TransHandGesture> transHandList;	// ���� ����ǥ
	private String requestId;						// ��û�� �������̵�
	
	
	public TransHandGestureListModel() {
		this(new ArrayList<TransHandGesture>(), null);
	}
	public TransHandGestureListModel(List<TransHandGesture> transHandList, String requestId)	{
		this.transHandList = transHandList;
		this.requestId = requestId;
	}
	public String getRequestId() {
		return requestId;
	}
	public List<TransHandGesture> getTransHandList() {
		return transHandList;
	}
	public boolean isHasTransHandGesture()	{
		// �������ִ��� Ȯ��
		return ! transHandList.isEmpty();
	}
}
