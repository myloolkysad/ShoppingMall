package AAA;
//ó�� ����ȭ��
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
          label1 = new JLabel( "���θ� �α���" );
          label1.setBorder(eborder);
          add( label1 );
          
          idPanel = new JPanel();
          pwPanel = new JPanel();
          label2 = new JLabel("���̵�");
          label3 = new JLabel("�н�����");
          idField = new JTextField(12);
          pwField = new JPasswordField(12);
          
          idPanel.add(label2);
          idPanel.add(idField);
          pwPanel.add(label3);
          pwPanel.add(pwField);
          
          // �α��ΰ� ȸ�������� ���� �г� ����

          loginPanel = new JPanel();
          button1 = new JButton("�α���");
          button2 = new JButton("ȸ������");
          loginPanel.add(button1);
          loginPanel.add(button2);
          subPanel = new JPanel();
          button3 = new JButton("��ȸ��");
          subPanel.add(button3);
          
          //�α��� ��ư
          button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String userID = idField.getText();
				String userPW = pwField.getText();
				Dao dao = new Dao();
				
				if(userID.equals("")) { JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���."); }
				else if(userPW.equals("")) { JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է����ּ���.");}
				else {	Dto dto = dao.loginMember(userID, userPW); 				
						
				try {
				if( dto != null ) {	setVisible(false);	}
				}catch (Exception e7) {	 System.out.println("�α��� ���ϸ� ��Ÿ���� ����"); }
				}
			}
		});
          
          //ȸ������ ��ư
          button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {new Sign_Up();}
		  });
          
          //��ȸ�� ��ư
          button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null,"��ȸ������ �����Ͻðڽ��ϱ�?","Confirm",JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.CLOSED_OPTION) {
					//x�� Ŭ���� ���
				}else if(result == JOptionPane.YES_OPTION){
					//yes�� Ŭ���Ѱ��
					new NotLoginCustomer();
					setVisible(false);
				}else {
					//no�� Ŭ���� ���
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
	