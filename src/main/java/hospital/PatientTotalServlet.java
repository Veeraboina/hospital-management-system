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
 * Servlet implementation class PatientTotalServlet
 */
public class PatientTotalServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PatientTotalServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
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

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//String s1=request.getParameter("PAT_ID");

		try {
			PreparedStatement pstmt = con.prepareStatement(
					"select * from patient pt join patient_diagnosis pd on pt.PAT_ID=pd.PAT_ID join BILL bl on pt.PAT_ID=bl.PAT_ID ");
//			pstmt.setString(1, s1);
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
			pw.println("<a href=welcome.html>receiptionist page</a>");
			pw.println("</table></center></body></html>");
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
