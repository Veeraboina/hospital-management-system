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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginlistServlet
 */
public class LoginlistServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginlistServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s1 = request.getParameter("PAT_ID");

		try {
			PreparedStatement pstmt = con.prepareStatement(
					"select * from patient pt join patient_diagnosis pd on pt.PAT_ID=pd.PAT_ID join BILL bl on pt.PAT_ID=bl.PAT_ID where pt.PAT_ID=?");
			pstmt.setString(1, s1);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rm = rs.getMetaData();
			int n = rm.getColumnCount();
			PrintWriter pw = response.getWriter();
			pw.println("<html><body style=background-color:cyan color:tomato><center>");
			pw.println("<h1>Patient list details .</h1>");
			
			pw.println("<table border=1>");
				pw.println("<tr>");
				for (int i = 1; i <= n; i++) {
					pw.println("<th>" + rm.getColumnName(i) + "</th>");
				}
				pw.println("</tr>");
				while (rs.next()) {
					pw.println("<tr><td>" + rs.getInt(1) + "</td>");
					pw.println("<td>" + rs.getString(2) + "</td>");
					pw.println("<td>" + rs.getString(3) + "</td>");
					pw.println("<td>" + rs.getString(4) + "</td>");
					pw.println("<td>" + rs.getString(5) + "</td>");
					pw.println("<td>" + rs.getInt(6) + "</td>");
					pw.println("<td>" + rs.getString(7) + "</td>");
					pw.println("<td>" + rs.getInt(8) + "</td>");
					pw.println("<td>" + rs.getString(9) + "</td>");
					pw.println("<td>" + rs.getString(10) + "</td>");
					pw.println("<td>" + rs.getString(11) + "</td>");
					pw.println("<td>" + rs.getString(12) + "</td>");
					pw.println("<td>" + rs.getInt(13) + "</td>");
					pw.println("<td>" + rs.getInt(14) + "</td>");
					pw.println("<td>" + rs.getString(15) + "</td>");
					pw.println("<td>" + rs.getString(16) + "</td>");
					pw.println("<td>" + rs.getInt(17) + "</td>");
					pw.println("</tr>");
				}
				pw.println("<P>if this page show empty then fill the diagnosis and bill details on this ID</p>");
				pw.println("<a href=welcome.html>Home</a>");
				pw.println("<br>");
				pw.println("<a href=diagnosishtml.html>TO ADD AGAIN NEW DIAGNOSIS DETAILS OF PATIENT</a>");
				pw.println("<br>");
				pw.println("<a href=patientlist.html> view Patient details</a>");
				pw.println("<br>");
				pw.println("<a href=bill.html> TO ADD BILL details</a>");
				pw.println("<br>");
				pw.println("<a href=diagnosislist.html>view diagnosis list details</a>");
				pw.println("<br>");
				pw.println("<a href=billlist.html>view bill list details</a>");
			pw.println("</table></center></body></html>");
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
