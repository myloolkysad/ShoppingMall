package AAA;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

//

/*		- 관리창UI
 * 		관리자 로그인할 시 실행되는 창
 */







public class AdminMenu extends JFrame {

	private JPanel contentPane;
	public AdminMenu() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 838, 524);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);



		JPanel panel = new JPanel();

		panel.setBackground(new Color(250, 240, 230));
		panel.setBounds(12, 10, 798, 465);
		contentPane.add(panel);
		panel.setLayout(null);


		
		JButton saleMag = new JButton("판매관리");
		JButton cusMag = new JButton("고객관리");
		JButton productMag = new JButton("상품관리");
		
		saleMag.setForeground(new Color(0, 0, 0));
		saleMag.setBackground(new Color(135, 206, 235));
		saleMag.setBounds(136, 79, 470, 86);
		panel.add(saleMag);

		cusMag.setForeground(new Color(0, 0, 0));
		cusMag.setBackground(new Color(135, 206, 235));
		cusMag.setBounds(136, 198, 470, 86);
		panel.add(cusMag);
		
		productMag.setForeground(new Color(0, 0, 0));
		productMag.setBackground(new Color(135, 206, 235));
		productMag.setBounds(136, 334, 470, 86);
		panel.add(productMag);
		
		JButton logoutButton = new JButton("로그아웃");
		logoutButton.setBounds(500, 30, 100, 35);
		panel.add(logoutButton);
		
		saleMag.addActionListener(new ActionListener() {	//판매관리 버튼	
			@Override
			public void actionPerformed(ActionEvent e) {
				new SalesManager();
				setVisible(false);
			}
		});

		productMag.addActionListener(new ActionListener() {	//상품관리 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				new ProductManager();
				setVisible(false);
			}
		});

		cusMag.addActionListener(new ActionListener() {	//고객관리 버튼
			@Override
			public void actionPerformed(ActionEvent e) {
				new CustomerManager();
				setVisible(false);
			}
		});
		
		logoutButton.addActionListener(new ActionListener() { //로그아웃 버튼을 눌렀을때	
			@Override
			public void actionPerformed(ActionEvent e) {
				new Login();
				setVisible(false);
			}
		});



		setVisible(true);
		setSize(832,518);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}
}