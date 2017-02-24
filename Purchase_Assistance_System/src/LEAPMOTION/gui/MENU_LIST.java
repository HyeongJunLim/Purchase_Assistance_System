package LEAPMOTION.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import MVC.model.TransHandGesture;
import MVC.model.TransHandGestureListModel;

public class MENU_LIST extends Frame	{
	private static MENU_LIST instance = new MENU_LIST();
	public static MENU_LIST getInstance()	{
		return instance;
	}
	//----------------------------------------------------------
	
	public List<MenuList_Graphic> addList = new ArrayList<MenuList_Graphic>();
	
	
	// 프레임 크기
	final int FRAME_WIDTH = 650;
	final int FRAME_HEIGHT = 800;

	// 폰트
	int fontSize = 20;
	Font font1 = new Font("고딕", Font.BOLD, fontSize);
	Font font2 = new Font("고딕", 0, fontSize);
	
	// 라벨
	JLabel type_1_label;
	JLabel type_2_label;
	JLabel type_3_label;
	JLabel type_4_label;
	
	public void init(){
		this.dispose();
		instance = null;
		instance = new MENU_LIST();
	}
		
	public void setFrame(TransHandGestureListModel transList){
		// 프레임 설정 ------------------------
		this.setLayout(new FlowLayout());
		int count = transList.getTransHandList().size();
		for(TransHandGesture trans : transList.getTransHandList())	{
			Panel temp = new Panel();
			temp.setLayout(new BorderLayout());
				switch(trans.getType())	{
					case 1:
						type_1_label = new JLabel("[메뉴]");
						type_1_label.setFont(font1);
						temp.add(type_1_label, BorderLayout.NORTH);
						break;
					case 2:
						type_2_label = new JLabel("[HOT/ICE]");
						type_2_label.setFont(font1);
						temp.add(type_2_label, BorderLayout.NORTH);
						break;
					case 3:
						type_3_label = new JLabel("[사이즈]");
						type_3_label.setFont(font1);
						temp.add(type_3_label, BorderLayout.NORTH);
						break;
					case 4:
						type_4_label = new JLabel("[수량]");
						type_4_label.setFont(font1);
						temp.add(type_4_label, BorderLayout.NORTH);
						break;
				}
				MenuList_Graphic menuListGraphic = new MenuList_Graphic(FRAME_WIDTH/3, FRAME_HEIGHT/3, trans);
				temp.add(menuListGraphic, BorderLayout.CENTER);
	
			addList.add(menuListGraphic);
			this.add(temp);
		}
		//-----------------------------------
	}
	
	public void event(){
		//X 누르면 종료되는 코드
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MENU_LIST.this.setVisible(false);
			}
		});
	}	

	public MENU_LIST(){
		event();					// 창에 이벤트 설정을 수행하는 메소드
		
		//세부 속성 설정
		setTitle("MENU LIST -Translate Program-");//제목
		setSize(FRAME_WIDTH, FRAME_HEIGHT);//크기(폭, 높이)
				
		//화면 중심 계산
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = di.width/2 + this.getWidth()/2;
		int ypos = di.height/2 - this.getHeight()/2;
		
		//위치(왼쪽 상단)
		setLocation(xpos, ypos);
		setResizable(true);			//크기 고정
		setVisible(true);			//보여주기
	}
}