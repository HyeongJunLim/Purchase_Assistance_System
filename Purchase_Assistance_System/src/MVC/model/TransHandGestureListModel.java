package MVC.model;

import java.util.ArrayList;
import java.util.List;

public class TransHandGestureListModel {
	private List<TransHandGesture> transHandList;	// 번역 손좌표
	private String requestId;						// 요청한 지점아이디
	
	
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
		// 가지고있는지 확인
		return ! transHandList.isEmpty();
	}
}
