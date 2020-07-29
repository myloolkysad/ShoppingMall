package AAA;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

/*		- 고객관리창 UI
 * 		관리자로그인 후 관리창(AdiminMenu)에서 고객관리 눌렀을 때 실행된다.
 */





public class CustomerManager extends JFrame {

	private JPanel contentPane;

	int a1_num=0;
	int i;

	public CustomerManager() {
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setPreferredSize(new Dimension(900, 650)); //panel 사이즈 고정
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 100, 700, 370);
		scrollPane.setPreferredSize(new Dimension(700, 400));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);

		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(panel); //스크롤 클라이언트
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		UnitedArray unit = new UnitedArray();
		unit.CustomerManage();  //unit에 유저 정보 리스트를 담아주는 메소드
		
		JPanel panelMain[] = new JPanel[unit.userList.size()];
		JPanel panelRight[] = new JPanel[unit.userList.size()];
		JPanel panelCenter[] = new JPanel[unit.userList.size()];
		
		JPanel leftPanel[] = new JPanel[unit.userList.size()];
		JPanel firstPanel[] = new JPanel[unit.userList.size()];
		JPanel secondPanel[] = new JPanel[unit.userList.size()];
		JPanel thirdPanel[] = new JPanel[unit.userList.size()];
		JPanel fourthPanel[] = new JPanel[unit.userList.size()];
		
		JLabel firstLabel[] = new JLabel[unit.userList.size()];
		JLabel secondLabel[] = new JLabel[unit.userList.size()];
		JLabel thirdLabel[] = new JLabel[unit.userList.size()];
		JLabel fourthLabel[] = new JLabel[unit.userList.size()];
		
		JPanel checkPanel[] = new JPanel[unit.userList.size()];
		JCheckBox checkBox[] = new JCheckBox[unit.userList.size()];
		
		for(i=0;i<unit.userList.size();i++) {
			panelMain[i] = new JPanel();
			panelMain[i].setPreferredSize(new Dimension(300,25));
			panel.add(panelMain[i]);
			panelMain[i].setBackground(new Color(192, 192, 192));
			panelMain[i].setLayout(new BorderLayout());
			
			panelRight[i] = new JPanel();
			panelMain[i].add(panelRight[i],BorderLayout.EAST);
			panelRight[i].setLayout(new BorderLayout());		

			panelCenter[i] = new JPanel();
			panelMain[i].add(panelCenter[i],BorderLayout.CENTER);
			panelCenter[i].setLayout(new BorderLayout());
			
			leftPanel[i] = new JPanel();
			leftPanel[i].setLayout(new GridLayout(1,3));
			
			firstPanel[i] = new JPanel();
			secondPanel[i] = new JPanel();
			thirdPanel[i] = new JPanel();
			fourthPanel[i] = new JPanel();

			
			//ArrayList에서 변수 받음
			
			firstLabel[i] = new JLabel(unit.userIdList.get(i)); // 유저아이디
//			secondLabel[i] = new JLabel(unit.userPwList.get(i)); //유저 비밀번호
			thirdLabel[i] = new JLabel(unit.userPhoneList.get(i)); 
			fourthLabel[i] = new JLabel(unit.userPurchaseList.get(i).toString());

			firstPanel[i].setBackground(new Color(0, 192, 192));
			thirdPanel[i].setBackground(new Color(0, 192, 192));
			
			firstPanel[i].add(firstLabel[i]);
//			secondPanel[i].add(secondLabel[i]);
			thirdPanel[i].add(thirdLabel[i]);
			fourthPanel[i].add(fourthLabel[i]);
			
			
			leftPanel[i].add(firstPanel[i]);
//			leftPanel[i].add(secondPanel[i]);
			leftPanel[i].add(thirdPanel[i]);
			leftPanel[i].add(fourthPanel[i]);

			panelCenter[i].add(leftPanel[i]);
				
			checkPanel[i] = new JPanel();
			checkBox[i] = new JCheckBox();

//			checkPanel[i].add(checkBox[i]);
//			panelRight[i].add(checkPanel[i],BorderLayout.CENTER);
		}
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(300,200));
		panel.add(panel_2);
		panel_2.setBackground(new Color(246,254,255));
		panel_2.setLayout(new BorderLayout());
		
		JPanel panel_2r = new JPanel();
		panel_2.add(panel_2r,BorderLayout.EAST);
		panel_2r.setLayout(new BorderLayout());

		JLabel textField1 = new JLabel("아이디");
		textField1.setBounds(140, 60, 100, 35);
		contentPane.add(textField1);
		
//		JLabel textField2 = new JLabel("비밀번호");
//		textField2.setBounds(270, 60, 100, 35);
//		contentPane.add(textField2);
		
		JLabel textField3 = new JLabel("핸드폰번호");
		textField3.setBounds(360, 60, 100, 35);
		contentPane.add(textField3);
		
		JLabel textField4 = new JLabel("구매금액");
		textField4.setBounds(590, 60, 100, 35);
		contentPane.add(textField4);
		
//		JLabel textField5 = new JLabel("삭제");
//		textField5.setBounds(700, 60, 100, 35);
//		contentPane.add(textField5);

//		JButton deleteButton = new JButton("삭제");
//		deleteButton.setBounds(630, 500, 100, 35);
//		contentPane.add(deleteButton);
		
		JButton logoutButton = new JButton("뒤로가기");
		logoutButton.setBounds(660, 20, 100, 35);
		contentPane.add(logoutButton);
		
//		deleteButton.addActionListener(new ActionListener() { //삭제버튼을 눌렀을 때 
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
		
		
		logoutButton.addActionListener(new ActionListener() { //뒤로가기 버튼을 눌렀을 때
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new AdminMenu();
			}
		});




	}

}