package AAA;
//처음 시작화면
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.*;
 //////
public class Login extends JFrame{
    JLabel label1,label2,label3;
    JTextField idField,phoneField;
    JPasswordField pwField;
    JPanel idPanel,pwPanel,loginPanel,subPanel;
    JButton button1,button2,button3;
    JTextArea content;
 
	JLabel Label4, Label5, Label6, lblStatus;
	TextField tfId, tfPw, tfPhone;
	JButton button4, button5;
	JPanel panel1, panel2, panel3, panel4;
    
	public Login(){
          super("Login");
          setLayout(new FlowLayout());
          EtchedBorder eborder =  new EtchedBorder();
          label1 = new JLabel( "쇼핑몰 로그인" );
          label1.setBorder(eborder);
          add( label1 );
          
          idPanel = new JPanel();
          pwPanel = new JPanel();
          label2 = new JLabel("아이디");
          label3 = new JLabel("패스워드");
          idField = new JTextField(12);
          pwField = new JPasswordField(12);
          
          idPanel.add(label2);
          idPanel.add(idField);
          pwPanel.add(label3);
          pwPanel.add(pwField);
          
          // 로그인과 회원가입을 위한 패널 생성

          loginPanel = new JPanel();
          button1 = new JButton("로그인");
          button2 = new JButton("회원가입");
          loginPanel.add(button1);
          loginPanel.add(button2);
          subPanel = new JPanel();
          button3 = new JButton("비회원");
          subPanel.add(button3);
          
          //로그인 버튼
          button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userID = idField.getText();
				String userPW = pwField.getText();
				Dao dao = new Dao();
				
				if(userID.equals("")) { JOptionPane.showMessageDialog(null, "아이디를 입력해주세요."); }
				else if(userPW.equals("")) { JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");}
				else {	Dto dto = dao.loginMember(userID, userPW); 				
						
				try {
				if( dto != null ) {	setVisible(false);	}
				}catch (Exception e7) {	 System.out.println("로그인 못하면 나타나는 오류"); }
				}
			}
		});
          
          //회원가입 버튼
          button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {new Sign_Up();}
		  });
          
          //비회원 버튼
          button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null,"비회원으로 입장하시겠습니까?","Confirm",JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.CLOSED_OPTION) {
					//x를 클릭한 경우
				}else if(result == JOptionPane.YES_OPTION){
					//yes를 클릭한경우
					new NotLoginCustomer();
					setVisible(false);
				}else {
					//no를 클릭한 경우
				}
				
			}
		});


          add(idPanel);
          add(pwPanel);
          add(loginPanel);
          add(subPanel);

          
          setSize( 250, 350 );
          setVisible(true);
          setLocationRelativeTo(null);
          setResizable(false);
          setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    //
    public static void main( String args[] ){
        new Login();
       }
}
	