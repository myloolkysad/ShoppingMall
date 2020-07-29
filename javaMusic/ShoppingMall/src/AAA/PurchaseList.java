package AAA;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;


/*		- 구매내역창 UI
 * 		회원구매창(LoginCustomer)에서 구매내역을 눌렀을 때 실행된다.
 */


public class PurchaseList extends JFrame {

	private JPanel contentPane;
	private Dto dto;
	int a1_num=0;
	int i;
	int total=0;

	public PurchaseList(Dto dto) {
		this.dto = dto;
		
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
		panel.setBackground(new Color(242,242,242));
		scrollPane.setViewportView(panel); //스크롤 클라이언트
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		


		UnitedArray unit = new UnitedArray();
		unit.purchasePrint(dto);  // 
		
		JPanel panelMain[] = new JPanel[unit.purchaseUserID.size()];
		JPanel panelRight[] = new JPanel[unit.purchaseUserID.size()];
		JPanel panelCenter[] = new JPanel[unit.purchaseUserID.size()];
		
		JPanel leftPanel[] = new JPanel[unit.purchaseUserID.size()];
		JPanel firstPanel[] = new JPanel[unit.purchaseUserID.size()];
		JPanel secondPanel[] = new JPanel[unit.purchaseUserID.size()];
		JPanel thirdPanel[] = new JPanel[unit.purchaseUserID.size()];
		
		JLabel firstLabel[] = new JLabel[unit.purchaseUserID.size()];
		JLabel secondLabel[] = new JLabel[unit.purchaseUserID.size()];
		JLabel thirdLabel[] = new JLabel[unit.purchaseUserID.size()];

		
		for(i=0;i<unit.purchaseUserID.size();i++) {
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
			
			if(i%2==0) {
				firstPanel[i].setBackground(new Color(199,254,244));
				secondPanel[i].setBackground(new Color(199,254,244));
				thirdPanel[i].setBackground(new Color(199,254,244));
			}else {
				firstPanel[i].setBackground(new Color(244,244,244));
				secondPanel[i].setBackground(new Color(244,244,244));
				thirdPanel[i].setBackground(new Color(244,244,244));
			}
			
			//ArrayList에서 변수 받음

			firstLabel[i] = new JLabel(unit.purchaseProductName.get(i)); //상품명
			secondLabel[i] = new JLabel(unit.purchaseProductPrice.get(i).toString());  //상품가격
			thirdLabel[i] = new JLabel(unit.pubchaseProductAmount.get(i).toString()); //구매수량
			
			total = total + (unit.pubchaseProductAmount.get(i) * unit.purchaseProductPrice.get(i));
			
			firstPanel[i].add(firstLabel[i]);
			secondPanel[i].add(secondLabel[i]);
			thirdPanel[i].add(thirdLabel[i]);
		
			leftPanel[i].add(firstPanel[i]);
			leftPanel[i].add(secondPanel[i]);
			leftPanel[i].add(thirdPanel[i]);

			panelCenter[i].add(leftPanel[i]);
		}

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(300,200));
		panel.add(panel_2);
		panel_2.setBackground(new Color(246,254,255));
		panel_2.setLayout(new BorderLayout());
		
		JPanel panel_2r = new JPanel();
		panel_2.add(panel_2r,BorderLayout.EAST);
		panel_2r.setLayout(new BorderLayout());
		
		JLabel textField1 = new JLabel("상품이름");
		textField1.setBounds(140, 60, 100, 35);
		contentPane.add(textField1);
		
		JLabel textField2 = new JLabel("상품가격");
		textField2.setBounds(360, 60, 100, 35);
		contentPane.add(textField2);
		
		JLabel textField3 = new JLabel("구매개수");
		textField3.setBounds(590, 60, 100, 35);
		contentPane.add(textField3);

		JButton logoutButton = new JButton("뒤로가기");
		logoutButton.setBounds(340, 480, 100, 35);
		contentPane.add(logoutButton);
		
		JLabel textField4 = new JLabel("총 금액 : "+total);
		textField4.setBounds(520, 480, 100, 35);
			contentPane.add(textField4);
		
		logoutButton.addActionListener(new ActionListener() { //뒤로가기 버튼을 눌렀을 때
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				
			}
		});

	}

}