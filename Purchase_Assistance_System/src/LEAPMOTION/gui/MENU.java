package LEAPMOTION.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MENU extends Frame implements ActionListener {
	//창에 배치할 내용들을 멤버 변수로 구현
	JPanel main_panel;
	JPanel sub_panel;
	JPanel text_panel;
	
	JLabel title_label_1;							// Purchase Assistance System using Leap Motion at the cafe
	JLabel title_label_2;							// - MENU -
	
	JButton order_button;							// 주문하기 버튼
	JButton menu_edit_button;						// 메뉴편집버튼
	
	DrawImage draw;
		
	// 폰트
	int fontSize = 20;
	Font font1 = new Font("고딕", Font.BOLD, fontSize);
	Font font2 = new Font("고딕", 0, fontSize);
				
		
	public void init(){
		// 버튼, 라벨 , 텍스트필드 부착을 위한 패널
		main_panel = new JPanel();
		sub_panel = new JPanel();
		text_panel = new JPanel();
		
		// 라벨 초기화
		title_label_1 = new JLabel("Purchase Assistance System using Leap Motion at the cafe");	// 로그인 화면 라벨 등록
			title_label_1.setHorizontalAlignment(SwingConstants.CENTER);						// 수평 : 가운데 정렬
			title_label_1.setVerticalAlignment(SwingConstants.CENTER);							// 수직: 가운데 정렬
		title_label_2 = new JLabel("- MENU -");													// 로그인 화면 라벨 등록
			title_label_2.setFont(font1);														// 폰트 설정	
			title_label_2.setHorizontalAlignment(SwingConstants.CENTER);						// 수평 : 가운데 정렬
			title_label_2.setVerticalAlignment(SwingConstants.CENTER);							// 수직: 가운데 정렬
		
		// 버튼 초기화
		order_button = new JButton("주문하기");								// 주문하기 버튼
		menu_edit_button = new JButton("메뉴편집");							// 메뉴편집 버튼
		
		//Canvas 객체 생성
		draw = new DrawImage();
	}
	
	public void setFrame(){
		// 프레임 설정 ------------------------
		this.setLayout(new BorderLayout());
		this.add(title_label_1, BorderLayout.NORTH);
		this.add(main_panel, BorderLayout.CENTER);
			main_panel.setLayout(new BorderLayout());
			main_panel.add(draw, BorderLayout.CENTER);
		this.add(sub_panel, BorderLayout.SOUTH);
			sub_panel.setLayout(new GridLayout(3,1));
			sub_panel.add(title_label_2);
			sub_panel.add(order_button);
			sub_panel.add(menu_edit_button);
		//-----------------------------------
	}
	
	public void event(){
		//X 누르면 종료되는 코드
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				LOGIN.getInstance().setVisible(true);
				MENU.this.dispose();
			}
		});

		//이벤트 연결 설정
		order_button.addActionListener(this);
		menu_edit_button.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e)  {
		if(e.getSource() == order_button)	{
			new LEAP_GUI();
			this.dispose();
		} else if(e.getSource() == menu_edit_button)	{
			new MENU_EDIT();
			this.dispose();
		}
		
	}
	
	public MENU(){
		init();						// 버튼 및 라벨 등등등 초기화
		setFrame();					// 프레임 설정
		event();					// 창에 이벤트 설정을 수행하는 메소드
		
		//세부 속성 설정
		setTitle("LEAP MOTION -Translate Program-");			//제목
		setSize(400, 450);										//크기(폭, 높이)
	
		//화면 중심 계산
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = di.width/2 - this.getWidth()/2;
		int ypos = di.height/2 - this.getHeight()/2;
//		int xpos = di.width - this.getWidth();
//		int ypos = 1;
		
		//위치(왼쪽 상단)
		setLocation(xpos, ypos);
		setResizable(false);		//크기 고정
		setVisible(true);			//보여주기
	}

	class DrawImage extends Canvas
	{
		
		Image image = new ImageIcon("image/Cafe.png").getImage();
		
		public void paint(Graphics g)	{
			int xpos = this.getWidth()/2 - image.getWidth(null)/2;
			int ypos = 30;

			g.drawImage(image, xpos, ypos, null);
			g.setFont(font2);
			g.drawString("ID : " + LOGIN.getInstance().loginUser.getUserId().toString(), 0, this.getHeight()-40);
			g.drawString("지점 : " + LOGIN.getInstance().loginUser.getBranchName().toString(), 0, this.getHeight()-10);
		}
		
	}
}
