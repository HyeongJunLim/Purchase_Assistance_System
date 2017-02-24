package LEAPMOTION.gui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import MVC.model.User;
import MVC.service.IdCheckService;
import MVC.service.RegeditUserService;

public class REGEDIT extends JFrame implements ActionListener{
	boolean canRegedit;
	
	JPanel text_panel;
	JPanel input_panel;
   
	JPanel Id_panel;
	JPanel check_panel;	
	JPanel number_panel;
	JPanel button_panel;
      
	JPanel type_panel;
   
	JLabel id_label;
	JLabel id_check_label;
	JLabel pw_label;
	JLabel check;
	JLabel pw_ok;
	JLabel name_label;
	JLabel branch;
	JLabel number;
   
	JTextField id_input;
   
	JPasswordField pw_input;
	JPasswordField pw_check;
   
	JTextField name_input;
	JTextField branch_input;
   
	Choice numType;
	JTextField number1_input;
	JTextField number2_input;
   
	JButton check_id;
	JButton ok_btn;
	JButton cancle_btn;
   
	public void setpart() {
		text_panel = new JPanel();
		input_panel = new JPanel();
		Id_panel = new JPanel(new GridLayout(1, 3));
		check_panel = new JPanel();
        	check_panel.setLayout(new GridLayout(1, 2));
        number_panel = new JPanel();
        	number_panel.setLayout(new GridLayout(1, 3));
        button_panel = new JPanel();
        type_panel = new JPanel();
        
        id_label = new JLabel("ID");
         	id_label.setAlignmentY(LEFT_ALIGNMENT);
        pw_label = new JLabel("PW");         
	    check = new JLabel("재입력");
	    pw_ok = new JLabel();         
	    name_label = new JLabel("이 름");   
	    branch = new JLabel("지점명");
	    number = new JLabel("연락처");
         
	    id_check_label = new JLabel("");
      
	    id_input = new JTextField("");
	    pw_input = new JPasswordField(10);
        pw_input.setEchoChar('*');
        pw_check = new JPasswordField(10);
        pw_check.setEchoChar('*');            
        name_input = new JTextField("");
        branch_input = new JTextField("");
         
        numType = new Choice();
        	numType.add("02");
        	numType.add("031");
        	numType.add("032");
        	numType.add("033");
        	numType.add("041");
        	numType.add("051");
        number1_input = new JTextField("");
        number2_input = new JTextField("");

        check_id = new JButton("중복확인");
        ok_btn = new JButton("확인");
        cancle_btn = new JButton("취소");      
      
        Id_panel.add(id_input);
        Id_panel.add(check_id);
        Id_panel.add(id_check_label);
      
        check_panel.add(pw_check);
        check_panel.add(pw_ok);
      
        number_panel.add(numType);
        number_panel.add(number1_input);
        number_panel.add(number2_input);
         
        button_panel.add(ok_btn);
        button_panel.add(cancle_btn);
      
        text_panel.setLayout(new GridLayout(6, 1));
        text_panel.add(id_label);
        text_panel.add(pw_label);
        text_panel.add(check);
        text_panel.add(name_label);
        text_panel.add(branch);
        text_panel.add(number);
      
        input_panel.setLayout(new GridLayout(6, 1));
        input_panel.add(Id_panel);
        input_panel.add(pw_input);
        input_panel.add(check_panel);
        input_panel.add(name_input);
        input_panel.add(branch_input);
        input_panel.add(number_panel);
      
        GridBagLayout grd = new GridBagLayout();
        GridBagConstraints gct = new GridBagConstraints();
        gct.fill = GridBagConstraints.BOTH;
        type_panel.setLayout(grd);
        gct.gridx = 0;
        gct.gridy = 1;
        grd.addLayoutComponent(text_panel, gct);
        type_panel.add(text_panel);
      
        gct.gridx = 1;
        gct.gridy = 1;
        grd.addLayoutComponent(input_panel, gct);
        type_panel.add(input_panel);
	}
   
  	public void setFrame(){
  		this.setLayout(new BorderLayout());
  		this.add(type_panel,BorderLayout.CENTER);
  		this.add(button_panel,BorderLayout.SOUTH);
  	}
   
  	public void event(){
  		//X 누르면 종료되는 코드
  		this.addWindowListener(new WindowAdapter() {
  			@Override
  			public void windowClosing(WindowEvent e) {
  				REGEDIT.this.dispose();
  			}
  		});
      /*if(!pw_input.getText().isEmpty()&& !pw_check.getText().isEmpty()) {
         if(pw_input.getText().length() == pw_check.getText().length() ) {
            if(pw_input.getText().toString() == pw_check.getText().toString()) {
               pw_ok.setText("비밀번호 일치");
            }else
               pw_ok.setText("비밀번호 불일치");
         }
      }*/   
      
  		//이벤트 연결 설정
  		check_id.addActionListener(this);
  		ok_btn.addActionListener(this);
  		cancle_btn.addActionListener(this);
  	}

  	public void actionPerformed(ActionEvent e) {
  		if(e.getSource() == ok_btn) {
  			if(canRegedit == false)	{
  				JOptionPane.showMessageDialog(null, "아이디 중복확인해주세요", "알림", JOptionPane.OK_CANCEL_OPTION);
  				return;
  			}
  			if( !pw_input.getText().equals(pw_check.getText()) )	{
  				JOptionPane.showMessageDialog(null, "비밀번호가 일치하지않습니다", "알림", JOptionPane.OK_CANCEL_OPTION);
  				return;
  			}
  			String userId = id_input.getText().toString();
  			String userPw = pw_input.getText().toString();
  			String userName = name_input.getText().toString();
  			String branchName = branch_input.getText().toString();
  			String number = numType.getSelectedItem().toString() + number1_input.getText().toString() + number2_input.getText().toString();
  			
  			User user = new User();
  			user.toUser(userId, userPw, userName, branchName, number);

  			RegeditUserService.getInstance().regedit(user);
  			JOptionPane.showMessageDialog(null, "회원가입 완료하였습니다", "성공", JOptionPane.OK_CANCEL_OPTION);
				
  			this.dispose();
  		} else if(e.getSource() == check_id) {
  			if(id_input.getText().isEmpty())	{
  				return;
  			}
  			User checkUser = IdCheckService.getInstance().check(id_input.getText().toString());
  			if(checkUser == null)	{
  				JOptionPane.showMessageDialog(null, "가입가능한 아이디입니다", "가능", JOptionPane.OK_CANCEL_OPTION);
  				canRegedit = true;
  			} else	{
  				JOptionPane.showMessageDialog(null, "이미있는 아이디입니다", "불가능", JOptionPane.OK_CANCEL_OPTION);
  				canRegedit = false;
  			}
  		} else {
  			this.dispose();
  		}
  	}
  	public REGEDIT() {
  		setpart();
  		setFrame();
  		event();
  		//세부 속성 설정
  		setTitle("LEAP MOTION -회원가입-");                         //제목
  		setSize(400, 300);                              //크기(폭, 높이)
         
  		//화면 중심 계산
  		Dimension di = Toolkit.getDefaultToolkit().getScreenSize();
  		int xpos = di.width/2 - this.getWidth()/2;
  		int ypos = di.height/2 - this.getHeight()/2;
            
  		canRegedit = false;
  		
  		//위치(왼쪽 상단)
  		setLocation(xpos, ypos);
  		setResizable(false);//크기 고정
  		setVisible(true);//보여주기
  	}
}