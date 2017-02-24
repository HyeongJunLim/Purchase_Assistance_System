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



// 리스너를 상속받아 클래스 샘플리스너를 생성(립모션을 연결하고 데이터 처리하는 클래스)
public class LEAP_TRANS extends Listener	{
	Graphic graphic = Graphic.getInstance();
	TransHandGestureListModel transHandGestureListModel;
	HandGesture handGesture = HandGesture.getInstance();

	// 번역을 위한 txt 변수
	public static String txt="";
		
	// 번역율
	private final int ERROR=10;			// 에러율
	private final int THRESHOLD=49;		// 카운트수
		
	
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

	// 초기화 함수
	public void onInit(Controller controller) {
		System.out.println("TRANS 초기화");
	
		trans_type = DEFAULT_TRANS_TYPE;
		
		ListTransHandGestureService listTransHandGestureService = ListTransHandGestureService.getInstance();
		transHandGestureListModel = listTransHandGestureService.getAllTransHandGestureListModel( LOGIN.getInstance().loginUser.getUserId() );		// ID를 통해 저장돼있는 번역 제스처들 다 불러옴
		
		MENU_LIST.getInstance().setFrame(transHandGestureListModel);
	}
	// 립모션의 연결부분으로 컨트롤러가 제스처를 인식하도록 한다.
	public void onConnect(Controller controller) {
		System.out.println("TRANS 립모션 연결");
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);		// 다음으로 넘어가기위한 제스쳐 -두드리기- 허용
	}
	// 연결 종료
	public void onDisconnect(Controller controller) {
		System.out.println("TRANS 립모션 연결 종료");
	}
	// 어플리케이션 종료
	public void onExit(Controller controller) {
		MENU_LIST.getInstance().init();
		System.out.println("TRANS 어플리케이션 종료");
	}
	// 립모션으로부터 매 프레임마다 Raw데이터를 입력받아 출력하는 부분
	public void onFrame(Controller controller) {
		// 가장 최신으로 컨트롤러로부터 프레임 정보를 입력 받는다. 
		Frame frame = controller.frame();
		
		handGesture.initInstance();
		
		int i, j;
		for( Hand hand : frame.hands() ) {
			i=0;
			
			// 다음으로 넘어가기위한 과정 --------------------------------------------------------------------------------------------------------------
			if(!frame.gestures().isEmpty())	{
				if(LEAP_GUI.all_txt.length()>0 && txt.length()>0)	{
					// 번역된게 하나라도있으면
					if(hand.isRight())	{
						for(Finger thumb:hand.fingers())	{
							for(Finger index:hand.fingers())	{
								for(Finger middle:hand.fingers())	{
									for(Finger pinky:hand.fingers())	{
										for(Finger ring:hand.fingers())		{
											// ↑ 여기까진 프레임마다 찾은 손가락 Fingerlist에 넣어주는것.
											if(thumb.type()==Finger.Type.TYPE_THUMB)	{
												if(index.type()==Finger.Type.TYPE_INDEX)	{
													if(middle.type()==Finger.Type.TYPE_MIDDLE)	{
														if(pinky.type()==Finger.Type.TYPE_PINKY)	{
															if(ring.type()==Finger.Type.TYPE_RING)		{
															// ↑ 여기까진 손가락타입 일치시켜주는것
																if(thumb.isExtended()&&!index.isExtended()&&!middle.isExtended()&&!pinky.isExtended()&&!ring.isExtended())	{								
																	// 엄지만 펴져있을때
																	for(Gesture gesture:frame.gestures())	{		
																		if(gesture.type()==Type.TYPE_KEY_TAP)	{
																			trans_type++;	// 다음으로 넘어감
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
			  
				// 뼈 받아오기
				for(Bone.Type boneType : Bone.Type.values()) {
					Bone bone = finger.bone(boneType);
					
	                  /**********************************************************************
	                   * 	Type.TYPE_DISTAL			=>	첫번째마디 (손톱뼈)					*
	                   *	Type.TYPE_INTERMEDIATE		=>	두번째마디							*
	                   *	Type.TYPE_PROXIMAL			=>	세번째마디							*
	                   *	Type.TYPE_METACARPAL		=>	네번째마디 (손바닥)	=> 엄지는 없음.		*
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
			
			if(hand.isRight())	{
				// 오른손일경우
				handGesture.setRightPalmPosition(hand.palmPosition());
			} else {
				// 왼손일경우
				handGesture.setLeftPalmPosition(hand.palmPosition());
			}
		}	//for( Hand hand : frame.hands() ) 의 끝


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
