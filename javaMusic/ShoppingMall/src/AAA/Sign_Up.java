package AAA;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*		- ȸ������ UI
 * 		Login���� ȸ�����Թ�ư�� �������� �����.
 */




public class Sign_Up extends JFrame{
	JLabel Label1, Label2, Label3;
	JTextField field1, field2, field3;
	JButton button1, button2;
	JPanel panel1, panel2, panel3, panel4;
	
	public Sign_Up() {
		setTitle("ȸ������");
		setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		Label1 = new JLabel(" id  ");
		Label2 = new JLabel(" pw  ");
		Label3 = new JLabel("phone");
		field1 = new JTextField(12);
		field2 = new JTextField(12);
		field3 = new JTextField("-�� ���� �Է�",12);
		button1 = new JButton("�ߺ�Ȯ��");

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
		button2 = new JButton("ȸ�������ϱ�");
		panel4.add(button2);
		add(panel4);
		
		button1.addActionListener(new ActionListener() {    //�ߺ�Ȯ�� ��ư ��������
			@Override
			public void actionPerformed(ActionEvent e) {
				String userID = field1.getText();
				Dao dao = new Dao();
				boolean check = dao.selectMember(userID);
				if(check == true) { JOptionPane.showMessageDialog(null, "���̵� �̹� �����մϴ�.");  } // ���̵� �̹� ���� ��
				else { JOptionPane.showMessageDialog(null, "���� �����մϴ�."); } // ���̵� ���� ��
			}
		});
		
		button2.addActionListener(new ActionListener() {      //ȸ�����Թ�ư ��������
			@Override
			public void actionPerformed(ActionEvent e) {
//				ȸ������ ����

				String userID = field1.getText();
				String userPW = field2.getText();
				String userPhone = field3.getText();	
				
				Dao dao = new Dao();
				dao.insertMember(userID, userPW, userPhone);
				
				JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�");
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