package LEAPMOTION.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;

import com.leapmotion.leap.Controller;

import LEAPMOTION.listener.LEAP_TRACK;
import LEAPMOTION.listener.LEAP_TRANS;




public class LEAP_GUI extends Frame implements ActionListener, ItemListener {
	// ����� ��Ʈ�ѷ��� ��Ʈ�ѷ��� �����͸� �о���̴� �����ʸ� ����	
	LEAP_TRACK track_listener = LEAP_TRACK.getInstance();			// �ܼ� ���� ������
	LEAP_TRANS trans_listener = LEAP_TRANS.getInstance();			// �޴� Ʈ���� ������
	
	public static Controller controller = new Controller(); 
		
	
	// ���ؽ�
	public static final Semaphore check_mutex = new Semaphore(1);	
	
	// �ؽ�Ʈ ����
	public static String all_txt="";			// text_area�� ���� ��Ʈ�� ����
	public static String temp="";				// �ߺ������� ���� ��Ʈ�� ����

	// ó�����۹�ư���� pause�� ����۹�ư���� Ȯ��
	private boolean isFirstStart = true;
	public boolean isFirstStart() {
		return isFirstStart;
	}
	public void setFirstStart(boolean isFirstStart) {
		this.isFirstStart = isFirstStart;
	}
	

	// ������ �̿� ũ����
	CHECK_THREAD thread = new CHECK_THREAD();	
	
	// ��Ʈ
	int fontSize = 15;
	Font font = new Font("Courier New", Font.BOLD, fontSize);
		

	
	// â�� ��ġ�� ������� ��� ������ ����
	// �׷��ȱ��� Graphic
	Graphic disp_Canvas = Graphic.getInstance();
	
	//�г� 4��
	private Panel main_panel = new Panel();
		private Panel btn_panel = new Panel();
			private Panel sub_btn_panel = new Panel();
		private Panel clock_panel = new Panel();
		private Panel text_panel = new Panel();
	
	//MenuBar : �޴��� ����� �� �ִ� ����
	private MenuBar bar = new MenuBar();
	
	//Menu : ���� ������ ������ �ִ� �׸�
	//MenuItem : ���� ������ ���� �׸�
	private Menu menu = new Menu("�޴�");
		private MenuItem program_exit  = new MenuItem("���α׷� ����");
	
	private Menu setting = new Menu("����...");
		private MenuItem font_size_up = new MenuItem("��Ʈ ũ�� ũ��");
		private MenuItem font_size_down = new MenuItem("��Ʈ ũ�� �۰�");
	
	private Menu option = new Menu("�ɼ�");
	
	private CheckboxMenuItem menu_list = new CheckboxMenuItem("�޴�����Ʈ ����");
	//--------------------- �� �޴�  ----------------------
	
	// �ؽ�Ʈ �����
	public static TextArea textarea = new TextArea();
		
	// ��ư
	private JButton btn_translate_start = new JButton("Translate Start");
	private JButton btn_translate_pause = new JButton("Translate Pause");
	private JButton btn_back = new JButton("Back");
	private JButton btn_clear = new JButton("Clear");
	
	// translate_start�� translate_pause�� ���� ī�巹�̾ƿ�
	public CardLayout card = new CardLayout();
	
	void setCardLayout()	{
		sub_btn_panel.add(btn_translate_start, "start");
		sub_btn_panel.add(btn_translate_pause, "pause");
		
		card.show(sub_btn_panel, "start");
	}
	void setDisplay()	{
		// ȭ�� ���� ------------------------
		this.setLayout(new BorderLayout());
		
		this.add(disp_Canvas, BorderLayout.CENTER);
		
		this.add(main_panel, BorderLayout.SOUTH);
		main_panel.setLayout(new BorderLayout());
		main_panel.add(text_panel, BorderLayout.CENTER);
			text_panel.add(textarea);
			textarea.setEditable(false);		// �ؽ�Ʈ����� Ŭ�� X
		main_panel.add(btn_panel, BorderLayout.EAST);
			btn_panel.setLayout(new GridLayout(3, 1)); 
				btn_panel.add(sub_btn_panel);
					sub_btn_panel.setLayout(card);
					setCardLayout();			// ī�巹�̾ƿ�
				btn_panel.add(btn_back);
				btn_panel.add(btn_clear);
		main_panel.add(clock_panel, BorderLayout.SOUTH);
				clock_panel.setBackground(Color.gray);	// �ð谡 �� ���� ***************
		//-----------------------------------
	}
	void menu()	{
		// �޴� ���� ----------------------------
		this.setMenuBar(bar);
		
		bar.add(menu);
			menu.add(setting);
				setting.add(font_size_up);		// ��Ʈ ũ�� ����
				setting.add(font_size_down);	// ��Ʈ ũ�� ����
			menu.addSeparator();				//���м� �߰�
			menu.add(program_exit);
		bar.add(option);
			option.add(menu_list);				// �޴�����Ʈ ���
		//-----------------------------------

	}
	void init()	{
		// ������ ����
		thread.start();
		
		// �ؽ�Ʈ ����� �ʱ�ȭ
		textarea.setFont(font);
	    textarea.setText(all_txt);
	    
	    // ��Ʈ�ѷ��κ��� �����ʸ� �߰��Ͽ� ������(����ó, ��)�� �о����
		controller.addListener(track_listener);
	}
	void event()	{
		//X ������ ����Ǵ� �ڵ�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// ����Ǹ鼭 ��Ʈ�ѷ����� �����ʸ� ����
				controller.removeListener(track_listener);
				controller.removeListener(trans_listener);
		
				System.exit(0);
			}
		});
		
		//�̺�Ʈ ���� ����
		program_exit.addActionListener(this);
		btn_translate_start.addActionListener(this);
		btn_translate_pause.addActionListener(this);
		btn_clear.addActionListener(this);

		font_size_up.addActionListener(this);
		font_size_down.addActionListener(this);
		menu_list.addItemListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_translate_start)	{
			card.show(sub_btn_panel, "pause");
			controller.removeListener(track_listener);
			controller.addListener(trans_listener);
			thread.setFlag(false);
			
			if(isFirstStart)	{
				trans_listener.trans_type_plus();	// �޴��������� Ÿ�԰� ����
			}
		}
		else if(e.getSource() == btn_translate_pause)	{
			// �Ͻ����� ��ư
			card.show(sub_btn_panel, "start");
			controller.removeListener(trans_listener);
			controller.addListener(track_listener);
			
			thread.setFlag(true);
			isFirstStart = false;					// ������̶� ����
		}
	    else if(e.getSource() == btn_back)   {
	    	new MENU();
	    	this.dispose();
	    }
	    else if(e.getSource() == btn_clear)   {
	    	textarea.setText("");
	    	textarea.setText(all_txt);
	    }
		else if(e.getSource() == font_size_up)	{
			fontSize++;	fontSize++;
			font = new Font("Courier New",Font.BOLD, fontSize);
			textarea.setFont(font);
		}
		else if(e.getSource() == font_size_down)	{
			fontSize--;	fontSize--;
			font = new Font("Courier New",Font.BOLD, fontSize);
			textarea.setFont(font);
		
		}
		else if(e.getSource() == program_exit)	{
			// ����Ǹ鼭 ��Ʈ�ѷ����� �����ʸ� ����
			controller.removeListener(track_listener);
			controller.removeListener(trans_listener);

			this.dispose();
			MENU_LIST.getInstance().dispose();
			MENU_LIST.getInstance().init();
			MENU_LIST.getInstance().dispose();
			new MENU();
		}
	}


	public void itemStateChanged(ItemEvent item) {
		if(item.getStateChange() == ItemEvent.SELECTED)	{
			MENU_LIST.getInstance().setVisible(true);
		} else	{
			MENU_LIST.getInstance().setVisible(false);
		}
	}
	
	
	public LEAP_GUI() {
		setDisplay();	// ȭ�鱸��
		init();			// �ʱ�ȭ
		menu();			// �޴� ������ ���õ� �޼ҵ�
		event();		// â�� �̺�Ʈ ������ �����ϴ� �޼ҵ�
		
		//���� �Ӽ� ����
		setTitle("LEAP MOTION -Translate Program-");//����
		setSize(650, 800);//ũ��(��, ����)
		
		//ȭ�� �߽� ���
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = di.width/2 - this.getWidth()/2;
		int ypos = di.height/2 - this.getHeight()/2;
		
		//��ġ(���� ���)
		setLocation(xpos, ypos);
		setResizable(false);		//ũ�� ����
		setVisible(true);			//�����ֱ�
	}
	
	
	public class CHECK_THREAD extends Thread {
		private boolean flag = false;
		public boolean getFlag() {
			return flag;
		}
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
		
		
		private boolean menu_flag = true;
		private boolean type_flag = true;
		private boolean size_flag = true;
		private boolean num_flag = true;
		
		public void run() {
			while(true)	{
				while(!flag)	{
					switch(trans_listener.trans_type)	{
						case 0:
							// �⺻
							num_flag = true;	// ���� �÷��״� true�� �ٽ� �ʱ�ȭ
						    
//							card.show(sub_btn_panel, "start");
//							controller.removeListener(trans_listener);
//							controller.addListener(track_listener);

							isFirstStart = true;					// ������̶� ����
						    
							break;
						case 1:
							// �޴�
							if(menu_flag)	{
								//trans_listener.trans_txt="";	// ���� ����� txt ���� �ʱ�ȭ
							    
								all_txt = ("<�޴� ����> : ");
								textarea.setText(all_txt);
								menu_flag = false;	// �ѹ��� ����ϵ���
							}
							break;
						case 2:
							// Ÿ��
							if(type_flag)	{
								//trans_listener.trans_txt="";	// ���� ����� txt ���� �ʱ�ȭ
						        
								all_txt += ("<HOT/ICE ����> : ");
								textarea.setText(all_txt);
								type_flag = false;	// �ѹ��� ����ϵ���
						        menu_flag = true;	// ���� �÷��״� true�� �ٽ� �ʱ�ȭ
							}
							break;
						case 3:
							// ������
							if(size_flag)	{
								//trans_listener.transTxt="";	// ���� ����� txt ���� �ʱ�ȭ
						        
								all_txt += ("<������ ����> : ");
								textarea.setText(all_txt);
								size_flag = false;	// �ѹ��� ����ϵ���
						        type_flag = true;	// ���� �÷��״� true�� �ٽ� �ʱ�ȭ
							}
							break;
						case 4:
							// ����
							if(num_flag)	{
								//trans_listener.trans_txt="";	// ���� ����� txt ���� �ʱ�ȭ
						        
								all_txt += ("<���� ����> : ");
								textarea.setText(all_txt);
								num_flag = false;	// �ѹ��� ����ϵ���
						        size_flag = true;	// ���� �÷��״� true�� �ٽ� �ʱ�ȭ
							}
							break;
					}
					try {
						if( LEAP_TRANS.txt.length()>0 && !LEAP_TRANS.txt.equals(temp) )	{
							check_mutex.acquire();
							
							// LEAP_MENU_TRANS.txt�� ������ �ԷµǾ���, ������ ���� ������ �ƴҶ�!! 
							
							temp = LEAP_TRANS.txt;
							
							all_txt += (LEAP_TRANS.txt + "\n");
							textarea.setText(all_txt);
							LEAP_TRANS.txt="";
							check_mutex.release();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	// while(!flag)�� ��
			}	// while(true)�� ��
		}
	}
}
