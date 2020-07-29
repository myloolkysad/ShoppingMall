package AAA;

//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.EventQueue;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.BoxLayout;
//import javax.swing.*;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.ScrollPaneConstants;
//import javax.swing.border.EmptyBorder;
//import java.awt.BorderLayout;
//import javax.swing.JLabel;
//import javax.swing.JButton;
//
///*		- 판매관리창 UI
// * 		관리자로그인 후 관리창(AdiminMenu)에서 판매관리 눌렀을 때 실행된다.
// */
//
//
//
//
//
//public class LoginBuyConfirm extends JFrame {
//
//	private JPanel contentPane;
//
//	int a1_num=0;
//	int i;
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginBuyConfirm frame = new LoginBuyConfirm();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//
//	public LoginBuyConfirm() {
//		
//		setVisible(true);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(150, 150, 800, 600);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setPreferredSize(new Dimension(900, 650)); //panel 사이즈 고정
//		contentPane.setLayout(null);
//
//		
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(45, 100, 700, 330);
//		scrollPane.setPreferredSize(new Dimension(700, 400));
//		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		contentPane.add(scrollPane);
//
//		
//		JPanel panel = new JPanel();
//		panel.setBackground(new Color(242,242,242));
//		scrollPane.setViewportView(panel); //스크롤 클라이언트
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//
//
//		
//		UnitedArray unit = new UnitedArray();
//		
//		unit.SalesManage();
//		
//		JPanel panelMain[] = new JPanel[unit.userID.size()];
//		JPanel panelRight[] = new JPanel[unit.userID.size()];
//		JPanel panelCenter[] = new JPanel[unit.userID.size()];
//		
//		JPanel leftPanel[] = new JPanel[unit.userID.size()];
//		JPanel firstPanel[] = new JPanel[unit.userID.size()];
//		JPanel secondPanel[] = new JPanel[unit.userID.size()];
//		JPanel thirdPanel[] = new JPanel[unit.userID.size()];
//		
//		JLabel firstLabel[] = new JLabel[unit.userID.size()];
//		JLabel secondLabel[] = new JLabel[unit.userID.size()];
//		JLabel thirdLabel[] = new JLabel[unit.userID.size()];
//
//		
//		for(i=0;i<unit.userID.size();i++) {
//			panelMain[i] = new JPanel();
//			panelMain[i].setPreferredSize(new Dimension(300,25));
//			panel.add(panelMain[i]);
//			panelMain[i].setBackground(new Color(192, 192, 192));
//			panelMain[i].setLayout(new BorderLayout());
//			
//			panelRight[i] = new JPanel();
//			panelMain[i].add(panelRight[i],BorderLayout.EAST);
//			panelRight[i].setLayout(new BorderLayout());		
//
//			panelCenter[i] = new JPanel();
//			panelMain[i].add(panelCenter[i],BorderLayout.CENTER);
//			panelCenter[i].setLayout(new BorderLayout());
//			
//			leftPanel[i] = new JPanel();
//			leftPanel[i].setLayout(new GridLayout(1,3));
//			
//			firstPanel[i] = new JPanel();
//			secondPanel[i] = new JPanel();
//			thirdPanel[i] = new JPanel();
//			
//			//ArrayList에서 변수 받음
//			int sales1 = unit.salesNum.get(i);
//			String sales1_st = Integer.toString(sales1);
//			int productNum1 = unit.userProductNum.get(i);
//			String productNum1_st = Integer.toString(productNum1);
//
//			
//			firstLabel[i] = new JLabel(unit.userID.get(i));
//			secondLabel[i] = new JLabel(sales1_st);
//			thirdLabel[i] = new JLabel(productNum1_st);
//			
//
//			firstPanel[i].setBackground(new Color(0, 192, 192));
//			thirdPanel[i].setBackground(new Color(0, 192, 192));
//			
//			firstPanel[i].add(firstLabel[i]);
//			secondPanel[i].add(secondLabel[i]);
//			thirdPanel[i].add(thirdLabel[i]);
//			
//			
//			
//			leftPanel[i].add(firstPanel[i]);
//			leftPanel[i].add(secondPanel[i]);
//			leftPanel[i].add(thirdPanel[i]);
//			
//
//			panelCenter[i].add(leftPanel[i]);
//				
//
//		}
//		
//		
//
//		
//		
//		
//		JPanel panel_2 = new JPanel();
//		panel_2.setPreferredSize(new Dimension(300,200));
//		panel.add(panel_2);
//		panel_2.setBackground(new Color(246,254,255));
//		panel_2.setLayout(new BorderLayout());
//		
//		JPanel panel_2r = new JPanel();
//		panel_2.add(panel_2r,BorderLayout.EAST);
//		panel_2r.setLayout(new BorderLayout());
//		
//
//
//		JLabel textField1 = new JLabel("상품명");
//		textField1.setBounds(140, 60, 100, 35);
//		contentPane.add(textField1);
//		
//		JLabel textField2 = new JLabel(" 가격");
//		textField2.setBounds(370, 60, 100, 35);
//		contentPane.add(textField2);
//		
//		JLabel textField3 = new JLabel("  수량");
//		textField3.setBounds(590, 60, 100, 35);
//		contentPane.add(textField3);
//		
//
//
//		
//		JLabel textField4 = new JLabel("총 구매금액은  "+"원 입니다");
//		textField4.setBounds(120, 480, 200, 35);
//		contentPane.add(textField4);
//
//		JButton logoutButton = new JButton("확인");
//		logoutButton.setBounds(400, 480, 100, 35);
//		contentPane.add(logoutButton);
//
//
//
//		
//
//		
//		logoutButton.addActionListener(new ActionListener() { //뒤로가기 버튼을 눌렀을 때
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				setVisible(false);
//			}
//		});
//
//
//
//
//	}
//
//}