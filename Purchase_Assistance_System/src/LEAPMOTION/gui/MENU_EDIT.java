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
	//â�� ��ġ�� ������� ��� ������ ����
	JPanel panel;
	
	// �ؽ�Ʈ �ʵ� ����
	JTextField content_input;					// ���� �Է� �ʵ�
	JTextField type_input;						// Ÿ�� �Է��ʵ�
	   	
	// �� ����
	JLabel title_label;							// - �߰� -
	JLabel content_label;						// ���� :
	JLabel type_label;							// Ÿ�� :
	
	// ��ư ����
	JButton add_button;							// �ֹ��ϱ� ��ư
	JButton back_button;						// �޴�������ư
	
	// �� �׷��� ĵ���� ����
	Graphic graphic;							// �׷��� 
	
	// ����� ���� ����
	LEAP_TRACK track_listener; 					// �ܼ� ���� ������
	Controller controller;						// ������ ���� ��Ʈ�ѷ�
	
	// ��Ʈ
	int fontSize = 20;
	Font font1 = new Font("���", Font.BOLD, fontSize);
	Font font2 = new Font("���", 0, fontSize);
				
		
	public void init(){
		// ��ư, �� , �ؽ�Ʈ�ʵ� ������ ���� �г�
		panel = new JPanel();

		// �� �ʱ�ȭ
		title_label = new JLabel("- �߰� -");									// �߰� ȭ�� �� ���
			title_label.setFont(font1);										// ��Ʈ ����	
			title_label.setHorizontalAlignment(SwingConstants.CENTER);		// ���� : ��� ����
			title_label.setVerticalAlignment(SwingConstants.CENTER);		// ����: ��� ����
		type_label = new JLabel("Ÿ�� :");									// Ÿ�� :
			type_label.setFont(font1);										// ��Ʈ����
			type_label.setHorizontalAlignment(SwingConstants.CENTER);		// ���� : ��� ����
			type_label.setVerticalAlignment(SwingConstants.CENTER);			// ����: ��� ����
		content_label = new JLabel("���� :");									// ���� :
			content_label.setFont(font1);									// ��Ʈ����
			content_label.setHorizontalAlignment(SwingConstants.CENTER);	// ���� : ��� ����
			content_label.setVerticalAlignment(SwingConstants.CENTER);		// ����: ��� ����
		
		// �ؽ�Ʈ �ʵ� �ʱ�ȭ
		content_input = new JTextField(10);									// �������� �Է� �ؽ�Ʈ�ʵ�
			content_input.setFont(font1);									// ��Ʈ����
		type_input = new JTextField(10);									// Ÿ���Է� �ؽ�Ʈ�ʵ�
			type_input.setFont(font1);										// ��Ʈ����
		
		// ��ư �ʱ�ȭ
		add_button = new JButton("�߰��ϱ�");									// �ֹ��ϱ� ��ư
		back_button = new JButton("�ڷΰ���");									// �޴����� ��ư

		// ĵ���� �ʱ�ȭ
		graphic = Graphic.getInstance();									// �׷��� ��ü ������
		graphic.setBackground(Color.WHITE);									// ���� ���
		
		// ����� ���� �ʱ�ȭ
		track_listener = LEAP_TRACK.getInstance();							// ��ü ��������
		controller = new Controller(track_listener);						// ��Ʈ�ѷ��� �����ʺ���
	}
		
	public void setFrame(){
		// ������ ���� ------------------------
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
		//X ������ ����Ǵ� �ڵ�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new MENU();
				MENU_EDIT.this.dispose();
			}
		});
		//�̺�Ʈ ���� ����
		 add_button.addActionListener(this);
		 back_button.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e)  {
		if(e.getSource() == add_button)	{
			if(!type_input.getText().toString().isEmpty() && !content_input.getText().toString().isEmpty())	{
				
				
				InsertTransHandGestureService.getInstance().insert( LOGIN.getInstance().loginUser.getUserId(), graphic.handGesture,
																	Integer.parseInt(type_input.getText().toString()), content_input.getText().toString() );
				
				
				
				JOptionPane.showMessageDialog(null, content_input.getText().toString()+"��(��) �߰� �Ǿ����ϴ�.", "�߰� �Ϸ�", JOptionPane.OK_CANCEL_OPTION);
			}
			else	{
				JOptionPane.showMessageDialog(null, "����� �Է����ּ���", "�Է¿���", JOptionPane.OK_CANCEL_OPTION);
			}
		} else if(e.getSource() == back_button)	{
			new MENU();
			this.dispose();
		}
	}
	
	public MENU_EDIT(){
		init();						// ��ư �� �� ���� �ʱ�ȭ
		setFrame();					// ������ ����
		event();					// â�� �̺�Ʈ ������ �����ϴ� �޼ҵ�
		
		//���� �Ӽ� ����
		setTitle("LEAP MOTION -Translate Program-");			//����
		setSize(500, 500);										//ũ��(��, ����)
	
		//ȭ�� �߽� ���
		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = di.width/2 - this.getWidth()/2;
		int ypos = di.height/2 - this.getHeight()/2;
		
		//��ġ(���� ���)
		setLocation(xpos, ypos);
		setResizable(false);		//ũ�� ����
		setVisible(true);			//�����ֱ�
	}
}