package hospital;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BillUpdateServlet extends HttpServlet {
	Connection con;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Orcl", "system", "munna");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void destroy() {
		// TODO Auto-generated method stub
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String s1= request.getParameter("PAT_ID");
		String s2= request.getParameter("BILL_AMOUNT");
        String s3= request.getParameter("BILL_STATUS");
        String s4= request.getParameter("BILL_FORMAT");
        String s5= request.getParameter("BALANCE");
        PrintWriter pw = response.getWriter();
		pw.println("<html><body style=background-color:cyan color:tomato><center>");
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("update BILL set BILL_AMOUNT=?,BILL_STATUS=?,BILL_FORMAT=? ,BALANCE=? where PAT_ID=?");
			int code = Integer.parseInt(s1);
			int amount = Integer.parseInt(s2);
			int balance = Integer.parseInt(s5);
			pstmt.setInt(1, amount);
			pstmt.setString(2, s3);
			pstmt.setString(3, s4);
			pstmt.setInt(4, balance);
			pstmt.setInt(5, code);
			int rs = pstmt.executeUpdate();
			
			if (rs > 0) {
				pw.println("<h1>BILL UPDATED successfully.</h1>");
			} else {
				pw.println("<h1>Failed to UPDATE BILL data.</h1>");
			}
			pw.println("<a href=welcome.html>Home</a>");
			pw.println("<a href=patient.html>TO ADD NEW PATIENT</a><br>");
			pw.println("<a href=patientlist.html>TO VIEW PATIENT</a><br>");
			pw.println("<a href=billlist.html>TO VIEW BILL</a><br>");
			pw.println("<a href=patientlist.html>view patient details</a><br>");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
