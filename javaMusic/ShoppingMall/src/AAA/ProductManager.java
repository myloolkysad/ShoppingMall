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

/*		- ��ǰ����â UI
 * 		�����ڷα��� �� ����â(AdiminMenu)���� ��ǰ���� ������ �� ����ȴ�.
 */

public class ProductManager extends JFrame {

	private JPanel contentPane;
	Dto dto = new Dto();
	int a1_num=0;
	int i;

	public ProductManager() {
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setPreferredSize(new Dimension(900, 650)); //panel ������ ����
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 100, 700, 370);
		scrollPane.setPreferredSize(new Dimension(700, 400));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);

		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(panel); //��ũ�� Ŭ���̾�Ʈ
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		UnitedArray unit = new UnitedArray();	
		unit.productPrint();
		System.out.println("���δ�Ʈ�Ŵ��� productList ����: "+ unit.productList.size());
		JPanel panelMain[] = new JPanel[unit.productList.size()];
		JPanel panelRight[] = new JPanel[unit.productList.size()];
		JPanel panelCenter[] = new JPanel[unit.productList.size()];
		
		JPanel leftPanel[] = new JPanel[unit.productList.size()];
		JPanel firstPanel[] = new JPanel[unit.productList.size()];
		JPanel secondPanel[] = new JPanel[unit.productList.size()];
		JPanel thirdPanel[] = new JPanel[unit.productList.size()];
		JPanel fourthPanel[] = new JPanel[unit.productList.size()];
		
		JLabel firstLabel[] = new JLabel[unit.productList.size()];
		JLabel secondLabel[] = new JLabel[unit.productList.size()];
		JTextField thirdText[] = new JTextField[unit.productList.size()];
		JTextField fourthText[] = new JTextField[unit.productList.size()];
		
		JPanel checkPanel[] = new JPanel[unit.productList.size()];
		JCheckBox checkBox[] = new JCheckBox[unit.productList.size()];
		
		for(i=0;i<unit.productList.size();i++) {
			panelMain[i] = new JPanel();
			panelMain[i].setPreferredSize(new Dimension(300,30));
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
			leftPanel[i].setLayout(new GridLayout(1,4));
			
			firstPanel[i] = new JPanel();
			secondPanel[i] = new JPanel();
			thirdPanel[i] = new JPanel();
			fourthPanel[i] = new JPanel();
		
			
			
			firstLabel[i] = new JLabel(unit.productNumList.get(i).toString()); //��ǰ��ȣ
			secondLabel[i] = new JLabel(unit.productNameList.get(i)); //��ǰ�̸�
			thirdText[i] = new JTextField(7); //��ǰ����
			fourthText[i] = new JTextField(3); //��������
			
			thirdText[i].setText(unit.productPriceList.get(i).toString());
			fourthText[i].setText(unit.productStockList.get(i).toString());
			
			firstPanel[i].setBackground(new Color(0, 192, 192));
			thirdPanel[i].setBackground(new Color(0, 192, 192));
			
			firstPanel[i].add(firstLabel[i]);
			secondPanel[i].add(secondLabel[i]);
			thirdPanel[i].add(thirdText[i]);
			fourthPanel[i].add(fourthText[i]);
			
			leftPanel[i].add(firstPanel[i]);
			leftPanel[i].add(secondPanel[i]);
			leftPanel[i].add(thirdPanel[i]);
			leftPanel[i].add(fourthPanel[i]);

			panelCenter[i].add(leftPanel[i]);
				
			checkPanel[i] = new JPanel();
			checkBox[i] = new JCheckBox();

			checkPanel[i].add(checkBox[i]);
			panelRight[i].add(checkPanel[i],BorderLayout.CENTER);
		}
		

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(300,200));
		panel.add(panel_2);
		panel_2.setBackground(new Color(246,254,255));
		panel_2.setLayout(new BorderLayout());
		
		JPanel panel_2r = new JPanel();
		panel_2.add(panel_2r,BorderLayout.EAST);
		panel_2r.setLayout(new BorderLayout());
		

		JLabel textField1 = new JLabel("��ǰ��ȣ");
		textField1.setBounds(90, 60, 100, 35);
		contentPane.add(textField1);
		
		JLabel textField2 = new JLabel("��ǰ�̸�");
		textField2.setBounds(270, 60, 100, 35);
		contentPane.add(textField2);
		
		JLabel textField3 = new JLabel("��ǰ����");
		textField3.setBounds(430, 60, 100, 35);
		contentPane.add(textField3);
		
		JLabel textField4 = new JLabel("��������");
		textField4.setBounds(590, 60, 100, 35);
		contentPane.add(textField4);
		
		JLabel textField5 = new JLabel("����/����");
		textField5.setBounds(690, 60, 100, 35);
		contentPane.add(textField5);
		

		JButton  addButton = new JButton("�߰�");
		addButton.setBounds(370, 500, 100, 35);
		contentPane.add(addButton);

		JButton  reviseButton = new JButton("����");
		reviseButton.setBounds(500, 500, 100, 35);
		contentPane.add(reviseButton);

		JButton deleteButton = new JButton("����");
		deleteButton.setBounds(630, 500, 100, 35);
		contentPane.add(deleteButton);
		
		JButton logoutButton = new JButton("�ڷΰ���");
		logoutButton.setBounds(660, 20, 100, 35);
		contentPane.add(logoutButton);


		addButton.addActionListener(new ActionListener() { //�߰� ��ư
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PlusProduct();
				setVisible(false);
				
			}
		});
		
		
		reviseButton.addActionListener(new ActionListener() { //������ư�� ������ ��
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String a;
				String b;
				Dao productUpdatedao = new Dao();
				for(i=unit.productList.size()-1;i>-1;i--) {
				if(checkBox[i].isSelected()) {
					a = thirdText[i].getText();
					b = fourthText[i].getText();
					productUpdatedao.updateProduct(unit.productNumList.get(i), unit.productNameList.get(i), a, b);
					checkBox[i].setSelected(false);
				}
			}
				JOptionPane.showMessageDialog(null, "�����Ͻ� ��ǰ������ �����߽��ϴ�.");	
				setVisible(false);
				new ProductManager();
			}
		});
		
		
		
		deleteButton.addActionListener(new ActionListener() { //������ư�� ������ �� 
			@Override
			public void actionPerformed(ActionEvent e) {
								
				Dao productDeleteDao = new Dao();
				for(i=unit.productList.size()-1;i>-1;i--) {
					if(checkBox[i].isSelected()) {
						productDeleteDao.deleteProduct(unit.productNameList.get(i));
						checkBox[i].setSelected(false);
					}
				}
				JOptionPane.showMessageDialog(null, "�����Ͻ� ��ǰ�� �����߽��ϴ�.");
				setVisible(false);
				new ProductManager();
			}
		});
		
		
		logoutButton.addActionListener(new ActionListener() { //�ڷΰ��� ��ư�� ������ ��
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new AdminMenu();
			}
		});




	}

}