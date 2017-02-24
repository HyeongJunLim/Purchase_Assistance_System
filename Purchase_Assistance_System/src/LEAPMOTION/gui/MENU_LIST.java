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
	
	
	// ������ ũ��
	final int FRAME_WIDTH = 650;
	final int FRAME_HEIGHT = 800;

	// ��Ʈ
	int fontSize = 20;
	Font font1 = new Font("���", Font.BOLD, fontSize);
	Font font2 = new Font("���", 0, fontSize);
	
	// ��
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
		// ������ ���� ------------------------
		this.setLayout(new FlowLayout());
		int count = transList.getTransHandList().size();
		for(TransHandGesture trans : transList.getTransHandList())	{
			Panel temp = new Panel();
			temp.setLayout(new BorderLayout());
				switch(trans.getType())	{
					case 1:
						type_1_label = new JLabel("[�޴�]");
						type_1_label.setFont(font1);
						temp.add(type_1_label, BorderLayout.NORTH);
						break;
					case 2:
						type_2_label = new JLabel("[HOT/ICE]");
						type_2_label.setFont(font1);
						temp.add(type_2_label, BorderLayout.NORTH);
						break;
					case 3:
						type_3_label = new JLabel("[������]");
						type_3_label.setFont(font1);
						temp.add(type_3_label, BorderLayout.NORTH);
						break;
					case 4:
						type_4_label = new JLabel("[����]");
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
		//X ������ ����Ǵ� �ڵ�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				MENU_LIST.this.setVisible(false);
			}
		});
	}	

	public MENU_LIST(){
		event();					// â�� �̺�Ʈ ������ �����ϴ� �޼ҵ�
		
		//���� �Ӽ� ����
		setTitle("MENU LIST -Translate Program-");//����
		setSize(FRAME_WIDTH, FRAME_HEIGHT);//ũ��(��, ����)
				
		//ȭ�� �߽� ���
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = di.width/2 + this.getWidth()/2;
		int ypos = di.height/2 - this.getHeight()/2;
		
		//��ġ(���� ���)
		setLocation(xpos, ypos);
		setResizable(true);			//ũ�� ����
		setVisible(true);			//�����ֱ�
	}
}