package LEAPMOTION.listener;

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;
import com.leapmotion.leap.Gesture.Type;
import com.leapmotion.leap.Listener;

import LEAPMOTION.gui.Graphic;
import LEAPMOTION.gui.LEAP_GUI;
import LEAPMOTION.gui.LOGIN;
import LEAPMOTION.gui.MENU_LIST;
import LEAPMOTION.gui.MenuList_Graphic;
import MVC.model.EuclidianDistanceValue;
import MVC.model.HandGesture;
import MVC.model.TransHandGesture;
import MVC.model.TransHandGestureListModel;
import MVC.service.ListTransHandGestureService;



// �����ʸ� ��ӹ޾� Ŭ���� ���ø����ʸ� ����(������� �����ϰ� ������ ó���ϴ� Ŭ����)
public class LEAP_TRANS extends Listener	{
	Graphic graphic = Graphic.getInstance();
	TransHandGestureListModel transHandGestureListModel;
	HandGesture handGesture = HandGesture.getInstance();

	// ������ ���� txt ����
	public static String txt="";
		
	// ������
	private final int ERROR=10;			// ������
	private final int THRESHOLD=49;		// ī��Ʈ��
		
	
	final int DEFAULT_TRANS_TYPE = 0;
	final int MENU_TRANS_TYPE = 1;
	final int TYPE_TRANS_TYPE = 2;
	final int SIZE_TRANS_TYPE = 3;
	final int NUM_TRANS_TYPE = 4;
	
	public int trans_type;
	public int trans_type_plus()	{
		trans_type++;
		return trans_type;
	}
	
	private static LEAP_TRANS instance = new LEAP_TRANS();
	public static LEAP_TRANS getInstance()	{
		return instance;
	}

	// �ʱ�ȭ �Լ�
	public void onInit(Controller controller) {
		System.out.println("TRANS �ʱ�ȭ");
	
		trans_type = DEFAULT_TRANS_TYPE;
		
		ListTransHandGestureService listTransHandGestureService = ListTransHandGestureService.getInstance();
		transHandGestureListModel = listTransHandGestureService.getAllTransHandGestureListModel( LOGIN.getInstance().loginUser.getUserId() );		// ID�� ���� ������ִ� ���� ����ó�� �� �ҷ���
		
		MENU_LIST.getInstance().setFrame(transHandGestureListModel);
	}
	// ������� ����κ����� ��Ʈ�ѷ��� ����ó�� �ν��ϵ��� �Ѵ�.
	public void onConnect(Controller controller) {
		System.out.println("TRANS ����� ����");
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);		// �������� �Ѿ������ ������ -�ε帮��- ���
	}
	// ���� ����
	public void onDisconnect(Controller controller) {
		System.out.println("TRANS ����� ���� ����");
	}
	// ���ø����̼� ����
	public void onExit(Controller controller) {
		MENU_LIST.getInstance().init();
		System.out.println("TRANS ���ø����̼� ����");
	}
	// ��������κ��� �� �����Ӹ��� Raw�����͸� �Է¹޾� ����ϴ� �κ�
	public void onFrame(Controller controller) {
		// ���� �ֽ����� ��Ʈ�ѷ��κ��� ������ ������ �Է� �޴´�. 
		Frame frame = controller.frame();
		
		handGesture.initInstance();
		
		int i, j;
		for( Hand hand : frame.hands() ) {
			i=0;
			
			// �������� �Ѿ������ ���� --------------------------------------------------------------------------------------------------------------
			if(!frame.gestures().isEmpty())	{
				if(LEAP_GUI.all_txt.length()>0 && txt.length()>0)	{
					// �����Ȱ� �ϳ���������
					if(hand.isRight())	{
						for(Finger thumb:hand.fingers())	{
							for(Finger index:hand.fingers())	{
								for(Finger middle:hand.fingers())	{
									for(Finger pinky:hand.fingers())	{
										for(Finger ring:hand.fingers())		{
											// �� ������� �����Ӹ��� ã�� �հ��� Fingerlist�� �־��ִ°�.
											if(thumb.type()==Finger.Type.TYPE_THUMB)	{
												if(index.type()==Finger.Type.TYPE_INDEX)	{
													if(middle.type()==Finger.Type.TYPE_MIDDLE)	{
														if(pinky.type()==Finger.Type.TYPE_PINKY)	{
															if(ring.type()==Finger.Type.TYPE_RING)		{
															// �� ������� �հ���Ÿ�� ��ġ�����ִ°�
																if(thumb.isExtended()&&!index.isExtended()&&!middle.isExtended()&&!pinky.isExtended()&&!ring.isExtended())	{								
																	// ������ ����������
																	for(Gesture gesture:frame.gestures())	{		
																		if(gesture.type()==Type.TYPE_KEY_TAP)	{
																			trans_type++;	// �������� �Ѿ
																			if(trans_type > 4)	{
																				trans_type = DEFAULT_TRANS_TYPE;
																			}
																		}
																	}
																}
																
															}
														}
													}
												}
											}
										}
									}
								}
							}	
						}
					}			
				}
			}
			// -----------------------------------------------------------------------------------------------------------
			
			for (Finger finger : hand.fingers()) {
				j=0;
			  
				// �� �޾ƿ���
				for(Bone.Type boneType : Bone.Type.values()) {
					Bone bone = finger.bone(boneType);
					
	                  /**********************************************************************
	                   * 	Type.TYPE_DISTAL			=>	ù��°���� (�����)					*
	                   *	Type.TYPE_INTERMEDIATE		=>	�ι�°����							*
	                   *	Type.TYPE_PROXIMAL			=>	����°����							*
	                   *	Type.TYPE_METACARPAL		=>	�׹�°���� (�չٴ�)	=> ������ ����.		*
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
			
			if(hand.isRight())	{
				// �������ϰ��
				handGesture.setRightPalmPosition(hand.palmPosition());
			} else {
				// �޼��ϰ��
				handGesture.setLeftPalmPosition(hand.palmPosition());
			}
		}	//for( Hand hand : frame.hands() ) �� ��


		graphic.repaint();	
		
		Map<Integer, double[][]> euclidianData = EuclidianDistanceValue.getInstance().toEuclidianDistanceValue(handGesture);
		
		CountMatchPoint(euclidianData);
	}


	
	private void CountMatchPoint(Map<Integer, double[][]> euclidianData) {
		for( TransHandGesture trans : transHandGestureListModel.getTransHandList() )	{
			Map<Integer, double[][]> transEucldianData = EuclidianDistanceValue.getInstance().toEuclidianDistanceValue(trans);
			
			double[][] transRight = transEucldianData.get(0);
			double[][] transLeft = transEucldianData.get(1);
			double[][] right = euclidianData.get(0);
			double[][] left = euclidianData.get(1);
			
			int count=0;
			if(trans.getType() == trans_type)	{
				for(int a=0; a<5; a++)	{
					for(int b=0; b<5; b++)	{
						if( right[a][b] >= (transRight[a][b]-ERROR) && right[a][b] <= (transRight[a][b]+ERROR) )	{
							count++;
						}
						if( left[a][b] >= (transLeft[a][b]-ERROR) && left[a][b] <= (transLeft[a][b]+ERROR) )	{
							count++;
						}
					}
				}
			}

			
			
			if(count>=THRESHOLD)	{
				try {
					LEAP_GUI.check_mutex.acquire();
					txt = trans.getContent();
					
					List<MenuList_Graphic> graphicList = MENU_LIST.getInstance().addList;
					for(int i=0; i<graphicList.size(); i++)	{
						TransHandGesture graphicTrans = graphicList.get(i).trans;
						
						if(	graphicTrans.getType() == trans_type && graphicTrans.getContent().equals(trans.getContent()) )	{
							graphicList.get(i).setBackground(Color.ORANGE);
						} else	{
							graphicList.get(i).setBackground(Color.WHITE);
						}
					}
					
					LEAP_GUI.check_mutex.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	
}
