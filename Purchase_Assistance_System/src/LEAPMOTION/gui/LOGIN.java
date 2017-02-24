package LEAPMOTION.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import MVC.model.User;
import MVC.service.LoginUserService;


public class LOGIN extends Frame  implements ActionListener	{
	// 객체 생성
	private static LOGIN instance = new LOGIN();
	public static LOGIN getInstance()	{
		return instance;
	}
	
	public User loginUser=null;
	
	// 창에 배치할 내용들을 멤버 변수로 구현
	JPanel panel;
	JPanel text_panel;
	JPanel text_id_panel;
	JPanel text_pw_panel;
	JPanel button_panel;

	// 라벨 선언
	JLabel title_label_1;										// Purchase Assistance System using Leap Motion at the cafe
	JLabel title_label_2;										// - LOGIN -
	JLabel id_label;											// ID:
	JLabel pw_label;											// PW:
	
	// 텍스트필드 선언
	public JTextField id_input;									// 아이디 입력 필드
	JPasswordField pw_input;									// 패스워드 입력 필드

	// 버튼선언
	JButton login_button;										// 로그인버튼
	JButton regedit_button;										// 회원가입버튼

	DrawImage draw;
	
	// 폰트
	int fontSize = 20;
	Font font1 = new Font("고딕", Font.BOLD, fontSize);
	Font font2 = new Font("고딕", 0, fontSize);
		
	
	public void init(){
		// 버튼, 라벨 , 텍스트필드 부착을 위한 패널
		panel = new JPanel();
		text_panel = new JPanel();
			text_id_panel = new JPanel();
			text_pw_panel = new JPanel();
		button_panel = new JPanel();

		// 라벨 초기화
		title_label_1 = new JLabel("Purchase Assistance System using Leap Motion at the cafe");	// 로그인 화면 라벨 등록
			title_label_1.setHorizontalAlignment(SwingConstants.CENTER);						// 수평 : 가운데 정렬
			title_label_1.setVerticalAlignment(SwingConstants.CENTER);							// 수직: 가운데 정렬
		
			
		title_label_2 = new JLabel("- LOGIN -");							// 로그인 화면 라벨 등록
			title_label_2.setFont(font1);									// 폰트 설정	
			title_label_2.setHorizontalAlignment(SwingConstants.CENTER);	// 수평 : 가운데 정렬
			title_label_2.setVerticalAlignment(SwingConstants.CENTER);		// 수직: 가운데 정렬
		id_label = new JLabel("ID  : ");									// ID 라벨 등록
			id_label.setFont(font1);										// 폰트 설정	
			id_label.setHorizontalAlignment(SwingConstants.RIGHT);			// 수평 : 오른쪽 정렬
			id_label.setVerticalAlignment(SwingConstants.CENTER);			// 수직: 가운데 정렬
		pw_label = new JLabel("PW: ");										// PW 라벨 등록
			pw_label.setFont(font1);										// 폰트설정
			id_label.setHorizontalAlignment(SwingConstants.RIGHT);			// 수평 : 오른쪽 정렬
			id_label.setVerticalAlignment(SwingConstants.CENTER);			// 수직: 가운데 정렬
		
		// 텍스트 필드 초기화
		id_input = new JTextField(15);										// ID 텍스트 필드 등록
			id_input.setFont(font2);										// 폰트설정
		pw_input = new JPasswordField(15);									// PW 텍스트 필드 등록
			pw_input.setFont(font2);										// 폰트설정
			pw_input.setEchoChar('●');										// PW 타입 설정 

		// 버튼 초기화
		login_button = new JButton("로그인");									// 로그인 버튼 등록
		regedit_button = new JButton("회원가입");								// 회원가입 버튼 등록
		
		//Canvas 객체 생성
		draw = new DrawImage();
	}
	
	public void setFrame(){
		// 프레임 설정 ------------------------
		this.setLayout(new BorderLayout());
		this.add(title_label_1, BorderLayout.NORTH);
		this.add(draw, BorderLayout.CENTER);
		this.add(panel, BorderLayout.SOUTH);
			panel.setLayout(new BorderLayout());
			panel.add(title_label_2, BorderLayout.NORTH);
			panel.add(text_panel, BorderLayout.CENTER);
				text_panel.setLayout(new GridLayout(2,1));
				text_panel.add(text_id_panel);
					text_id_panel.add(id_label);
					text_id_panel.add(id_input);
				text_panel.add(text_pw_panel);
					text_pw_panel.add(pw_label);
					text_pw_panel.add(pw_input);
			panel.add(button_panel, BorderLayout.SOUTH);
				button_panel.add(login_button);
				button_panel.add(regedit_button);
		//-----------------------------------
	}

	public void event(){
		//X 누르면 종료되는 코드
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//이벤트 연결 설정
		login_button.addActionListener(this);
		regedit_button.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		if(e.getSource() == login_button)	{
			// 로그인 버튼 눌렸을경우

			if( !id_input.getText().isEmpty() && !pw_input.getText().isEmpty() )	{
				//아이디 비밀번호 확인 true? -> DB
				loginUser = LoginUserService.getInstance().login( id_input.getText().toString(), pw_input.getText().toString() );
				if( loginUser == null )	{
					JOptionPane.showMessageDialog(null, "아이디/비밀번호 확인해주세요", "실패", JOptionPane.OK_CANCEL_OPTION);
					return;
				}
				new MENU();
				this.setVisible(false);;			// 로그인 화면 꺼짐	
			} else	{
				JOptionPane.showMessageDialog(null, "아이디/비밀번호를 입력해주세요", "입력오류", JOptionPane.OK_CANCEL_OPTION);
			}
			
		}
		else if(e.getSource() == regedit_button)	{
			// 회원가입 버튼 눌렸을경우
			new REGEDIT();
		}
	}
	
	public LOGIN(){
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
		setResizable(false);//크기 고정
		setVisible(true);//보여주기
	}

	class DrawImage extends Canvas
	{
		
		Image image = new ImageIcon("image/Cafe.png").getImage();
		
		public void paint(Graphics g)	{
			int xpos = this.getWidth()/2 - image.getWidth(null)/2;
			int ypos = 20;
			g.drawImage(image, xpos, ypos, null);
		}
		
	}
}

