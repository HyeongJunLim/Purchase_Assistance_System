package LEAPMOTION.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.leapmotion.leap.Controller;

import LEAPMOTION.listener.LEAP_TRACK;
import MVC.service.InsertTransHandGestureService;

public class MENU_EDIT extends JFrame implements ActionListener{
	//창에 배치할 내용들을 멤버 변수로 구현
	JPanel panel;
	
	// 텍스트 필드 선언
	JTextField content_input;					// 내용 입력 필드
	JTextField type_input;						// 타입 입력필드
	   	
	// 라벨 선언
	JLabel title_label;							// - 추가 -
	JLabel content_label;						// 내용 :
	JLabel type_label;							// 타입 :
	
	// 버튼 선언
	JButton add_button;							// 주문하기 버튼
	JButton back_button;						// 메뉴편집버튼
	
	// 손 그려줄 캔버스 선언
	Graphic graphic;							// 그래픽 
	
	// 립모션 관련 선언
	LEAP_TRACK track_listener; 					// 단순 추적 리스너
	Controller controller;						// 리스너 붙일 컨트롤러
	
	// 폰트
	int fontSize = 20;
	Font font1 = new Font("고딕", Font.BOLD, fontSize);
	Font font2 = new Font("고딕", 0, fontSize);
				
		
	public void init(){
		// 버튼, 라벨 , 텍스트필드 부착을 위한 패널
		panel = new JPanel();

		// 라벨 초기화
		title_label = new JLabel("- 추가 -");									// 추가 화면 라벨 등록
			title_label.setFont(font1);										// 폰트 설정	
			title_label.setHorizontalAlignment(SwingConstants.CENTER);		// 수평 : 가운데 정렬
			title_label.setVerticalAlignment(SwingConstants.CENTER);		// 수직: 가운데 정렬
		type_label = new JLabel("타입 :");									// 타입 :
			type_label.setFont(font1);										// 폰트설정
			type_label.setHorizontalAlignment(SwingConstants.CENTER);		// 수평 : 가운데 정렬
			type_label.setVerticalAlignment(SwingConstants.CENTER);			// 수직: 가운데 정렬
		content_label = new JLabel("내용 :");									// 내용 :
			content_label.setFont(font1);									// 폰트설정
			content_label.setHorizontalAlignment(SwingConstants.CENTER);	// 수평 : 가운데 정렬
			content_label.setVerticalAlignment(SwingConstants.CENTER);		// 수직: 가운데 정렬
		
		// 텍스트 필드 초기화
		content_input = new JTextField(10);									// 번역내용 입력 텍스트필드
			content_input.setFont(font1);									// 폰트설정
		type_input = new JTextField(10);									// 타입입력 텍스트필드
			type_input.setFont(font1);										// 폰트설정
		
		// 버튼 초기화
		add_button = new JButton("추가하기");									// 주문하기 버튼
		back_button = new JButton("뒤로가기");									// 메뉴편집 버튼

		// 캔버스 초기화
		graphic = Graphic.getInstance();									// 그래픽 객체 가져옴
		graphic.setBackground(Color.WHITE);									// 배경색 흰색
		
		// 립모션 관련 초기화
		track_listener = LEAP_TRACK.getInstance();							// 객체 가져오기
		controller = new Controller(track_listener);						// 컨트롤러에 리스너부착
	}
		
	public void setFrame(){
		// 프레임 설정 ------------------------
		this.setLayout(new BorderLayout());
		this.add(title_label, BorderLayout.NORTH);
		this.add(graphic, BorderLayout.CENTER);
		this.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new GridLayout(3,2));
			panel.add(type_label);
			panel.add(type_input);
			panel.add(content_label);
			panel.add(content_input);
			panel.add(add_button);
			panel.add(back_button);
		//-----------------------------------
	}
	
	public void event(){
		//X 누르면 종료되는 코드
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new MENU();
				MENU_EDIT.this.dispose();
			}
		});
		//이벤트 연결 설정
		 add_button.addActionListener(this);
		 back_button.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e)  {
		if(e.getSource() == add_button)	{
			if(!type_input.getText().toString().isEmpty() && !content_input.getText().toString().isEmpty())	{
				
				
				InsertTransHandGestureService.getInstance().insert( LOGIN.getInstance().loginUser.getUserId(), graphic.handGesture,
																	Integer.parseInt(type_input.getText().toString()), content_input.getText().toString() );
				
				
				
				JOptionPane.showMessageDialog(null, content_input.getText().toString()+"가(이) 추가 되었습니다.", "추가 완료", JOptionPane.OK_CANCEL_OPTION);
			}
			else	{
				JOptionPane.showMessageDialog(null, "제대로 입력해주세요", "입력오류", JOptionPane.OK_CANCEL_OPTION);
			}
		} else if(e.getSource() == back_button)	{
			new MENU();
			this.dispose();
		}
	}
	
	public MENU_EDIT(){
		init();						// 버튼 및 라벨 등등등 초기화
		setFrame();					// 프레임 설정
		event();					// 창에 이벤트 설정을 수행하는 메소드
		
		//세부 속성 설정
		setTitle("LEAP MOTION -Translate Program-");			//제목
		setSize(500, 500);										//크기(폭, 높이)
	
		//화면 중심 계산
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = di.width/2 - this.getWidth()/2;
		int ypos = di.height/2 - this.getHeight()/2;
		
		//위치(왼쪽 상단)
		setLocation(xpos, ypos);
		setResizable(false);		//크기 고정
		setVisible(true);			//보여주기
	}
}