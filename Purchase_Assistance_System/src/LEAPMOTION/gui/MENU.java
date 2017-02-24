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
	//â�� ��ġ�� ������� ��� ������ ����
	JPanel main_panel;
	JPanel sub_panel;
	JPanel text_panel;
	
	JLabel title_label_1;							// Purchase Assistance System using Leap Motion at the cafe
	JLabel title_label_2;							// - MENU -
	
	JButton order_button;							// �ֹ��ϱ� ��ư
	JButton menu_edit_button;						// �޴�������ư
	
	DrawImage draw;
		
	// ��Ʈ
	int fontSize = 20;
	Font font1 = new Font("���", Font.BOLD, fontSize);
	Font font2 = new Font("���", 0, fontSize);
				
		
	public void init(){
		// ��ư, �� , �ؽ�Ʈ�ʵ� ������ ���� �г�
		main_panel = new JPanel();
		sub_panel = new JPanel();
		text_panel = new JPanel();
		
		// �� �ʱ�ȭ
		title_label_1 = new JLabel("Purchase Assistance System using Leap Motion at the cafe");	// �α��� ȭ�� �� ���
			title_label_1.setHorizontalAlignment(SwingConstants.CENTER);						// ���� : ��� ����
			title_label_1.setVerticalAlignment(SwingConstants.CENTER);							// ����: ��� ����
		title_label_2 = new JLabel("- MENU -");													// �α��� ȭ�� �� ���
			title_label_2.setFont(font1);														// ��Ʈ ����	
			title_label_2.setHorizontalAlignment(SwingConstants.CENTER);						// ���� : ��� ����
			title_label_2.setVerticalAlignment(SwingConstants.CENTER);							// ����: ��� ����
		
		// ��ư �ʱ�ȭ
		order_button = new JButton("�ֹ��ϱ�");								// �ֹ��ϱ� ��ư
		menu_edit_button = new JButton("�޴�����");							// �޴����� ��ư
		
		//Canvas ��ü ����
		draw = new DrawImage();
	}
	
	public void setFrame(){
		// ������ ���� ------------------------
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
		//X ������ ����Ǵ� �ڵ�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				LOGIN.getInstance().setVisible(true);
				MENU.this.dispose();
			}
		});

		//�̺�Ʈ ���� ����
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
		init();						// ��ư �� �� ���� �ʱ�ȭ
		setFrame();					// ������ ����
		event();					// â�� �̺�Ʈ ������ �����ϴ� �޼ҵ�
		
		//���� �Ӽ� ����
		setTitle("LEAP MOTION -Translate Program-");			//����
		setSize(400, 450);										//ũ��(��, ����)
	
		//ȭ�� �߽� ���
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = di.width/2 - this.getWidth()/2;
		int ypos = di.height/2 - this.getHeight()/2;
//		int xpos = di.width - this.getWidth();
//		int ypos = 1;
		
		//��ġ(���� ���)
		setLocation(xpos, ypos);
		setResizable(false);		//ũ�� ����
		setVisible(true);			//�����ֱ�
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
			g.drawString("���� : " + LOGIN.getInstance().loginUser.getBranchName().toString(), 0, this.getHeight()-10);
		}
		
	}
}
