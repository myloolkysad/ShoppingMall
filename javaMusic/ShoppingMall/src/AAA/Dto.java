package AAA;
//����κ�
public class Dto {
	
	private String memberID; //���̵�
    private String memberPW; //��й�ȣ
    private String memberPhone; //��ȭ��ȣ
    private int purchase; //���űݾ�
    
    private int productNum; //��ǰ��ȣ
    private String productName; //��ǰ��
    private int productPrice; //����
    private int stockAmount; //���
    
    private int orderNum; //��ٱ��Ϲ�ȣ
    private String orderId; //��ٱ��Ͼ��̵�
    private String orderProduct; //������ǰ��
    private int orderPrice; //������ǰ����
	private int orderProductAmount; //���� ����
    
    private String payId; //������ ���̵�
    private String payProductName; //���Ż�ǰ��
    private int payProductPrice; //���Ż�ǰ����
    private int payProductAmount; //���Ż�ǰ����
    
    
	//��������
    public Dto(String memberID, String memberPW, String memberPhone, int purchase) {
    	this.memberID = memberID;
    	this.memberPW = memberPW;
    	this.memberPhone = memberPhone;
    	this.purchase = purchase;
    }
    //��ǰ������
    public Dto(int productNum, String productName, int productPrice, int stockAmount) {
    	this.productNum = productNum;
    	this.productName = productName;
    	this.productPrice = productPrice;
    	this.stockAmount = stockAmount;
    }
    
    //��ٱ��� ������
    public Dto(int orderNum, String orderId, String orderProduct, int orderPrice, int orderProductAmount) {
    	this.orderNum = orderNum;
    	this.orderId = orderId;
    	this.orderProduct = orderProduct;
    	this.orderPrice = orderPrice;
    	this.orderProductAmount = orderProductAmount;
    }
    
    //���Ÿ�� ������
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
