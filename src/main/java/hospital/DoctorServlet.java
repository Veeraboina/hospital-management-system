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
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class DoctorServlet
 */
public class DoctorServlet extends HttpServlet {
	Connection con;
	
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Orcl", "system", "munna");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String s1 = request.getParameter("DOC_CODE");
		String s2 = request.getParameter("DOC_NAME");
		String s3 = request.getParameter("DOC_GENDER");
		String s4 = request.getParameter("DOC_ADDRESS");
		String s5 = request.getParameter("DOC_DESIGNATION");
		String s6 = request.getParameter("DOC_NUMBER");
		String s7 = request.getParameter("DOC_JOIN_DATE");
		String s8 = request.getParameter("USERNAME");
		String s9 = request.getParameter("PWORD");
		
		try {
			PreparedStatement pstmt = con.prepareStatement("select * from doctor_details where DOC_CODE=?");
			pstmt.setString(1, s1);
			ResultSet resultSet = pstmt.executeQuery();
			PrintWriter pw = response.getWriter();
			pw.println("<html><body style=background-color:cyan color:tomato><center>");
			if (resultSet.next()) {
				pw.println("<h1>doc_code is already present.</h1>");
			} else {
				PreparedStatement pstmt1 = con.prepareStatement("insert into doctor_details(DOC_CODE,DOC_NAME,DOC_GENDER,DOC_ADDRESS,DOC_DESIGNATION,DOC_NUMBER,DOC_JOIN_DATE,USERNAME,PWORD) values(?,?,?,?,?,?,?,?,?)");
				int code = Integer.parseInt(s1);
//				int number = Integer.parseInt(s6);
				pstmt1.setInt(1, code);
				pstmt1.setString(2, s2);
				pstmt1.setString(3, s3);
				pstmt1.setString(4, s4);
				pstmt1.setString(5, s5);
				pstmt1.setString(6, s6);
				pstmt1.setString(7, s7);
				pstmt1.setString(8, s8);
				pstmt1.setString(9, s9);

				int rowsInserted = pstmt1.executeUpdate();

				if (rowsInserted > 0) {
					pw.println("<h1>doctor data inserted successfully.</h1>");
				} else {
					pw.println("<h2>Failed to insert patient data.</h2>");
				}
			}

			pw.println("<a href=homepage.html>Home</a>");
			pw.println("<a href=welcome.html>receiptionaist page</a>");
			pw.println("<a href=patientlist.html> view Patient details</a>");
			pw.println("<a href=doctorlist.html>view doctor details</a>");
			pw.println("</center></body></html>");
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
