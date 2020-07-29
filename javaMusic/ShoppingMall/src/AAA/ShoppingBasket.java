package AAA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;




/*		- 장바구니 UI
 * 		회원 구매창(LoginCustomer)에서 장바구니를 눌렀을 때 실행된다.
 */


public class ShoppingBasket extends JFrame {

	private JPanel contentPane;
	private Dto dto;
	int a1_num=0;
	int[] priceSum;
	int total=0;
	JLabel textField;
	UnitedArray unit = new UnitedArray();
	
	public ShoppingBasket(Dto dto) {
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
		panel.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(panel); //스크롤 클라이언트
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		//고객이 장바구니 확인
		//
		//

		unit.basketPrint(dto); //고객꺼 장바구니만 담아온
		
		JPanel panelMain[] = new JPanel[unit.bascketProductName.size()];
		JPanel panelRight[] = new JPanel[unit.bascketProductName.size()];
		JPanel panelCenter[] = new JPanel[unit.bascketProductName.size()];
		
		JPanel leftPanel[] = new JPanel[unit.bascketProductName.size()];
		JPanel firstPanel[] = new JPanel[unit.bascketProductName.size()];
		JPanel secondPanel[] = new JPanel[unit.bascketProductName.size()];
		JPanel thirdPanel[] = new JPanel[unit.bascketProductName.size()];
		
		JLabel firstLabel[] = new JLabel[unit.bascketProductName.size()];
		JLabel secondLabel[] = new JLabel[unit.bascketProductName.size()];
		JLabel thirdLabel[] = new JLabel[unit.bascketProductName.size()];
		
		JPanel checkPanel[] = new JPanel[unit.bascketProductName.size()];
		JCheckBox checkBox[] = new JCheckBox[unit.bascketProductName.size()];
		
		for(int i=0;i<unit.bascketProductName.size();i++) {
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
			
			firstLabel[i] = new JLabel(unit.bascketProductName.get(i)); //장바구니상품명
			secondLabel[i] = new JLabel(unit.bascketProductPrice.get(i).toString());  //장바구니상품가격
			thirdLabel[i] = new JLabel(unit.bascketProductAmount.get(i).toString()); //장바구니수량
						
			firstPanel[i].add(firstLabel[i]);
			secondPanel[i].add(secondLabel[i]);
			thirdPanel[i].add(thirdLabel[i]);
		
			leftPanel[i].add(firstPanel[i]);
			leftPanel[i].add(secondPanel[i]);
			leftPanel[i].add(thirdPanel[i]);

			panelCenter[i].add(leftPanel[i]);
			
			checkPanel[i] = new JPanel();
			checkBox[i] = new JCheckBox();
			
			checkPanel[i].add(checkBox[i]);
			panelRight[i].add(checkPanel[i], BorderLayout.CENTER);
			
			int a = unit.bascketProductPrice.get(i);  // 익명구현객체 내에서 지역변수를 사용하면 final이 되기 때문에 밖에서 final로 될 지역변수를 선언
			int b = unit.bascketProductAmount.get(i);  // ----> 상품 가격과 상품 수량을 미리 꺼내서 변수에 담음
			

			checkBox[i].addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					 switch(e.getStateChange()){
		                case 1: //체크되면 1을 리턴
		                	System.out.println("클릭할 때 상품가격 : "+a);
		                	System.out.println("클릭할 때 상품개수 : "+b);
		                	System.out.println(total);
		                	total = total+(a*b);
		                	System.out.println(total);
		                	textField.setText("원");
		                    textField.setText("총 가격 : "+total);	
		                    break;
		                case 2: //체크해제하면 2를 리턴
		                	System.out.println("해제할 때 상품가격 : "+a);
		                	System.out.println("해제할 때 상품개수 : "+b);
		                	total = total-(a*b);
		                    textField.setText("총 가격 : "+total);
		                    break;
		                }
			  }
			});
		}
		

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(300,200));
		panel.add(panel_2);
		panel_2.setBackground(new Color(246,254,255));
		panel_2.setLayout(new BorderLayout());
		
		JPanel panel_2r = new JPanel();
		panel_2.add(panel_2r,BorderLayout.EAST);
		panel_2r.setLayout(new BorderLayout());

		textField = new JLabel("총 가격 : "+total);
		textField.setBounds(80, 500, 360, 35);
		contentPane.add(textField);
		
		JLabel textField1 = new JLabel("상품이름");
		textField1.setBounds(140, 60, 100, 35);
		contentPane.add(textField1);
		
		JLabel textField2 = new JLabel("상품가격");
		textField2.setBounds(360, 60, 100, 35);
		contentPane.add(textField2);
		
		JLabel textField3 = new JLabel("구매개수");
		textField3.setBounds(590, 60, 100, 35);
		contentPane.add(textField3);
		
		JButton purchaseButton = new JButton("구매");
		purchaseButton.setBounds(500, 500, 100, 35);
		contentPane.add(purchaseButton);
		
		JButton deleteButton = new JButton("삭제");
		deleteButton.setBounds(630, 500, 100, 35);
		contentPane.add(deleteButton);
		
		JButton logoutButton = new JButton("뒤로가기");
		logoutButton.setBounds(660, 20, 100, 35);
		contentPane.add(logoutButton);
		
		purchaseButton.addActionListener(new ActionListener() { //구매버튼을 눌렀을 때
			@Override
			public void actionPerformed(ActionEvent e) {
				Dao purchasDao = new Dao();
				for(int i=unit.bascketProductName.size()-1;i>-1;i--) {
					if(checkBox[i].isSelected()) {
						purchasDao.buyProduct(dto.getMemberID(), unit.bascketProductName.get(i), unit.bascketProductPrice.get(i),
								unit.bascketProductAmount.get(i)); 
						purchasDao.deletePurchaseProductFromBascket(dto.getMemberID(), unit.bascketProductName.get(i), 
								unit.bascketProductPrice.get(i), unit.bascketProductAmount.get(i));
					}
				}
				purchasDao.updatePurchase(dto); // 총 구매금액 업데이트

				JOptionPane.showMessageDialog(null, "선택하신 상품을 구매했습니다.");
				setVisible(false);
				new ShoppingBasket(dto);
				
			}
		});
		
		deleteButton.addActionListener(new ActionListener() { //삭제버튼을 눌렀을 때 
			@Override
			public void actionPerformed(ActionEvent e) {
				Dao bascketDeleteDao = new Dao();
				for(int i=unit.bascketProductName.size()-1;i>-1;i--) {
					if(checkBox[i].isSelected()) {
						bascketDeleteDao.deleteProductFromBascket(dto.getMemberID(), unit.bascketProductName.get(i), 
								unit.bascketProductPrice.get(i), unit.bascketProductAmount.get(i));
						checkBox[i].setSelected(false);
					}
				}
				JOptionPane.showMessageDialog(null, "선택하신 상품을 삭제했습니다.");
				setVisible(false);
				new ShoppingBasket(dto);
			}
			});
		
		logoutButton.addActionListener(new ActionListener() { //뒤로가기 버튼을 눌렀을 때
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new LoginCustomer(dto);
			}
		});

	}

}