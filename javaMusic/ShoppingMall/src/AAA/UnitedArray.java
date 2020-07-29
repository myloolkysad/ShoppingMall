package AAA;

import java.util.ArrayList;

/*		- ArrayList�迭 ����
 * 		DB�� �����ؼ� �����͸� ����޴´�.
 */



public class UnitedArray {
	Dao userDao = new Dao(); //���������� �������ִ� ����
	Dao productDao = new Dao(); //��ǰ������ �������ִ� ����
	Dao basketDao = new Dao(); //��ٱ��������� �������ִ� ����
	Dao purchaseDao = new Dao(); //���������� �������ִ� ����
	Dto dto = new Dto(); //������ �α����ϸ� �ڱ� ������ ����� ����
	
	//Dto ����Ʈ
	ArrayList<Dto> userList = new ArrayList<Dto>();
	ArrayList<Dto> productList = new ArrayList<Dto>();
	ArrayList<Dto> bascketList = new ArrayList<Dto>();
	ArrayList<Dto> purchaseList = new ArrayList<Dto>();
	
	//������������Ʈ
	ArrayList<String> userIdList = new ArrayList<String>(); // ���� ���̵�
	ArrayList<String> userPwList = new ArrayList<String>(); // ������й�ȣ
	ArrayList<String> userPhoneList = new ArrayList<String>(); // �����޴�����ȣ
	ArrayList<Integer> userPurchaseList = new ArrayList<Integer>(); //�������űݾ�
	
	//��ǰ��������Ʈ
	ArrayList<Integer> productNumList = new ArrayList<Integer>(); //��ǰ��ȣ����Ʈ
	ArrayList<String> productNameList = new ArrayList<String>(); //��ǰ�̸�����Ʈ
	ArrayList<Integer> productPriceList = new ArrayList<Integer>(); //��ǰ����
	ArrayList<Integer> productStockList = new ArrayList<Integer>(); //���
	
	//��ٱ�����������Ʈ
	ArrayList<String> bascketUserID = new ArrayList<String>(); //��ٱ����������̵�
	ArrayList<String> bascketProductName = new ArrayList<String>(); //��ٱ��� ��ǰ��
	ArrayList<Integer> bascketProductPrice = new ArrayList<Integer>(); //��ٱ��� ����
	ArrayList<Integer> bascketProductAmount = new ArrayList<Integer>(); //��ٱ��� ����
	
	//������������Ʈ
	ArrayList<String> purchaseUserID = new ArrayList<String>(); //���� �������̵�
	ArrayList<String> purchaseProductName = new ArrayList<String>(); //���� ��ǰ��
	ArrayList<Integer> purchaseProductPrice = new ArrayList<Integer>(); //���űݾ�
	ArrayList<Integer> pubchaseProductAmount = new ArrayList<Integer>(); //���� ��ǰ ����
	
	
	public UnitedArray() {	}
	
	//���� ���� ��
	public void productPrint() {	// ��,�����ڰ� �����ͺ��̽����� ��ǰ���� ��������
		productList=productDao.bringProduct(); //�����ͺ��̽����� ��ǰ���� �������� 
		System.out.println("������Ƽ�� unit.productList.size() ���� " + productList.size());
		for(int i=0; i<productList.size(); i++) {
			productNumList.add(productList.get(i).getProductNum());
			productNameList.add(productList.get(i).getProductName());
			productPriceList.add(productList.get(i).getProductPrice());
			productStockList.add(productList.get(i).getStockAmount());
		}
	}
		
	public void basketPrint(Dto dto) {	// �����ϱ� ���ؼ� ��ٱ��� ������ ������
		this.dto = dto;
		
		bascketList = basketDao.bringbascket(dto); //�ڱⲨ�� ����Ʈ���� ��������
		
		for(int i=0; i<bascketList.size(); i++) {
			bascketProductName.add(bascketList.get(i).getOrderProduct());   //��ǰ�̸�
			bascketProductPrice.add(bascketList.get(i).getOrderPrice()); //���Ű���
			bascketProductAmount.add(bascketList.get(i).getOrderProductAmount()); //���ż���		
		}
	}

	public void purchasePrint(Dto dto) {  //���� �����ͺ��̽����� �ڱⰡ ������ ��ǰ���� ��������
		this.dto = dto;
		
		purchaseList = purchaseDao.bringPurchaseList(dto);
		for(int i=0; i<purchaseList.size(); i++) {
			purchaseUserID.add(purchaseList.get(i).getPayId());   //������ ���̵�
			purchaseProductName.add(purchaseList.get(i).getPayProductName()); //���Ż�ǰ��
			purchaseProductPrice.add(purchaseList.get(i).getPayProductPrice()); //���Ż�ǰ ����
			pubchaseProductAmount.add(purchaseList.get(i).getPayProductAmount()); //���Ż�ǰ ����			
		}
	}

	
	

	//�����ڰ� ���� ��
	

	public void SalesManage() { // ������ �ǸŰ���
		purchaseList = purchaseDao.bringPurchaseList();
		int a,b;
		for(int i=0; i<purchaseList.size(); i++) {            
			System.out.println("���� ��ǰ�� �ִ���"+purchaseProductName.contains(purchaseList.get(i).getPayProductName()));
			if(purchaseProductName.contains(purchaseList.get(i).getPayProductName())) {           // ��ǰ�� :
				System.out.println("���� ��ǰ�� �ִ���2222"+purchaseProductName.contains(purchaseList.get(i).getPayProductName()));
				a = purchaseProductName.indexOf(purchaseList.get(i).getPayProductName());     // ����1: ���� ��ǰ���� �ִٸ�
				b = pubchaseProductAmount.get(a)+purchaseList.get(i).getPayProductAmount() ; //����Ʈ�� �߰� ���ϰ� �ش� ��ǰ���� �ε��� ��ȣ�� ����
				pubchaseProductAmount.set(a, b);												//�ִ� ��ǰ�� add ���� �ʰ� �ش� �ε��� ��ȣ�� ��ǰ ������ �߰�
			} else {                                                                             //���� 2: ���� ��ǰ���� ������
				purchaseProductName.add(purchaseList.get(i).getPayProductName());				// �״�� �߰� 
				purchaseProductPrice.add(purchaseList.get(i).getPayProductPrice()); // ��ǰ����
				pubchaseProductAmount.add(purchaseList.get(i).getPayProductAmount()); // �Ǹż���
				
			} 
		}
	}


	public void CustomerManage() { //������ ������
		userList = userDao.bringMember();

		for(int i=0; i<userList.size(); i++) {
			userIdList.add(userList.get(i).getMemberID());   //�������̵�
			userPwList.add(userList.get(i).getMemberPW()); 
			userPhoneList.add(userList.get(i).getMemberPhone());   //�������̵�
			userPurchaseList.add(userList.get(i).getPurchase()); //���ż���		
		}
	}		
	
	
	
	
			
}
