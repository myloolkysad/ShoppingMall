package AAA;

//
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//오라클이랑 연동, 쿼리문 전달 담당
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
			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
			e.getStackTrace();
		}
	}
	
	//로그인ok
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
//			/*rs.getString("name"): 데이터베이스 쪽의 속성명*/
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
					JOptionPane.showMessageDialog(null, "관리자 로그인 되었습니다."); new AdminMenu();
				}
				else { JOptionPane.showMessageDialog(null, "로그인 되었습니다."); new LoginCustomer(dto); }
			} 
			else { JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 확인해주세요."); dto=null; }
		}
		else { JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 확인해주세요."); dto=null; }
//		if(memberNo==0) { JOptionPane.showMessageDialog(null, "아이디가 존재하지 않습니다.");  }
		} catch(Exception e) {System.out.println("로그인 중 오류"); }
		
    return dto;
	} // end
	
	//회원검색
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
	
	//회원정보 가져오기
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
	

	//상품정보 가져오기ok
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
				/*rs.getString("name"): 데이터베이스 쪽의 속성명*/
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

	//데이터 베이스에서 장바구니 리스트 가져오기 ok
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
	
	
	//데이터 베이스에서 구매리스트 가져오기 ☆★☆★☆★ 회원 Dto를 연결시켜야 함 / 회원꺼만 가져와야함
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


	//DB에 삽입하는 코드
	
	//회원등록ok
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
		} catch (SQLException e) {System.out.println("커넥션 실패");e.printStackTrace();}
		finally { 
			try {
				if(pstmt != null) {pstmt.close();}
				if(conn !=null) {conn.close();}
			} catch(SQLException e) {e.printStackTrace();}
		}
		return n;
	}
	
	//상품등록
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
	
	//장바구니에저장ok
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
	
//	  구매하기☆★☆★☆★ 회원 dto를 연결시켜서 자기꺼만 저장할 수 있도록 해야함 ok
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
		
		//현재 재고에서 구매수량 만큼을 빼고 나머지 수량을 계산해주는 메소드
		public int checkAmount(String payProductName, int payProductAmount) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Dto dtoTemp = new Dto();
			int n = 0;
			try {
				conn = DriverManager.getConnection(url, userid, password); //구매하려는 상품의 재고 가져오기
				String sql = "SELECT * FROM product WHERE PRODUCTNAME = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,payProductName);
				rs = pstmt.executeQuery();
				System.out.println("구매체크어마운트에서 쿼리문 결과값 출력"+rs); // rs가 1개 나와야 함
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
		
		// 구매하면 상품 재고 줄어드는거☆★☆★☆★ ok
		public void minusProduct(String payProductName, int resultStock) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			int n = 0;
			try {
				conn = DriverManager.getConnection(url, userid, password);
				String sql = "update product set stockAmount=? where productName=?";
				pstmt = conn.prepareStatement(sql);
				//원래 수량에서 이거 뺀 수량만큼
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
		
		//구매하고 장바구니에서 목록 삭제
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
		
		//총 구매금액을 업데이트 하는 메소드 - step1. 구매데이터에서 회원 아이디로 금액 가져와서 총금액을 더하기
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
		
		//총 구매금액을 업데이트 하는 메소드 - step2. 구한 총금액을 고객데이터에 업데이트 하기
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
		//장바구니 업데이트
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
		
		
		//장바구니에서 목록 삭제
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
		
	//상품삭제 ok
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
		
		System.out.println("상품삭제하고 n 값" + n);
	}
	
	//상품수정 ok
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
//				/*rs.getString("name"): 데이터베이스 쪽의 속성명*/
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