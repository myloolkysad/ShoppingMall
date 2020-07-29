package AAA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


/*		- 회원 구매창 UI
 * 		회원 로그인할 시 실행된다.
 */

//
public class LoginCustomer extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	int i;
	private Dto dto;
	

	public LoginCustomer(Dto dto) {
		this.dto = dto;
		
		System.out.println("누가로그인했는지 출력: "+dto.getMemberID());
						
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setPreferredSize(new Dimension(900, 650)); //panel 사이즈 고정
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 180, 900, 650);
		scrollPane.setPreferredSize(new Dimension(900, 650));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(panel); //스크롤 클라이언트
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
//객체화
		
		
		//유저 로그인 후 상품 정보 불러오기
		//
		//
		//
		//
		//
		//
		UnitedArray unit = new UnitedArray();
		unit.productPrint();
		
		int buyNum[] = new int[unit.productList.size()];		//구매개수
		JPanel panelMain[] = new JPanel[unit.productList.size()];
		JPanel panelRight[] = new JPanel[unit.productList.size()];
		JPanel imagePanel[] = new JPanel[unit.productList.size()];	//이미지 작업
		JPanel panelCenter[] = new JPanel[unit.productList.size()];
		JButton minus[] = new JButton[unit.productList.size()];
		JButton plus[] = new JButton[unit.productList.size()];
		
		JLabel jl1[] = new JLabel[unit.productList.size()];
		JLabel jl2[] = new JLabel[unit.productList.size()];
		JLabel jl3[] = new JLabel[unit.productList.size()];
		JLabel jl4[] = new JLabel[unit.productList.size()];
		JLabel imageLabel[] = new JLabel[unit.productList.size()];	//이미지 작업
		ImageIcon icon[] = new ImageIcon[unit.productList.size()];//이미지작업
		
		for(i=0; i<unit.productList.size(); i++) {
			
			panelMain[i] = new JPanel();							
			panelMain[i].setPreferredSize(new Dimension(300,300));
			panel.add(panelMain[i]);
			panelMain[i].setBackground(new Color(192, 192, 192));
			panelMain[i].setLayout(new BorderLayout());
			
			panelRight[i] = new JPanel();
			panelCenter[i] = new JPanel();
			
			imagePanel[i] = new JPanel();//이미지작업
			imagePanel[i].setPreferredSize(new Dimension(600,300));//이미지작업
			panelMain[i].add(panelRight[i],BorderLayout.CENTER);
			panelMain[i].add(imagePanel[i],BorderLayout.WEST);//이미지작업
			panelRight[i].setLayout(new BorderLayout());
			panelCenter[i].setLayout(new GridLayout(4,1));
			
			if(i%2==0) {
				panelCenter[i].setBackground(new Color(199,254,240));
			}else {
				panelCenter[i].setBackground(new Color(199,240,254));
			}
			
			for(int j=0;j<23;j++) {
				int a = 1001+j;
				if(unit.productNumList.get(i).equals(a)) {
					icon[i] = new ImageIcon("./image/" + a + ".png");//이미지 작업
				}
			}
			imageLabel[i] = new JLabel(icon[i]);//이미지 작업
			imagePanel[i].add(imageLabel[i]);//이미지 작업
			
			buyNum[i]=0;
			
			jl1[i] = new JLabel("상품명 : "+unit.productNameList.get(i));
			jl2[i] = new JLabel("상품가격  : "+unit.productPriceList.get(i));
			jl3[i] = new JLabel("남은수량 : "+unit.productStockList.get(i));
			jl4[i] = new JLabel("구매 개수 : "+buyNum[i]);
									
			
			jl1[i].setHorizontalAlignment(JLabel.CENTER);
			jl2[i].setHorizontalAlignment(JLabel.CENTER);
			jl3[i].setHorizontalAlignment(JLabel.CENTER);
			jl4[i].setHorizontalAlignment(JLabel.CENTER);
			jl1[i].setPreferredSize(new Dimension(300, 100));
			
			panelCenter[i].add(jl1[i]);
			panelCenter[i].add(jl2[i]);
			panelCenter[i].add(jl3[i]);
			panelCenter[i].add(jl4[i]);
			
			minus[i] = new JButton("ㅡ");
			plus[i] = new JButton("+");
			panelRight[i].add(panelCenter[i],BorderLayout.CENTER);
			panelRight[i].add(plus[i],BorderLayout.NORTH);
			panelRight[i].add(minus[i],BorderLayout.SOUTH);
		}
		
		for(i=0;i<unit.productNameList.size();i++) {
			int j = i;
			
			plus[i].addActionListener(new ActionListener() {// + 버튼 누를때
				@Override
				public void actionPerformed(ActionEvent e) {
					buyNum[j]++;
					jl4[j].setText("구매 개수 : "+buyNum[j]);
				}
			});

			minus[i].addActionListener(new ActionListener() {// ㅡ 버튼 누를때
				@Override
				public void actionPerformed(ActionEvent e) {
					if(buyNum[j] > 0) {
						buyNum[j]--;
						jl4[j].setText("구매 개수 : "+buyNum[j]);
					}
				}
			});
		}
	

		textField = new JTextField();
		textField.setBounds(100, 110, 410, 35);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel userIdLabel = new JLabel(dto.getMemberID()+" 님 안녕하세요");
		userIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userIdLabel.setBounds(30, 30, 150, 36);
		contentPane.add(userIdLabel);
		
		JLabel searchLabel = new JLabel("검색");
		searchLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		searchLabel.setBounds(25, 110, 57, 36);
		contentPane.add(searchLabel);

		JButton logoutButton = new JButton("로그아웃");
		logoutButton.setBounds(850, 20, 100, 35);
		contentPane.add(logoutButton);

		JButton buyLogButton = new JButton("구매내역");
		buyLogButton.setBounds(650, 20, 100, 35);
		contentPane.add(buyLogButton);

		JButton takeButton = new JButton("담기");
		takeButton.setBounds(700, 110, 100, 35);
		contentPane.add(takeButton);

		JButton buyButton = new JButton("장바구니");
		buyButton.setBounds(850, 110, 100, 35);
		contentPane.add(buyButton);

		JButton searchButton = new JButton("검색");
		searchButton.setBounds(550, 110, 100, 35);
		contentPane.add(searchButton);
		
		
		
		searchButton.addActionListener(new ActionListener() { //검색 버튼을 눌렀을 때		
			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void actionPerformed(ActionEvent e) {
				String arrayA;
				int arrayB, arrayC, arrayA00;
				
				for(i=0;i<unit.productList.size();i++) {
					String a = unit.productNameList.get(i);
					String b = textField.getText();
					buyNum[i]=0;
					jl4[i].setText("구매 개수 : "+buyNum[i]);
					
					if(a.equals(b)) {
						arrayA00 = unit.productNumList.get(0);
						unit.productNumList.set(0, unit.productNumList.get(i));
						unit.productNumList.set(i, arrayA00);
						
						arrayA = unit.productNameList.get(0);
						unit.productNameList.set(0, unit.productNameList.get(i));
						unit.productNameList.set(i, arrayA);
						
						arrayC = unit.productPriceList.get(0);
						unit.productPriceList.set(0, unit.productPriceList.get(i));
						unit.productPriceList.set(i, arrayC);
						
						arrayB = unit.productStockList.get(0);
						unit.productStockList.set(0, unit.productStockList.get(i));
						unit.productStockList.set(i, arrayB);
						
						jl1[0].setText("상품명 : "+unit.productNameList.get(0));
						jl2[0].setText("상품가격  : "+unit.productPriceList.get(0));
						jl3[0].setText("남은수량 : "+unit.productStockList.get(0));
						
						jl1[i].setText("상품명 : "+unit.productNameList.get(i));
						jl2[i].setText("상품가격  : "+unit.productPriceList.get(i));
						jl3[i].setText("남은수량 : "+unit.productStockList.get(i));
						
						System.out.println(unit.productNumList.get(i));
						
						for(int j=0;j<23;j++) {
							int aa = 1001+j;
							if(unit.productNumList.get(i) == aa) {
								icon[i] = new ImageIcon("./image/" + aa + ".png");
							}else if(unit.productNumList.get(0) == aa ){
								icon[0] = new ImageIcon("./image/" + aa + ".png");
							}

						}
						imageLabel[i].setIcon(icon[i]);
						imageLabel[0].setIcon(icon[0]);
						
						jl1[0].setOpaque(true);jl2[0].setOpaque(true); //Label 불투명화
						jl3[0].setOpaque(true);jl4[0].setOpaque(true);
						jl1[0].setBackground(new Color(73,217,248));
						jl2[0].setBackground(new Color(255,117,120));
						jl3[0].setBackground(new Color(73,217,248));
						jl4[0].setBackground(new Color(73,217,248));

					}
				}

			}
		});
		
		buyLogButton.addActionListener(new ActionListener() { //구매 내역 버튼을 눌렀을 때	
			@Override
			public void actionPerformed(ActionEvent e) {
				new PurchaseList(dto);
			}
		});
		
		takeButton.addActionListener(new ActionListener() { //담기 버튼을 눌렀을 때
			@Override
			public void actionPerformed(ActionEvent e) {
				//구매갯수에 따라서 담기는거 
				ArrayList<Dto> baskectDto = new ArrayList<Dto>(); //담을 상품들 목록
				ArrayList<Dto> compareDto = new ArrayList<Dto>();
				Dao dao = new Dao();
				int a,b;
				int count=0;
				try {
				for(int i=0; i<unit.productNameList.size();i++) {  //구매수량 있는거만 상품목록에 추가   
					
					try {
					if(unit.productStockList.get(i) < buyNum[i]) { JOptionPane.showMessageDialog(null, unit.productNameList.get(i)+"의 수량이 모자랍니다.");  }
					} catch (Exception e9) {
						System.out.println("이거이거");
					}
					
					if(buyNum[i] > 0 && unit.productStockList.get(i) >= buyNum[i])	{
						Dto basDto = new Dto();
						basDto.setOrderId(dto.getMemberID());
						basDto.setOrderProduct(unit.productNameList.get(i));
						basDto.setOrderPrice(unit.productPriceList.get(i));
						basDto.setOrderProductAmount(buyNum[i]);
						baskectDto.add(basDto);
						count++;   //담기되는 상품 갯수
					}
				}
				
				//insertBasket 하기 전에 장바구니 가져와서 상품명 비교
				unit.basketPrint(dto); //장바구니 가져오기				

				for(int i=0; i<count;i++) {
						//있으면 update
					if(unit.bascketProductName.contains(baskectDto.get(i).getOrderProduct())) {  //기존 장바구니에 상품명이 있다면
						a = unit.bascketProductName.indexOf(baskectDto.get(i).getOrderProduct()); //기존 상품명의 인덱스 번호 구한뒤
						b = unit.bascketProductAmount.get(a)+baskectDto.get(i).getOrderProductAmount();  //인덱스 번호로 찾은 상품의 장바구니 수량 + 새로 추가한 수량
						unit.bascketProductAmount.set(a, b);	   // 을 새롭게 수량 변경
						//장바구니 업데이트
						dao.updateBascket(baskectDto.get(i).getOrderId(), unit.bascketProductName.get(a), b);
					}else {
						//없으면 insert
						dao.insertBasket(baskectDto.get(i).getOrderId(), baskectDto.get(i).getOrderProduct(), 
								baskectDto.get(i).getOrderPrice(), baskectDto.get(i).getOrderProductAmount());
					}
				}
				} catch(Exception e5) { System.out.println("장바구니 오류"); e5.getStackTrace();}
				finally {
					for(int i=0; i<unit.productList.size();i++) {
						buyNum[i] = 0;
					} 
					if(!baskectDto.isEmpty()) { JOptionPane.showMessageDialog(null, "선택하신 상품이 장바구니에 담겼습니다."); }
					setVisible(false);
					new LoginCustomer(dto);
				}
			}
		});
		
		buyButton.addActionListener(new ActionListener() { //장바구니 버튼을 눌렀을 때
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShoppingBasket(dto);
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

	}
}