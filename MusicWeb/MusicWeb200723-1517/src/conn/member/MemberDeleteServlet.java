package conn.member;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaseConn.DBConnection;
import dto.MemberDto;

@WebServlet("/WithdrawServlet")
public class MemberDeleteServlet extends HttpServlet{
	private static final long serialVersionUID = -4274700572038677000L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
						
		HttpSession session = request.getSession();
		MemberDto member = (MemberDto) session.getAttribute("member");
		
		String userID = member.getMemberid();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			 conn = DBConnection.getConnection();
			
			String sql = "delete from music.member where userid=?";  
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			int n = pstmt.executeUpdate();
						
			if(n>0) {
				System.out.println("멤버딜리트서블릿: 탈퇴완료");
				session.invalidate();
				response.sendRedirect("Main.jsp"); //탈퇴완료 후 메인으로 보내기
				}	
			else {
				System.out.println("멤버딜리트서블릿: 탈퇴실패");
				response.sendRedirect("fail.jsp");  } 
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("멤버딜리트서블릿: SQLException ����");
		}finally {
			try {
				if(rs != null) { rs.close(); }
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
			}catch (Exception e) {
				e.getStackTrace();
				System.out.println("멤버딜리트서블릿: 데이터닫기 오류");
			}
		}

	}
}
