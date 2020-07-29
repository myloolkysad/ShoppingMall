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


/*		- ȸ�� ����â UI
 * 		ȸ�� �α����� �� ����ȴ�.
 */

//
public class LoginCustomer extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	int i;
	private Dto dto;
	

	public LoginCustomer(Dto dto) {
		this.dto = dto;
		
		System.out.println("�����α����ߴ��� ���: "+dto.getMemberID());
						
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 900);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setPreferredSize(new Dimension(900, 650)); //panel ������ ����
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 180, 900, 650);
		scrollPane.setPreferredSize(new Dimension(900, 650));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(panel); //��ũ�� Ŭ���̾�Ʈ
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
//��üȭ
		
		
		//���� �α��� �� ��ǰ ���� �ҷ�����
		//
		//
		//
		//
		//
		//
		UnitedArray unit = new UnitedArray();
		unit.productPrint();
		
		int buyNum[] = new int[unit.productList.size()];		//���Ű���
		JPanel panelMain[] = new JPanel[unit.productList.size()];
		JPanel panelRight[] = new JPanel[unit.productList.size()];
		JPanel imagePanel[] = new JPanel[unit.productList.size()];	//�̹��� �۾�
		JPanel panelCenter[] = new JPanel[unit.productList.size()];
		JButton minus[] = new JButton[unit.productList.size()];
		JButton plus[] = new JButton[unit.productList.size()];
		
		JLabel jl1[] = new JLabel[unit.productList.size()];
		JLabel jl2[] = new JLabel[unit.productList.size()];
		JLabel jl3[] = new JLabel[unit.productList.size()];
		JLabel jl4[] = new JLabel[unit.productList.size()];
		JLabel imageLabel[] = new JLabel[unit.productList.size()];	//�̹��� �۾�
		ImageIcon icon[] = new ImageIcon[unit.productList.size()];//�̹����۾�
		
		for(i=0; i<unit.productList.size(); i++) {
			
			panelMain[i] = new JPanel();							
			panelMain[i].setPreferredSize(new Dimension(300,300));
			panel.add(panelMain[i]);
			panelMain[i].setBackground(new Color(192, 192, 192));
			panelMain[i].setLayout(new BorderLayout());
			
			panelRight[i] = new JPanel();
			panelCenter[i] = new JPanel();
			
			imagePanel[i] = new JPanel();//�̹����۾�
			imagePanel[i].setPreferredSize(new Dimension(600,300));//�̹����۾�
			panelMain[i].add(panelRight[i],BorderLayout.CENTER);
			panelMain[i].add(imagePanel[i],BorderLayout.WEST);//�̹����۾�
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
					icon[i] = new ImageIcon("./image/" + a + ".png");//�̹��� �۾�
				}
			}
			imageLabel[i] = new JLabel(icon[i]);//�̹��� �۾�
			imagePanel[i].add(imageLabel[i]);//�̹��� �۾�
			
			buyNum[i]=0;
			
			jl1[i] = new JLabel("��ǰ�� : "+unit.productNameList.get(i));
			jl2[i] = new JLabel("��ǰ����  : "+unit.productPriceList.get(i));
			jl3[i] = new JLabel("�������� : "+unit.productStockList.get(i));
			jl4[i] = new JLabel("���� ���� : "+buyNum[i]);
									
			
			jl1[i].setHorizontalAlignment(JLabel.CENTER);
			jl2[i].setHorizontalAlignment(JLabel.CENTER);
			jl3[i].setHorizontalAlignment(JLabel.CENTER);
			jl4[i].setHorizontalAlignment(JLabel.CENTER);
			jl1[i].setPreferredSize(new Dimension(300, 100));
			
			panelCenter[i].add(jl1[i]);
			panelCenter[i].add(jl2[i]);
			panelCenter[i].add(jl3[i]);
			panelCenter[i].add(jl4[i]);
			
			minus[i] = new JButton("��");
			plus[i] = new JButton("+");
			panelRight[i].add(panelCenter[i],BorderLayout.CENTER);
			panelRight[i].add(plus[i],BorderLayout.NORTH);
			panelRight[i].add(minus[i],BorderLayout.SOUTH);
		}
		
		for(i=0;i<unit.productNameList.size();i++) {
			int j = i;
			
			plus[i].addActionListener(new ActionListener() {// + ��ư ������
				@Override
				public void actionPerformed(ActionEvent e) {
					buyNum[j]++;
					jl4[j].setText("���� ���� : "+buyNum[j]);
				}
			});

			minus[i].addActionListener(new ActionListener() {// �� ��ư ������
				@Override
				public void actionPerformed(ActionEvent e) {
					if(buyNum[j] > 0) {
						buyNum[j]--;
						jl4[j].setText("���� ���� : "+buyNum[j]);
					}
				}
			});
		}
	

		textField = new JTextField();
		textField.setBounds(100, 110, 410, 35);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel userIdLabel = new JLabel(dto.getMemberID()+" �� �ȳ��ϼ���");
		userIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userIdLabel.setBounds(30, 30, 150, 36);
		contentPane.add(userIdLabel);
		
		JLabel searchLabel = new JLabel("�˻�");
		searchLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		searchLabel.setBounds(25, 110, 57, 36);
		contentPane.add(searchLabel);

		JButton logoutButton = new JButton("�α׾ƿ�");
		logoutButton.setBounds(850, 20, 100, 35);
		contentPane.add(logoutButton);

		JButton buyLogButton = new JButton("���ų���");
		buyLogButton.setBounds(650, 20, 100, 35);
		contentPane.add(buyLogButton);

		JButton takeButton = new JButton("���");
		takeButton.setBounds(700, 110, 100, 35);
		contentPane.add(takeButton);

		JButton buyButton = new JButton("��ٱ���");
		buyButton.setBounds(850, 110, 100, 35);
		contentPane.add(buyButton);

		JButton searchButton = new JButton("�˻�");
		searchButton.setBounds(550, 110, 100, 35);
		contentPane.add(searchButton);
		
		
		
		searchButton.addActionListener(new ActionListener() { //�˻� ��ư�� ������ ��		
			@SuppressWarnings("unlikely-arg-type")
			@Override
			public void actionPerformed(ActionEvent e) {
				String arrayA;
				int arrayB, arrayC, arrayA00;
				
				for(i=0;i<unit.productList.size();i++) {
					String a = unit.productNameList.get(i);
					String b = textField.getText();
					buyNum[i]=0;
					jl4[i].setText("���� ���� : "+buyNum[i]);
					
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
						
						jl1[0].setText("��ǰ�� : "+unit.productNameList.get(0));
						jl2[0].setText("��ǰ����  : "+unit.productPriceList.get(0));
						jl3[0].setText("�������� : "+unit.productStockList.get(0));
						
						jl1[i].setText("��ǰ�� : "+unit.productNameList.get(i));
						jl2[i].setText("��ǰ����  : "+unit.productPriceList.get(i));
						jl3[i].setText("�������� : "+unit.productStockList.get(i));
						
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
						
						jl1[0].setOpaque(true);jl2[0].setOpaque(true); //Label ������ȭ
						jl3[0].setOpaque(true);jl4[0].setOpaque(true);
						jl1[0].setBackground(new Color(73,217,248));
						jl2[0].setBackground(new Color(255,117,120));
						jl3[0].setBackground(new Color(73,217,248));
						jl4[0].setBackground(new Color(73,217,248));

					}
				}

			}
		});
		
		buyLogButton.addActionListener(new ActionListener() { //���� ���� ��ư�� ������ ��	
			@Override
			public void actionPerformed(ActionEvent e) {
				new PurchaseList(dto);
			}
		});
		
		takeButton.addActionListener(new ActionListener() { //��� ��ư�� ������ ��
			@Override
			public void actionPerformed(ActionEvent e) {
				//���Ű����� ���� ���°� 
				ArrayList<Dto> baskectDto = new ArrayList<Dto>(); //���� ��ǰ�� ���
				ArrayList<Dto> compareDto = new ArrayList<Dto>();
				Dao dao = new Dao();
				int a,b;
				int count=0;
				try {
				for(int i=0; i<unit.productNameList.size();i++) {  //���ż��� �ִ°Ÿ� ��ǰ��Ͽ� �߰�   
					
					try {
					if(unit.productStockList.get(i) < buyNum[i]) { JOptionPane.showMessageDialog(null, unit.productNameList.get(i)+"�� ������ ���ڶ��ϴ�.");  }
					} catch (Exception e9) {
						System.out.println("�̰��̰�");
					}
					
					if(buyNum[i] > 0 && unit.productStockList.get(i) >= buyNum[i])	{
						Dto basDto = new Dto();
						basDto.setOrderId(dto.getMemberID());
						basDto.setOrderProduct(unit.productNameList.get(i));
						basDto.setOrderPrice(unit.productPriceList.get(i));
						basDto.setOrderProductAmount(buyNum[i]);
						baskectDto.add(basDto);
						count++;   //���Ǵ� ��ǰ ����
					}
				}
				
				//insertBasket �ϱ� ���� ��ٱ��� �����ͼ� ��ǰ�� ��
				unit.basketPrint(dto); //��ٱ��� ��������				

				for(int i=0; i<count;i++) {
						//������ update
					if(unit.bascketProductName.contains(baskectDto.get(i).getOrderProduct())) {  //���� ��ٱ��Ͽ� ��ǰ���� �ִٸ�
						a = unit.bascketProductName.indexOf(baskectDto.get(i).getOrderProduct()); //���� ��ǰ���� �ε��� ��ȣ ���ѵ�
						b = unit.bascketProductAmount.get(a)+baskectDto.get(i).getOrderProductAmount();  //�ε��� ��ȣ�� ã�� ��ǰ�� ��ٱ��� ���� + ���� �߰��� ����
						unit.bascketProductAmount.set(a, b);	   // �� ���Ӱ� ���� ����
						//��ٱ��� ������Ʈ
						dao.updateBascket(baskectDto.get(i).getOrderId(), unit.bascketProductName.get(a), b);
					}else {
						//������ insert
						dao.insertBasket(baskectDto.get(i).getOrderId(), baskectDto.get(i).getOrderProduct(), 
								baskectDto.get(i).getOrderPrice(), baskectDto.get(i).getOrderProductAmount());
					}
				}
				} catch(Exception e5) { System.out.println("��ٱ��� ����"); e5.getStackTrace();}
				finally {
					for(int i=0; i<unit.productList.size();i++) {
						buyNum[i] = 0;
					} 
					if(!baskectDto.isEmpty()) { JOptionPane.showMessageDialog(null, "�����Ͻ� ��ǰ�� ��ٱ��Ͽ� �����ϴ�."); }
					setVisible(false);
					new LoginCustomer(dto);
				}
			}
		});
		
		buyButton.addActionListener(new ActionListener() { //��ٱ��� ��ư�� ������ ��
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShoppingBasket(dto);
				setVisible(false);
			}
		});
		
		logoutButton.addActionListener(new ActionListener() { //�α׾ƿ� ��ư�� ��������	
			@Override
			public void actionPerformed(ActionEvent e) {
				new Login();
				setVisible(false);
			}
		});

	}
}