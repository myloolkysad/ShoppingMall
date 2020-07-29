package AAA;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*		- 회원가입 UI
 * 		Login에서 회원가입버튼을 눌렀을때 실행됨.
 */




public class Sign_Up extends JFrame{
	JLabel Label1, Label2, Label3;
	JTextField field1, field2, field3;
	JButton button1, button2;
	JPanel panel1, panel2, panel3, panel4;
	
	public Sign_Up() {
		setTitle("회원가입");
		setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		Label1 = new JLabel(" id  ");
		Label2 = new JLabel(" pw  ");
		Label3 = new JLabel("phone");
		field1 = new JTextField(12);
		field2 = new JTextField(12);
		field3 = new JTextField("-는 빼고 입력",12);
		button1 = new JButton("중복확인");

		panel1.add(Label1);
		panel1.add(field1);
		panel1.add(button1);
		panel2.add(Label2);
		panel2.add(field2);
		panel3.add(Label3);
		panel3.add(field3);
		
		add(panel1);
		add(panel2);
		add(panel3);
		
		panel4 = new JPanel();
		button2 = new JButton("회원가입하기");
		panel4.add(button2);
		add(panel4);
		
		button1.addActionListener(new ActionListener() {    //중복확인 버튼 눌렀을때
			@Override
			public void actionPerformed(ActionEvent e) {
				String userID = field1.getText();
				Dao dao = new Dao();
				boolean check = dao.selectMember(userID);
				if(check == true) { JOptionPane.showMessageDialog(null, "아이디가 이미 존재합니다.");  } // 아이디가 이미 있을 때
				else { JOptionPane.showMessageDialog(null, "가입 가능합니다."); } // 아이디가 없을 때
			}
		});
		
		button2.addActionListener(new ActionListener() {      //회원가입버튼 눌렀을때
			@Override
			public void actionPerformed(ActionEvent e) {
//				회원정보 저장

				String userID = field1.getText();
				String userPW = field2.getText();
				String userPhone = field3.getText();	
				
				Dao dao = new Dao();
				dao.insertMember(userID, userPW, userPhone);
				
				JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다");
			}
		});
		
		setSize(260, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
	
	}
	
	public static void main(String[] args) {
		new Sign_Up();
	}
}