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

/**
 * Servlet implementation class DiagUpdateServlet
 */
public class DiagUpdateServlet extends HttpServlet {
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
		try {
		String s1= request.getParameter("PAT_ID");
		String s2= request.getParameter("DIAG_DETAILS");
        String s3= request.getParameter("OPERATION");
        String s4= request.getParameter("OPERATION_STATUS");
        String s5= request.getParameter("DIAG_DATE");
        PrintWriter pw = response.getWriter();
		pw.println("<html><body style=background-color:cyan color:tomato><center>");
		PreparedStatement pstmt1 = con.prepareStatement("update patient_diagnosis set  DIAG_DETAILS=?,OPERATION=?,OPERATION_STATUS=?, DIAG_DATE=? where PAT_ID=?");
			int code = Integer.parseInt(s1);
			pstmt1.setString(1, s2);
			pstmt1.setString(2, s3);
			pstmt1.setString(3, s4);
			pstmt1.setString(4, s5);
			pstmt1.setInt(5, code);
			int rs = pstmt1.executeUpdate();
			
			if (rs > 0) {
				pw.println("<h1>UPDATED successfully.</h1>");
			} else {
				pw.println("<h1>Failed to UPDATE patient_diagnosis data.</h1>");
			}
			pw.println("<a href=welcome.html>Home</a><br>");
			pw.println("<a href=patient.html>TO ADD NEW PATIENT</a><br>");
			pw.println("<a href=diagnosislist.html>TO view diagnoosis details</a><br>");
			pw.println("<a href=patientlist.html>view patient details</a><br>");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			
        
	}

}
