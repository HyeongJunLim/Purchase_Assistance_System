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
	// ��ü ����
	private static LOGIN instance = new LOGIN();
	public static LOGIN getInstance()	{
		return instance;
	}
	
	public User loginUser=null;
	
	// â�� ��ġ�� ������� ��� ������ ����
	JPanel panel;
	JPanel text_panel;
	JPanel text_id_panel;
	JPanel text_pw_panel;
	JPanel button_panel;

	// �� ����
	JLabel title_label_1;										// Purchase Assistance System using Leap Motion at the cafe
	JLabel title_label_2;										// - LOGIN -
	JLabel id_label;											// ID:
	JLabel pw_label;											// PW:
	
	// �ؽ�Ʈ�ʵ� ����
	public JTextField id_input;									// ���̵� �Է� �ʵ�
	JPasswordField pw_input;									// �н����� �Է� �ʵ�

	// ��ư����
	JButton login_button;										// �α��ι�ư
	JButton regedit_button;										// ȸ�����Թ�ư

	DrawImage draw;
	
	// ��Ʈ
	int fontSize = 20;
	Font font1 = new Font("���", Font.BOLD, fontSize);
	Font font2 = new Font("���", 0, fontSize);
		
	
	public void init(){
		// ��ư, �� , �ؽ�Ʈ�ʵ� ������ ���� �г�
		panel = new JPanel();
		text_panel = new JPanel();
			text_id_panel = new JPanel();
			text_pw_panel = new JPanel();
		button_panel = new JPanel();

		// �� �ʱ�ȭ
		title_label_1 = new JLabel("Purchase Assistance System using Leap Motion at the cafe");	// �α��� ȭ�� �� ���
			title_label_1.setHorizontalAlignment(SwingConstants.CENTER);						// ���� : ��� ����
			title_label_1.setVerticalAlignment(SwingConstants.CENTER);							// ����: ��� ����
		
			
		title_label_2 = new JLabel("- LOGIN -");							// �α��� ȭ�� �� ���
			title_label_2.setFont(font1);									// ��Ʈ ����	
			title_label_2.setHorizontalAlignment(SwingConstants.CENTER);	// ���� : ��� ����
			title_label_2.setVerticalAlignment(SwingConstants.CENTER);		// ����: ��� ����
		id_label = new JLabel("ID  : ");									// ID �� ���
			id_label.setFont(font1);										// ��Ʈ ����	
			id_label.setHorizontalAlignment(SwingConstants.RIGHT);			// ���� : ������ ����
			id_label.setVerticalAlignment(SwingConstants.CENTER);			// ����: ��� ����
		pw_label = new JLabel("PW: ");										// PW �� ���
			pw_label.setFont(font1);										// ��Ʈ����
			id_label.setHorizontalAlignment(SwingConstants.RIGHT);			// ���� : ������ ����
			id_label.setVerticalAlignment(SwingConstants.CENTER);			// ����: ��� ����
		
		// �ؽ�Ʈ �ʵ� �ʱ�ȭ
		id_input = new JTextField(15);										// ID �ؽ�Ʈ �ʵ� ���
			id_input.setFont(font2);										// ��Ʈ����
		pw_input = new JPasswordField(15);									// PW �ؽ�Ʈ �ʵ� ���
			pw_input.setFont(font2);										// ��Ʈ����
			pw_input.setEchoChar('��');										// PW Ÿ�� ���� 

		// ��ư �ʱ�ȭ
		login_button = new JButton("�α���");									// �α��� ��ư ���
		regedit_button = new JButton("ȸ������");								// ȸ������ ��ư ���
		
		//Canvas ��ü ����
		draw = new DrawImage();
	}
	
	public void setFrame(){
		// ������ ���� ------------------------
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
		//X ������ ����Ǵ� �ڵ�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//�̺�Ʈ ���� ����
		login_button.addActionListener(this);
		regedit_button.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)  {
		if(e.getSource() == login_button)	{
			// �α��� ��ư ���������

			if( !id_input.getText().isEmpty() && !pw_input.getText().isEmpty() )	{
				//���̵� ��й�ȣ Ȯ�� true? -> DB
				loginUser = LoginUserService.getInstance().login( id_input.getText().toString(), pw_input.getText().toString() );
				if( loginUser == null )	{
					JOptionPane.showMessageDialog(null, "���̵�/��й�ȣ Ȯ�����ּ���", "����", JOptionPane.OK_CANCEL_OPTION);
					return;
				}
				new MENU();
				this.setVisible(false);;			// �α��� ȭ�� ����	
			} else	{
				JOptionPane.showMessageDialog(null, "���̵�/��й�ȣ�� �Է����ּ���", "�Է¿���", JOptionPane.OK_CANCEL_OPTION);
			}
			
		}
		else if(e.getSource() == regedit_button)	{
			// ȸ������ ��ư ���������
			new REGEDIT();
		}
	}
	
	public LOGIN(){
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
		setResizable(false);//ũ�� ����
		setVisible(true);//�����ֱ�
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

