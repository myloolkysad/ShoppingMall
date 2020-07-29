package AAA;

import java.util.ArrayList;

/*		- ArrayList배열 저장
 * 		DB와 연동해서 데이터를 저장받는다.
 */



public class UnitedArray {
	Dao userDao = new Dao(); //유저정보를 가져와주는 변수
	Dao productDao = new Dao(); //상품정보를 가져와주는 변수
	Dao basketDao = new Dao(); //장바구니정보를 가져와주는 변수
	Dao purchaseDao = new Dao(); //구매정보를 가져와주는 변수
	Dto dto = new Dto(); //유저가 로그인하면 자기 정보를 담아줄 변수
	
	//Dto 리스트
	ArrayList<Dto> userList = new ArrayList<Dto>();
	ArrayList<Dto> productList = new ArrayList<Dto>();
	ArrayList<Dto> bascketList = new ArrayList<Dto>();
	ArrayList<Dto> purchaseList = new ArrayList<Dto>();
	
	//유저정보리스트
	ArrayList<String> userIdList = new ArrayList<String>(); // 유저 아이디
	ArrayList<String> userPwList = new ArrayList<String>(); // 유저비밀번호
	ArrayList<String> userPhoneList = new ArrayList<String>(); // 유저휴대폰번호
	ArrayList<Integer> userPurchaseList = new ArrayList<Integer>(); //유저구매금액
	
	//상품정보리스트
	ArrayList<Integer> productNumList = new ArrayList<Integer>(); //상품번호리스트
	ArrayList<String> productNameList = new ArrayList<String>(); //상품이름리스트
	ArrayList<Integer> productPriceList = new ArrayList<Integer>(); //상품가격
	ArrayList<Integer> productStockList = new ArrayList<Integer>(); //재고
	
	//장바구니정보리스트
	ArrayList<String> bascketUserID = new ArrayList<String>(); //장바구니유저아이디
	ArrayList<String> bascketProductName = new ArrayList<String>(); //장바구니 상품명
	ArrayList<Integer> bascketProductPrice = new ArrayList<Integer>(); //장바구니 수량
	ArrayList<Integer> bascketProductAmount = new ArrayList<Integer>(); //장바구니 수량
	
	//구매정보리스트
	ArrayList<String> purchaseUserID = new ArrayList<String>(); //구매 유저아이디
	ArrayList<String> purchaseProductName = new ArrayList<String>(); //구매 상품명
	ArrayList<Integer> purchaseProductPrice = new ArrayList<Integer>(); //구매금액
	ArrayList<Integer> pubchaseProductAmount = new ArrayList<Integer>(); //구매 상품 수량
	
	
	public UnitedArray() {	}
	
	//고객이 쓰는 곳
	public void productPrint() {	// 고객,관리자가 데이터베이스에서 상품정보 가져오기
		productList=productDao.bringProduct(); //데이터베이스에서 상품정보 가져오기 
		System.out.println("유나이티드 unit.productList.size() 길이 " + productList.size());
		for(int i=0; i<productList.size(); i++) {
			productNumList.add(productList.get(i).getProductNum());
			productNameList.add(productList.get(i).getProductName());
			productPriceList.add(productList.get(i).getProductPrice());
			productStockList.add(productList.get(i).getStockAmount());
		}
	}
		
	public void basketPrint(Dto dto) {	// 구매하기 위해서 장바구니 정보를 가져옴
		this.dto = dto;
		
		bascketList = basketDao.bringbascket(dto); //자기꺼의 리스트만을 가져왔음
		
		for(int i=0; i<bascketList.size(); i++) {
			bascketProductName.add(bascketList.get(i).getOrderProduct());   //상품이름
			bascketProductPrice.add(bascketList.get(i).getOrderPrice()); //구매가격
			bascketProductAmount.add(bascketList.get(i).getOrderProductAmount()); //구매수량		
		}
	}

	public void purchasePrint(Dto dto) {  //고객이 데이터베이스에서 자기가 구매한 상품정보 가져오기
		this.dto = dto;
		
		purchaseList = purchaseDao.bringPurchaseList(dto);
		for(int i=0; i<purchaseList.size(); i++) {
			purchaseUserID.add(purchaseList.get(i).getPayId());   //구매자 아이디
			purchaseProductName.add(purchaseList.get(i).getPayProductName()); //구매상품명
			purchaseProductPrice.add(purchaseList.get(i).getPayProductPrice()); //구매상품 가격
			pubchaseProductAmount.add(purchaseList.get(i).getPayProductAmount()); //구매상품 수량			
		}
	}

	
	

	//관리자가 쓰는 곳
	

	public void SalesManage() { // 관리자 판매관리
		purchaseList = purchaseDao.bringPurchaseList();
		int a,b;
		for(int i=0; i<purchaseList.size(); i++) {            
			System.out.println("같은 상품명 있는지"+purchaseProductName.contains(purchaseList.get(i).getPayProductName()));
			if(purchaseProductName.contains(purchaseList.get(i).getPayProductName())) {           // 상품명 :
				System.out.println("같은 상품명 있는지2222"+purchaseProductName.contains(purchaseList.get(i).getPayProductName()));
				a = purchaseProductName.indexOf(purchaseList.get(i).getPayProductName());     // 조건1: 기존 상품명이 있다면
				b = pubchaseProductAmount.get(a)+purchaseList.get(i).getPayProductAmount() ; //리스트에 추가 안하고 해당 상품명의 인덱스 번호를 얻어옴
				pubchaseProductAmount.set(a, b);												//있는 상품은 add 하지 않고 해당 인덱스 번호의 상품 수량에 추가
			} else {                                                                             //조건 2: 기존 상품명이 없으면
				purchaseProductName.add(purchaseList.get(i).getPayProductName());				// 그대로 추가 
				purchaseProductPrice.add(purchaseList.get(i).getPayProductPrice()); // 상품가격
				pubchaseProductAmount.add(purchaseList.get(i).getPayProductAmount()); // 판매수량
				
			} 
		}
	}


	public void CustomerManage() { //관리자 고객관리
		userList = userDao.bringMember();

		for(int i=0; i<userList.size(); i++) {
			userIdList.add(userList.get(i).getMemberID());   //유저아이디
			userPwList.add(userList.get(i).getMemberPW()); 
			userPhoneList.add(userList.get(i).getMemberPhone());   //유저아이디
			userPurchaseList.add(userList.get(i).getPurchase()); //구매수량		
		}
	}		
	
	
	
	
			
}
