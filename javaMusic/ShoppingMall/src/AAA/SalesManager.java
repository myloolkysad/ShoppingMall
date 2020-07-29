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
import javax.swing.JCheckBox;

/*		- �ǸŰ���â UI
 * 		�����ڷα��� �� ����â(AdiminMenu)���� �ǸŰ��� ������ �� ����ȴ�.
 */

public class SalesManager extends JFrame {

	private JPanel contentPane;

	int a1_num=0;
	int i;
	int total=0;
	public SalesManager() {
		
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
		panel.setBackground(new Color(242,242,242));
		scrollPane.setViewportView(panel); //��ũ�� Ŭ���̾�Ʈ
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		UnitedArray unit = new UnitedArray(); //unit�� �Ǹ����� ����Ʈ�� ����ִ� �޼ҵ�
		unit.SalesManage();
		System.out.println("unitpurchaseList ����: "+unit.purchaseList.size());
		
		JPanel panelMain[] = new JPanel[unit.purchaseProductName.size()];
		JPanel panelRight[] = new JPanel[unit.purchaseProductName.size()];
		JPanel panelCenter[] = new JPanel[unit.purchaseProductName.size()];
		
		JPanel leftPanel[] = new JPanel[unit.purchaseProductName.size()];
		JPanel firstPanel[] = new JPanel[unit.purchaseProductName.size()];
		JPanel secondPanel[] = new JPanel[unit.purchaseProductName.size()];
		JPanel thirdPanel[] = new JPanel[unit.purchaseProductName.size()];
		
		JLabel firstLabel[] = new JLabel[unit.purchaseProductName.size()];
		JLabel secondLabel[] = new JLabel[unit.purchaseProductName.size()];
		JLabel thirdLabel[] = new JLabel[unit.purchaseProductName.size()];
		System.out.println("(unit.pubchaseProductAmount.����"+ (unit.pubchaseProductAmount).size());
		System.out.println("unit.bascketProductPrice.get(i)) ����"+unit.bascketProductPrice.size());
		for(i=0;i<unit.purchaseProductName.size();i++) {
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
			
			//ArrayList���� ���� ����
			
			firstLabel[i] = new JLabel(unit.purchaseProductName.get(i)); //��ǰ��
			secondLabel[i] = new JLabel(unit.pubchaseProductAmount.get(i).toString()); //�Ǹŷ�
			thirdLabel[i] = new JLabel((unit.pubchaseProductAmount.get(i) * unit.purchaseProductPrice.get(i))+"��"); //�Ǹűݾ�
			total = total + (unit.pubchaseProductAmount.get(i) * unit.purchaseProductPrice.get(i));
			firstPanel[i].setBackground(new Color(0, 192, 192));
			thirdPanel[i].setBackground(new Color(0, 192, 192));
			
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
		
		JLabel textField1 = new JLabel("��ǰ��");
		textField1.setBounds(140, 60, 100, 35);
		contentPane.add(textField1);
		
		JLabel textField2 = new JLabel("�Ǹŷ�");
		textField2.setBounds(360, 60, 100, 35);
		contentPane.add(textField2);
		
		JLabel textField3 = new JLabel("�Ǹűݾ�");
		textField3.setBounds(590, 60, 100, 35);
		contentPane.add(textField3);
		
		JLabel textField4 = new JLabel("�� �ݾ�: "+total );
		textField4.setBounds(500,500,100,35);
		contentPane.add(textField4);
		
		JButton logoutButton = new JButton("�ڷΰ���");
		logoutButton.setBounds(660, 20, 100, 35);
		contentPane.add(logoutButton);

		logoutButton.addActionListener(new ActionListener() { //�ڷΰ��� ��ư�� ������ ��
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new AdminMenu();
			}
		});

	}
}