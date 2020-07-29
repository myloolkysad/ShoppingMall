package AAA;
//공통부분
public class Dto {
	
	private String memberID; //아이디
    private String memberPW; //비밀번호
    private String memberPhone; //전화번호
    private int purchase; //구매금액
    
    private int productNum; //상품번호
    private String productName; //상품명
    private int productPrice; //가격
    private int stockAmount; //재고
    
    private int orderNum; //장바구니번호
    private String orderId; //장바구니아이디
    private String orderProduct; //담은상품명
    private int orderPrice; //담은상품가격
	private int orderProductAmount; //담은 수량
    
    private String payId; //구매자 아이디
    private String payProductName; //구매상품명
    private int payProductPrice; //구매상품가격
    private int payProductAmount; //구매상품수량
    
    
	//고객생성자
    public Dto(String memberID, String memberPW, String memberPhone, int purchase) {
    	this.memberID = memberID;
    	this.memberPW = memberPW;
    	this.memberPhone = memberPhone;
    	this.purchase = purchase;
    }
    //상품생성자
    public Dto(int productNum, String productName, int productPrice, int stockAmount) {
    	this.productNum = productNum;
    	this.productName = productName;
    	this.productPrice = productPrice;
    	this.stockAmount = stockAmount;
    }
    
    //장바구니 생성자
    public Dto(int orderNum, String orderId, String orderProduct, int orderPrice, int orderProductAmount) {
    	this.orderNum = orderNum;
    	this.orderId = orderId;
    	this.orderProduct = orderProduct;
    	this.orderPrice = orderPrice;
    	this.orderProductAmount = orderProductAmount;
    }
    
    //구매목록 생성자
    public Dto(String payId, String payProductName, int payProductPrice, int payProductAmount) {
    	this.payId = payId;
    	this.payProductName = payProductName;
    	this.payProductPrice = payProductPrice;
    	this.payProductAmount = payProductAmount;
    }
    
    public int getOrderProductAmount() {
		return orderProductAmount;
	}
	public void setOrderProductAmount(int orderProductAmount) {
		this.orderProductAmount = orderProductAmount;
	}
    
    public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(String orderProduct) {
		this.orderProduct = orderProduct;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getPayProductName() {
		return payProductName;
	}

	public void setPayProductName(String payProductName) {
		this.payProductName = payProductName;
	}

	public int getPayProductPrice() {
		return payProductPrice;
	}

	public void setPayProductPrice(int payProductPrice) {
		this.payProductPrice = payProductPrice;
	}
	
    
    public Dto() {  }
    
   
    
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getMemberPW() {
		return memberPW;
	}
	public void setMemberPW(String memberPW) {
		this.memberPW = memberPW;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public int getPurchase() {
		return purchase;
	}
	public void setPurchase(int purchase) {
		this.purchase = purchase;
	}
	public int getProductNum() {
		return productNum;
	}
	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public int getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(int stockAmount) {
		this.stockAmount = stockAmount;
	}

	public int getPayProductAmount() {
		return payProductAmount;
	}
	public void setPayProductAmount(int payProductAmount) {
		this.payProductAmount = payProductAmount;
	}

	
}
