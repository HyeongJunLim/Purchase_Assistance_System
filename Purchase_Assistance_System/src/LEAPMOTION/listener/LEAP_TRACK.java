package LEAPMOTION.listener;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;





import com.leapmotion.leap.Vector;

import LEAPMOTION.gui.Graphic;
import MVC.model.HandGesture;



// 리스너를 상속받아 클래스 샘플리스너를 생성(립모션을 연결하고 데이터 처리하는 클래스)
public class LEAP_TRACK extends Listener	{
	Graphic graphic = Graphic.getInstance();
	HandGesture handGesture = HandGesture.getInstance();
	
	private static LEAP_TRACK instance = new LEAP_TRACK();
	public static LEAP_TRACK getInstance()	{
		return instance;
	}
	
	// 초기화 함수
	public void onInit(Controller controller) {
		System.out.println("TRACK 초기화");
	}
	// 립모션의 연결부분으로 컨트롤러가 제스처를 인식하도록 한다.
	public void onConnect(Controller controller) {
		System.out.println("TRACK 립모션 연결");
	}
	// 연결 종료
	public void onDisconnect(Controller controller) {
		System.out.println("TRACK 립모션 연결 종료");
	}
	// 어플리케이션 종료
	public void onExit(Controller controller) {
		System.out.println("TRACK 어플리케이션 종료");
	}
	// 립모션으로부터 매 프레임마다 Raw데이터를 입력받아 출력하는 부분
	public void onFrame(Controller controller) {
		// 가장 최신으로 컨트롤러로부터 프레임 정보를 입력 받는다. 
		Frame frame = controller.frame();
		
		
		handGesture.initInstance();

		
		
		int i, j;
		for( Hand hand : frame.hands() ) {
			i=0;
			
			for (Finger finger : hand.fingers()) {
				j=0;
			  
				// 뼈 받아오기
				for(Bone.Type boneType : Bone.Type.values()) {
					Bone bone = finger.bone(boneType);
					
	                /**********************************************************************
	                 * 	Type.TYPE_DISTAL			=>	첫번째마디 (손톱뼈)					  *
	                 *	Type.TYPE_INTERMEDIATE		=>	두번째마디							  *
	                 *	Type.TYPE_PROXIMAL			=>	세번째마디							  *
	                 *	Type.TYPE_METACARPAL		=>	네번째마디 (손바닥)	=> 엄지는 없음.	      *
	                 **********************************************************************/
					
					
					if(hand.isRight())	{
						// 오른손일 경우
						if(bone.type().equals(Bone.Type.TYPE_METACARPAL))	{
							handGesture.setRightX(i, j, bone.prevJoint().getX());
							handGesture.setRightY(i, j, bone.prevJoint().getY());
							handGesture.setRightZ(i, j, bone.prevJoint().getZ());
							j++;
						}
						handGesture.setRightX(i, j, bone.nextJoint().getX());
						handGesture.setRightY(i, j, bone.nextJoint().getY());
						handGesture.setRightZ(i, j, bone.nextJoint().getZ());
						
						j++;
					} else	{
						// 왼손일 경우
						if(bone.type().equals(Bone.Type.TYPE_METACARPAL))	{
							handGesture.setLeftX(i, j, bone.prevJoint().getX());
							handGesture.setLeftY(i, j, bone.prevJoint().getY());
							handGesture.setLeftZ(i, j, bone.prevJoint().getZ());
							j++;
						}
						handGesture.setLeftX(i, j, bone.nextJoint().getX());
						handGesture.setLeftY(i, j, bone.nextJoint().getY());
						handGesture.setLeftZ(i, j, bone.nextJoint().getZ());
						j++;
					}	// if(hand.isLeft) 의 끝
				}		//for(Bone.Type boneType : Bone.Type.values()) 의 끝
				i++;			
			}
			
			if( hand.isRight() )	{
				// 오른손일경우
				handGesture.setRightPalmPosition(hand.palmPosition());
			} else {
				// 왼손일경우
				handGesture.setLeftPalmPosition(hand.palmPosition());
			}
		}	//for( Hand hand : frame.hands() ) 의 끝
	
		graphic.repaint();
	}
}
