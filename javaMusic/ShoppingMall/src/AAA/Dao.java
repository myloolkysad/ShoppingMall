package AAA;

//
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//����Ŭ�̶� ����, ������ ���� ���
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Dao {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "System";
	String password = "1234";
			//
	public Dao() {
		try {
			Class.forName(driver);
			System.out.println("����̹� �ε� ����");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� �ε� ����");
			e.getStackTrace();
		}
	}
	
	//�α���ok
	public Dto loginMember(String userID, String userPW) {
		ArrayList<Dto> list = new ArrayList<Dto>();
		Dto dto = new Dto();
		try {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
		conn = DriverManager.getConnection(url,userid,password);
		String query = "SELECT * FROM member";
		pstmt = conn.prepareStatement(query);
		rs = pstmt.executeQuery();
	
		while(rs.next()) {
			Dto dtoList = new Dto();
		    dtoList.setMemberID(rs.getString("MEMBERID"));
//			/*rs.getString("name"): �����ͺ��̽� ���� �Ӽ���*/
			dtoList.setMemberPW(rs.getString("MEMBERPW"));
			dtoList.setMemberPhone(rs.getString("MEMBERPHONE"));
			dtoList.setPurchase(rs.getInt("purchase"));
			list.add(dtoList);
		}
		}catch (SQLException e) {e.printStackTrace();}
	finally {
		try {
			if(rs != null) { rs.close(); }
			if(pstmt != null) { pstmt.close(); }
			if(conn != null) { conn.close(); }
		} catch (SQLException e) {e.printStackTrace();}
	}

		int memberNo=-1;
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getMemberID().equals(userID)) { memberNo=i; }
		}
		if(memberNo >= 0) {
			dto = (Dto) list.get(memberNo);
			
			if(userPW.equals(dto.getMemberPW())) {
				if(userID.equals("Admin")) { 
					JOptionPane.showMessageDialog(null, "������ �α��� �Ǿ����ϴ�."); new AdminMenu();
				}
				else { JOptionPane.showMessageDialog(null, "�α��� �Ǿ����ϴ�."); new LoginCustomer(dto); }
			} 
			else { JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���."); dto=null; }
		}
		else { JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���."); dto=null; }
//		if(memberNo==0) { JOptionPane.showMessageDialog(null, "���̵� �������� �ʽ��ϴ�.");  }
		} catch(Exception e) {System.out.println("�α��� �� ����"); }
		
    return dto;
	} // end
	
	//ȸ���˻�
	public boolean selectMember(String userID) {
		boolean userCheck = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url,userid,password);
			String query = "SELECT * FROM member where memberID = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,userID);
			rs = pstmt.executeQuery();
			if(rs.next()) { userCheck = true; }
			else { userCheck = false; }
			
		} catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) { rs.close(); }
				if(pstmt != null) { pstmt.close(); }
				if(conn != null) { conn.close(); }
			} catch (SQLException e) {e.printStackTrace();}
		}
		return userCheck;
	}
	
	//ȸ������ ��������
	public ArrayList<Dto> bringMember() {
		ArrayList<Dto> userlist = new ArrayList<Dto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url,userid,password);
			String query = "SELECT * FROM member ORDER BY PURCHASE DESC";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Dto dto = new Dto();
				dto.setMemberID(rs.getString("MEMBERID"));
				dto.setMemberPW(rs.getString("MEMBERPW"));
				dto.setMemberPhone(rs.getString("MEMBERPHONE"));
				dto.setPurchase(rs.getInt("PURCHASE"));
				userlist.add(dto);
			}
		} catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) { rs.close(); }
				if(pstmt != null) { pstmt.close(); }
				if(conn != null) { conn.close(); }
			} catch (SQLException e) {e.printStackTrace();}
		}
		return userlist;
	}
	

	//��ǰ���� ��������ok
	public ArrayList<Dto> bringProduct() {
		ArrayList<Dto> list = new ArrayList<Dto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url,userid,password);
			String query = "SELECT * FROM product ORDER BY PRODUCTNUM";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Dto dto = new Dto();
				dto.setProductNum(rs.getInt("productNum"));
				/*rs.getString("name"): �����ͺ��̽� ���� �Ӽ���*/
				dto.setProductName(rs.getString("productName"));
				dto.setProductPrice(rs.getInt("productPrice"));
				dto.setStockAmount(rs.getInt("stockAmount"));
				list.add(dto);
			}
		} catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) { rs.close(); }
				if(pstmt != null) { pstmt.close(); }
				if(conn != null) { conn.close(); }
			} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}

	//������ ���̽����� ��ٱ��� ����Ʈ �������� ok
	public ArrayList<Dto> bringbascket(Dto dto) {
		ArrayList<Dto> list = new ArrayList<Dto>();
		String memberID = dto.getMemberID();
			
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url,userid,password);
			String query = "SELECT * FROM bascket where orderId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,memberID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Dto tempdto = new Dto();
				tempdto.setOrderId(rs.getString("orderId"));
				tempdto.setOrderProduct(rs.getString("orderProduct"));
				tempdto.setOrderPrice(rs.getInt("orderPrice"));
				tempdto.setOrderProductAmount(rs.getInt("orderProductAmount"));
				list.add(tempdto);
			}
		} catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) { rs.close(); }
				if(pstmt != null) { pstmt.close(); }
				if(conn != null) { conn.close(); }
			} catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	
	
	//������ ���̽����� ���Ÿ���Ʈ �������� �١ڡ١ڡ١� ȸ�� Dto�� ������Ѿ� �� / ȸ������ �����;���
		public ArrayList<Dto> bringPurchaseList(Dto dto) {
			ArrayList<Dto> list = new ArrayList<Dto>();
			String memberID = dto.getMemberID();
			
			System.out.println(memberID);
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DriverManager.getConnection(url,userid,password);
				String query = "SELECT * FROM payment where payId = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1,memberID);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Dto tempdto = new Dto();
					tempdto.setPayId(rs.getString("payId"));
					tempdto.setPayProductName(rs.getString("payProductName"));
					tempdto.setPayProductPrice(rs.getInt("payProductPrice"));
					tempdto.setPayProductAmount(rs.getInt("payProductAmount"));
					list.add(tempdto);
				}
			} catch (SQLException e) {e.printStackTrace();}
			finally {
				try {
					if(rs != null) { rs.close(); }
					if(pstmt != null) { pstmt.close(); }
					if(conn != null) { conn.close(); }
				} catch (SQLException e) {e.printStackTrace();}
			}
			return list;
		}
		
		public ArrayList<Dto> bringPurchaseList() {
			ArrayList<Dto> purchaselist = new ArrayList<Dto>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DriverManager.getConnection(url,userid,password);
				String query = "SELECT * FROM payment ORDER BY PAYPRODUCTPRICE*payproductamount DESC";
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Dto tempdto = new Dto();
					tempdto.setPayId(rs.getString("payId"));
					tempdto.setPayProductName(rs.getString("payProductName"));
					tempdto.setPayProductPrice(rs.getInt("payProductPrice"));
					tempdto.setPayProductAmount(rs.getInt("payProductAmount"));
					purchaselist.add(tempdto);
				}
			} catch (SQLException e) {e.printStackTrace();}
			finally {
				try {
					if(rs != null) { rs.close(); }
					if(pstmt != null) { pstmt.close(); }
					if(conn != null) { conn.close(); }
				} catch (SQLException e) {e.printStackTrace();}
			}
			
			return purchaselist;
		}


	//DB�� �����ϴ� �ڵ�
	
	//ȸ�����ok
	public int insertMember(String memberID, String memberPW, String memberPhone) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int n = 0;
		try {
			conn = DriverManager.getConnection(url, userid, password);
			String sql = "insert into member (memberID, memberPW, memberPhone,PURCHASE) values (?,?,?,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,memberID);
			pstmt.setString(2,memberPW);
			pstmt.setString(3,memberPhone);
			n = pstmt.executeUpdate();
		} catch (SQLException e) {System.out.println("Ŀ�ؼ� ����");e.printStackTrace();}
		finally { 
			try {
				if(pstmt != null) {pstmt.close();}
				if(conn !=null) {conn.close();}
			} catch(SQLException e) {e.printStackTrace();}
		}
		return n;
	}
	
	//��ǰ���
	public void insertProduct(String productNum, String productName, String productPrice, String stockAmount) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, userid, password);
			String sql = "insert into product (productNum, productName, productPrice, stockAmount) values (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,Integer.parseInt(productNum));
			pstmt.setString(2,productName);
			pstmt.setInt(3,Integer.parseInt(productPrice));
			pstmt.setInt(4,Integer.parseInt(stockAmount));
			pstmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		finally { 
			try {
				if(pstmt != null) {pstmt.close();}
				if(conn !=null) {conn.close();}
			} catch(SQLException e) {e.printStackTrace();}
		}
	}
	
	//��ٱ��Ͽ�����ok
	public int insertBasket(String orderId, String orderProduct, int orderPrice, int orderProductAmount) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int n = 0;
		try {
			conn = DriverManager.getConnection(url, userid, password);
			String sql = "insert into bascket (orderId, orderProduct, orderPrice, orderProductAmount) values (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,orderId);
			pstmt.setString(2,orderProduct);
			pstmt.setInt(3,orderPrice);
			pstmt.setInt(4,orderProductAmount);
			n = pstmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();}
		finally { 
			try {
				if(pstmt != null) {pstmt.close();}
				if(conn !=null) {conn.close();}
			} catch(SQLException e) {e.printStackTrace();}
		}
		return n;
	}
	
//	  �����ϱ�١ڡ١ڡ١� ȸ�� dto�� ������Ѽ� �ڱⲨ�� ������ �� �ֵ��� �ؾ��� ok
		public int buyProduct(String bascketId, String bascketProductName, int bascketProductPrice, int bascketProductAmount) {
			ArrayList<Dto> list = new ArrayList<Dto>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int n = 0;
			try {
				conn = DriverManager.getConnection(url, userid, password);
				String sql = "insert into payment (payId, payProductName, payProductPrice, payProductAmount) values (?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,bascketId);
				pstmt.setString(2,bascketProductName);
				pstmt.setInt(3,bascketProductPrice);
				pstmt.setInt(4,bascketProductAmount);
				pstmt.executeUpdate();
			} catch (SQLException e) {e.printStackTrace();}
			finally { 
				try {
					if(pstmt != null) {pstmt.close();}
					if(conn !=null) {conn.close();}
				} catch(SQLException e) {e.printStackTrace();}
			}
			int resultStock = this.checkAmount(bascketProductName, bascketProductAmount);
			this.minusProduct(bascketProductName, resultStock);
			return n;
		}
		
		//���� ����� ���ż��� ��ŭ�� ���� ������ ������ ������ִ� �޼ҵ�
		public int checkAmount(String payProductName, int payProductAmount) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Dto dtoTemp = new Dto();
			int n = 0;
			try {
				conn = DriverManager.getConnection(url, userid, password); //�����Ϸ��� ��ǰ�� ��� ��������
				String sql = "SELECT * FROM product WHERE PRODUCTNAME = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,payProductName);
				rs = pstmt.executeQuery();
				System.out.println("����üũ���Ʈ���� ������ ����� ���"+rs); // rs�� 1�� ���;� ��
				while(rs.next()) {
					dtoTemp.setProductNum(rs.getInt("productNum"));
					dtoTemp.setProductName(rs.getString("productName"));
					dtoTemp.setProductPrice(rs.getInt("productPrice"));
					dtoTemp.setStockAmount(rs.getInt("STOCKAMOUNT"));
				}	
			} catch (SQLException e) {e.printStackTrace();}
			finally { 
				try {
					if(pstmt != null) {pstmt.close();}
					if(conn !=null) {conn.close();}
				} catch(SQLException e) {e.printStackTrace();}
			}
			n = dtoTemp.getStockAmount() - payProductAmount; //dto.Tem[
			return n;
		}
		
		// �����ϸ� ��ǰ ��� �پ��°š١ڡ١ڡ١� ok
		public void minusProduct(String payProductName, int resultStock) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int n = 0;
			try {
				conn = DriverManager.getConnection(url, userid, password);
				String sql = "update product set stockAmount=? where productName=?";
				pstmt = conn.prepareStatement(sql);
				//���� �������� �̰� �� ������ŭ
				pstmt.setInt(1,resultStock);
				pstmt.setString(2,payProductName);
				n = pstmt.executeUpdate();
			} catch (SQLException e) {e.printStackTrace();}
			finally { 
				try {
					if(pstmt != null) {pstmt.close();}
					if(conn !=null) {conn.close();}
				} catch(SQLException e) {e.printStackTrace();}
			}
		}
		
		//�����ϰ� ��ٱ��Ͽ��� ��� ����
		public void deletePurchaseProductFromBascket(String purchasId, String PurchaseProduct, int payProductPrice, int payProductAmount) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DriverManager.getConnection(url, userid, password);
				String sql = "delete from BASCKET where ORDERID=? and ORDERPRODUCT=? and ORDERPRICE=? and ORDERPRODUCTAMOUNT=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,purchasId);
				pstmt.setString(2,PurchaseProduct);
				pstmt.setInt(3,payProductPrice);
				pstmt.setInt(4,payProductAmount);
				pstmt.executeUpdate();
			}catch(SQLException e) {e.getStackTrace();}
			finally {
				try {
					if(pstmt != null) {pstmt.close();}
					if(conn != null) {conn.close();}
				}catch(SQLException e) {e.getStackTrace();}
			}
		}
		
		//�� ���űݾ��� ������Ʈ �ϴ� �޼ҵ� - step1. ���ŵ����Ϳ��� ȸ�� ���̵�� �ݾ� �����ͼ� �ѱݾ��� ���ϱ�
		public void updatePurchase(Dto dto) {
			ArrayList<Dto> paymentlist = new ArrayList<Dto>();
			String memberID = dto.getMemberID();
			int totalPurchase = 0;
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DriverManager.getConnection(url,userid,password);
				String query = "SELECT * FROM payment where payId = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1,memberID);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					Dto tempdto = new Dto();
					tempdto.setPayId(rs.getString("payId"));
					tempdto.setPayProductName(rs.getString("payProductName"));
					tempdto.setPayProductPrice(rs.getInt("payProductPrice"));
					tempdto.setPayProductAmount(rs.getInt("payProductAmount"));
					paymentlist.add(tempdto);
				}
			} catch (SQLException e) {e.printStackTrace();}
			finally {
				try {
					if(rs != null) { rs.close(); }
					if(pstmt != null) { pstmt.close(); }
					if(conn != null) { conn.close(); }
				} catch (SQLException e) {e.printStackTrace();}
			}
			for(int i=0; i<paymentlist.size();i++) {
			totalPurchase = totalPurchase + (paymentlist.get(i).getPayProductPrice() * paymentlist.get(i).getPayProductAmount());
			}
			
			this.updatePurchase2(memberID, totalPurchase);
		}
		
		//�� ���űݾ��� ������Ʈ �ϴ� �޼ҵ� - step2. ���� �ѱݾ��� �������Ϳ� ������Ʈ �ϱ�
		public void updatePurchase2(String memberID, int totalPurchase) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DriverManager.getConnection(url, userid, password);
				String sql = "update member set PURCHASE=? where MEMBERID=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,totalPurchase);
				pstmt.setString(2,memberID);
				pstmt.executeUpdate();
			} catch (SQLException e) {e.printStackTrace();}
			finally { 
				try {
					if(pstmt != null) {pstmt.close();}
					if(conn !=null) {conn.close();}
				} catch(SQLException e) {e.printStackTrace();}
			}
		}
		//��ٱ��� ������Ʈ
		public void updateBascket(String memberID, String productName, int updateAmount) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DriverManager.getConnection(url, userid, password);
				String sql = "update BASCKET set ORDERPRODUCTAMOUNT=? where ORDERID=? AND ORDERPRODUCT=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,updateAmount);
				pstmt.setString(2,memberID);
				pstmt.setString(3,productName);
				pstmt.executeUpdate();
			} catch (SQLException e) {e.printStackTrace();}
			finally { 
				try {
					if(pstmt != null) {pstmt.close();}
					if(conn !=null) {conn.close();}
				} catch(SQLException e) {e.printStackTrace();}
			}
		}
		
		
		//��ٱ��Ͽ��� ��� ����
		public void deleteProductFromBascket(String bascketId, String bascketProductName, int bascketProductPrice, int bascketProductAmount) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = DriverManager.getConnection(url, userid, password);
				String sql = "delete from BASCKET where ORDERID=? and ORDERPRODUCT=? and ORDERPRICE=? and ORDERPRODUCTAMOUNT=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,bascketId);
				pstmt.setString(2,bascketProductName);
				pstmt.setInt(3,bascketProductPrice);
				pstmt.setInt(4,bascketProductAmount);
				pstmt.executeUpdate();
			}catch(SQLException e) {e.getStackTrace();}
			finally {
				try {
					if(pstmt != null) {pstmt.close();}
					if(conn != null) {conn.close();}
				}catch(SQLException e) {e.getStackTrace();}
			}
		}
		
	//��ǰ���� ok
	public void deleteProduct(String productName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int n = 0;
		try {
			conn = DriverManager.getConnection(url, userid, password);
			String sql = "delete from PRODUCT where productName=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,productName);
			n = pstmt.executeUpdate();
			
			sql = "delete from bascket where ORDERPRODUCT=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,productName);
			n = pstmt.executeUpdate();
		}catch(SQLException e) {e.getStackTrace();}
		finally {
			try {
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
			}catch(SQLException e) {e.getStackTrace();}
		}
		
		System.out.println("��ǰ�����ϰ� n ��" + n);
	}
	
	//��ǰ���� ok
	public void updateProduct(int productNum, String productName, String productPrice, String stockAmount) {
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url,userid,password);
			String sql = "update product set productNum=?, productName=?, productPrice=?, stockAmount=? where productNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productNum);
			pstmt.setString(2, productName);
			pstmt.setInt(3, Integer.parseInt(productPrice));
			pstmt.setInt(4, Integer.parseInt(stockAmount));
			pstmt.setInt(5, productNum);
			pstmt.executeUpdate();
			
			sql = "update bascket set ORDERPRICE=? where ORDERPRODUCT=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(productPrice));
			pstmt.setString(2, productName);
			pstmt.executeUpdate();
		}catch(SQLException e) {e.getStackTrace();}
		finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			}catch(SQLException e) {e.getStackTrace();}
		}
	}
	
	
//	public Dto search(String name) {
//		Dto dto = new Dto();
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			conn = DriverManager.getConnection(url,userid,password);
//			String sql = "select * from member where name = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, name);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				dto.setMember_number(rs.getString("member_number"));
//				/*rs.getString("name"): �����ͺ��̽� ���� �Ӽ���*/
//				dto.setName(rs.getString("name"));
//				dto.setPassword(rs.getString("password"));
//				dto.setPhone(rs.getString("phone"));
//				dto.setUser_id(rs.getString("user_id"));
//			}
//		}catch(SQLException e) {e.getStackTrace();}
//		finally {
//			try {
//				if(rs != null) {rs.close();}
//				if(pstmt != null) {pstmt.close();}
//				if(conn != null) {conn.close();}
//			}catch(SQLException e) {e.getStackTrace();}
//		}
//		return dto;
//	}
}