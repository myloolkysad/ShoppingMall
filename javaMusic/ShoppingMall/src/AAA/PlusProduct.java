package AAA;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;


public class PlusProduct extends JFrame{
	JLabel label1,label2,label3,label4,label5;
	JTextField field1,field2,field3,field4;
	JPanel panel1,panel2,panel3,panel4;
	JButton button1,button2;
	JTextArea content;

	public PlusProduct(){
		super("추가");
		setLayout(new FlowLayout());
		EtchedBorder eborder =  new EtchedBorder();
		label1 = new JLabel( "상품 추가!" );
		label1.setBorder(eborder);
		add( label1 );

		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		
		
		label2 = new JLabel("상품번호");
		label3 = new JLabel("상품이름");
		label4 = new JLabel("상품가격");
		label5 = new JLabel("상품수량");
		
						/*
						 *  필드에 입력되는 변수값
						 */
		field1 = new JTextField(12);		
		field2 = new JTextField(12);
		field3 = new JTextField(12);
		field4 = new JTextField(12);

		panel1.add(label2);
		panel1.add(field1);
		panel2.add(label3);
		panel2.add(field2);
		panel3.add(label4);
		panel3.add(field3);		
		panel4.add(label5);
		panel4.add(field4);

		button1 = new JButton("상품 추가");
		button2 = new JButton("뒤로가기");
		
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		add(button1);
		add(button2);
		
		
		
		button1.addActionListener(new ActionListener() { //상품추가
			
			@Override
			public void actionPerformed(ActionEvent e) {
			/*
			 *			field1 : 상품번호
			 * 			field2 : 상품이름
			 * 			field3 : 상품가격
			 * 			field4 : 상품수량
			 */
				Dao dao = new Dao();
				String productNum = field1.getText();
				String productName = field2.getText();
				String productPrice = field3.getText();
				String stockAmount = field4.getText();
				dao.insertProduct(productNum, productName, productPrice, stockAmount);
				JOptionPane.showMessageDialog(null, "상품이 추가 되었습니다.");
				setVisible(false);
				new ProductManager();
			}
		});
		
		button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new ProductManager();
			}
		});
		

         
         setSize( 250, 350 );
         setVisible(true);
         setLocationRelativeTo(null);
         setResizable(false);

	}

}
