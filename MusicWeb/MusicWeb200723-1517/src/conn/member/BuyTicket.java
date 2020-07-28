package conn.member;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaseConn.DBConnection;
import dto.MemberDto;

@WebServlet("/BuyTicket")
public class BuyTicket extends HttpServlet{
	private static final long serialVersionUID = -4274700572038677000L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String item = request.getParameter("item");
		System.out.println("바이티켓 아이템 값"+item);
		
		HttpSession session = request.getSession();
		MemberDto member = (MemberDto) session.getAttribute("member");
		
		String ticket = member.getMemberFreeTicket();
		
		if(ticket.equals("이용권 미이용")) {
			System.out.println("바이티켓 미이용 진입");
			new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			ticket = df.format(cal.getTime());
		}
		LocalDate date = LocalDate.parse(ticket);
		
		if(item.equals("1")) {
			date = date.plusMonths(1);
		}
		else if(item.equals("2")) {
			date = date.plusMonths(6);
		}
		else if(item.equals("3")) {
			date = date.plusYears(1);
		}
		
		System.out.println("바이티켓 date 값: "+date);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			 conn = DBConnection.getConnection();
			
			String sql = "update music.member set userFreeTicket=? where userid=?";  
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date.toString());
			pstmt.setString(2, member.getMemberid());
			int n = pstmt.executeUpdate();
			
			if(n>0) {
				System.out.println("바이티켓서블릿: 티켓구매 완료");
				
				member.setMemberFreeTicket(date.toString());
//					RequestDispatcher rd = request.getRequestDispatcher("suc.jsp");
				session.setAttribute("member", member);
				response.sendRedirect("Mypage.jsp");
			}
			else { response.sendRedirect("fail.jsp");  } 
		} catch (ClassNotFoundException e) {
			System.out.println("바이티켓서블릿: 티켓구매실패");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("바이티켓서블릿: 티켓구매실패");
		}	finally {
			try {
				if(rs != null) { rs.close(); }
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
			}catch (Exception e) {
				e.getStackTrace();
				System.out.println("바이티켓서블릿: 데이터닫기 실패");
			}
		}

	}
}
