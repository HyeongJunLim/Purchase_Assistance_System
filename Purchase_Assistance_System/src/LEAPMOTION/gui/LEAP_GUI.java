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
	// 립모션 컨트롤러와 컨트롤러의 데이터를 읽어들이는 리스너를 생성	
	LEAP_TRACK track_listener = LEAP_TRACK.getInstance();			// 단순 추적 리스너
	LEAP_TRANS trans_listener = LEAP_TRANS.getInstance();			// 메뉴 트랜스 리스너
	
	public static Controller controller = new Controller(); 
		
	
	// 뮤텍스
	public static final Semaphore check_mutex = new Semaphore(1);	
	
	// 텍스트 변수
	public static String all_txt="";			// text_area를 위한 스트링 변수
	public static String temp="";				// 중복검출을 위한 스트링 변수

	// 처음시작버튼인지 pause후 재시작버튼인지 확인
	private boolean isFirstStart = true;
	public boolean isFirstStart() {
		return isFirstStart;
	}
	public void setFirstStart(boolean isFirstStart) {
		this.isFirstStart = isFirstStart;
	}
	

	// 쓰레드 이용 크래스
	CHECK_THREAD thread = new CHECK_THREAD();	
	
	// 폰트
	int fontSize = 15;
	Font font = new Font("Courier New", Font.BOLD, fontSize);
		

	
	// 창에 배치할 내용들을 멤버 변수로 구현
	// 그래픽구현 Graphic
	Graphic disp_Canvas = Graphic.getInstance();
	
	//패널 4개
	private Panel main_panel = new Panel();
		private Panel btn_panel = new Panel();
			private Panel sub_btn_panel = new Panel();
		private Panel clock_panel = new Panel();
		private Panel text_panel = new Panel();
	
	//MenuBar : 메뉴를 등록할 수 있는 공간
	private MenuBar bar = new MenuBar();
	
	//Menu : 하위 집합을 가지고 있는 항목
	//MenuItem : 하위 집합이 없는 항목
	private Menu menu = new Menu("메뉴");
		private MenuItem program_exit  = new MenuItem("프로그램 종료");
	
	private Menu setting = new Menu("설정...");
		private MenuItem font_size_up = new MenuItem("폰트 크기 크게");
		private MenuItem font_size_down = new MenuItem("폰트 크기 작게");
	
	private Menu option = new Menu("옵션");
	
	private CheckboxMenuItem menu_list = new CheckboxMenuItem("메뉴리스트 보기");
	//--------------------- ↑ 메뉴  ----------------------
	
	// 텍스트 에어리어
	public static TextArea textarea = new TextArea();
		
	// 버튼
	private JButton btn_translate_start = new JButton("Translate Start");
	private JButton btn_translate_pause = new JButton("Translate Pause");
	private JButton btn_back = new JButton("Back");
	private JButton btn_clear = new JButton("Clear");
	
	// translate_start와 translate_pause를 위한 카드레이아웃
	public CardLayout card = new CardLayout();
	
	void setCardLayout()	{
		sub_btn_panel.add(btn_translate_start, "start");
		sub_btn_panel.add(btn_translate_pause, "pause");
		
		card.show(sub_btn_panel, "start");
	}
	void setDisplay()	{
		// 화면 구성 ------------------------
		this.setLayout(new BorderLayout());
		
		this.add(disp_Canvas, BorderLayout.CENTER);
		
		this.add(main_panel, BorderLayout.SOUTH);
		main_panel.setLayout(new BorderLayout());
		main_panel.add(text_panel, BorderLayout.CENTER);
			text_panel.add(textarea);
			textarea.setEditable(false);		// 텍스트에어리어 클릭 X
		main_panel.add(btn_panel, BorderLayout.EAST);
			btn_panel.setLayout(new GridLayout(3, 1)); 
				btn_panel.add(sub_btn_panel);
					sub_btn_panel.setLayout(card);
					setCardLayout();			// 카드레이아웃
				btn_panel.add(btn_back);
				btn_panel.add(btn_clear);
		main_panel.add(clock_panel, BorderLayout.SOUTH);
				clock_panel.setBackground(Color.gray);	// 시계가 들어갈 예정 ***************
		//-----------------------------------
	}
	void menu()	{
		// 메뉴 설정 ----------------------------
		this.setMenuBar(bar);
		
		bar.add(menu);
			menu.add(setting);
				setting.add(font_size_up);		// 폰트 크기 증가
				setting.add(font_size_down);	// 폰트 크기 감소
			menu.addSeparator();				//구분선 추가
			menu.add(program_exit);
		bar.add(option);
			option.add(menu_list);				// 메뉴리스트 출력
		//-----------------------------------

	}
	void init()	{
		// 쓰레드 시작
		thread.start();
		
		// 텍스트 에어리어 초기화
		textarea.setFont(font);
	    textarea.setText(all_txt);
	    
	    // 컨트롤러로부터 리스너를 추가하여 데이터(제스처, 손)을 읽어들임
		controller.addListener(track_listener);
	}
	void event()	{
		//X 누르면 종료되는 코드
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// 종료되면서 컨트롤러에서 리스너를 제거
				controller.removeListener(track_listener);
				controller.removeListener(trans_listener);
		
				System.exit(0);
			}
		});
		
		//이벤트 연결 설정
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
				trans_listener.trans_type_plus();	// 메뉴선택으로 타입값 증가
			}
		}
		else if(e.getSource() == btn_translate_pause)	{
			// 일시정지 버튼
			card.show(sub_btn_panel, "start");
			controller.removeListener(trans_listener);
			controller.addListener(track_listener);
			
			thread.setFlag(true);
			isFirstStart = false;					// 재시작이라 정의
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
			// 종료되면서 컨트롤러에서 리스너를 제거
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
		setDisplay();	// 화면구성
		init();			// 초기화
		menu();			// 메뉴 설정과 관련된 메소드
		event();		// 창에 이벤트 설정을 수행하는 메소드
		
		//세부 속성 설정
		setTitle("LEAP MOTION -Translate Program-");//제목
		setSize(650, 800);//크기(폭, 높이)
		
		//화면 중심 계산
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = di.width/2 - this.getWidth()/2;
		int ypos = di.height/2 - this.getHeight()/2;
		
		//위치(왼쪽 상단)
		setLocation(xpos, ypos);
		setResizable(false);		//크기 고정
		setVisible(true);			//보여주기
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
							// 기본
							num_flag = true;	// 이전 플래그는 true로 다시 초기화
						    
//							card.show(sub_btn_panel, "start");
//							controller.removeListener(trans_listener);
//							controller.addListener(track_listener);

							isFirstStart = true;					// 재시작이라 정의
						    
							break;
						case 1:
							// 메뉴
							if(menu_flag)	{
								//trans_listener.trans_txt="";	// 이전 모드의 txt 버퍼 초기화
							    
								all_txt = ("<메뉴 선택> : ");
								textarea.setText(all_txt);
								menu_flag = false;	// 한번만 사용하도록
							}
							break;
						case 2:
							// 타입
							if(type_flag)	{
								//trans_listener.trans_txt="";	// 이전 모드의 txt 버퍼 초기화
						        
								all_txt += ("<HOT/ICE 선택> : ");
								textarea.setText(all_txt);
								type_flag = false;	// 한번만 사용하도록
						        menu_flag = true;	// 이전 플래그는 true로 다시 초기화
							}
							break;
						case 3:
							// 사이즈
							if(size_flag)	{
								//trans_listener.transTxt="";	// 이전 모드의 txt 버퍼 초기화
						        
								all_txt += ("<사이즈 선택> : ");
								textarea.setText(all_txt);
								size_flag = false;	// 한번만 사용하도록
						        type_flag = true;	// 이전 플래그는 true로 다시 초기화
							}
							break;
						case 4:
							// 수량
							if(num_flag)	{
								//trans_listener.trans_txt="";	// 이전 모드의 txt 버퍼 초기화
						        
								all_txt += ("<수량 선택> : ");
								textarea.setText(all_txt);
								num_flag = false;	// 한번만 사용하도록
						        size_flag = true;	// 이전 플래그는 true로 다시 초기화
							}
							break;
					}
					try {
						if( LEAP_TRANS.txt.length()>0 && !LEAP_TRANS.txt.equals(temp) )	{
							check_mutex.acquire();
							
							// LEAP_MENU_TRANS.txt에 내용이 입력되었고, 이전과 같은 내용이 아닐때!! 
							
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
				}	// while(!flag)의 끝
			}	// while(true)의 끝
		}
	}
}
