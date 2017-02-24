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



// �����ʸ� ��ӹ޾� Ŭ���� ���ø����ʸ� ����(������� �����ϰ� ������ ó���ϴ� Ŭ����)
public class LEAP_TRACK extends Listener	{
	Graphic graphic = Graphic.getInstance();
	HandGesture handGesture = HandGesture.getInstance();
	
	private static LEAP_TRACK instance = new LEAP_TRACK();
	public static LEAP_TRACK getInstance()	{
		return instance;
	}
	
	// �ʱ�ȭ �Լ�
	public void onInit(Controller controller) {
		System.out.println("TRACK �ʱ�ȭ");
	}
	// ������� ����κ����� ��Ʈ�ѷ��� ����ó�� �ν��ϵ��� �Ѵ�.
	public void onConnect(Controller controller) {
		System.out.println("TRACK ����� ����");
	}
	// ���� ����
	public void onDisconnect(Controller controller) {
		System.out.println("TRACK ����� ���� ����");
	}
	// ���ø����̼� ����
	public void onExit(Controller controller) {
		System.out.println("TRACK ���ø����̼� ����");
	}
	// ��������κ��� �� �����Ӹ��� Raw�����͸� �Է¹޾� ����ϴ� �κ�
	public void onFrame(Controller controller) {
		// ���� �ֽ����� ��Ʈ�ѷ��κ��� ������ ������ �Է� �޴´�. 
		Frame frame = controller.frame();
		
		
		handGesture.initInstance();

		
		
		int i, j;
		for( Hand hand : frame.hands() ) {
			i=0;
			
			for (Finger finger : hand.fingers()) {
				j=0;
			  
				// �� �޾ƿ���
				for(Bone.Type boneType : Bone.Type.values()) {
					Bone bone = finger.bone(boneType);
					
	                /**********************************************************************
	                 * 	Type.TYPE_DISTAL			=>	ù��°���� (�����)					  *
	                 *	Type.TYPE_INTERMEDIATE		=>	�ι�°����							  *
	                 *	Type.TYPE_PROXIMAL			=>	����°����							  *
	                 *	Type.TYPE_METACARPAL		=>	�׹�°���� (�չٴ�)	=> ������ ����.	      *
	                 **********************************************************************/
					
					
					if(hand.isRight())	{
						// �������� ���
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
						// �޼��� ���
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
					}	// if(hand.isLeft) �� ��
				}		//for(Bone.Type boneType : Bone.Type.values()) �� ��
				i++;			
			}
			
			if( hand.isRight() )	{
				// �������ϰ��
				handGesture.setRightPalmPosition(hand.palmPosition());
			} else {
				// �޼��ϰ��
				handGesture.setLeftPalmPosition(hand.palmPosition());
			}
		}	//for( Hand hand : frame.hands() ) �� ��
	
		graphic.repaint();
	}
}
