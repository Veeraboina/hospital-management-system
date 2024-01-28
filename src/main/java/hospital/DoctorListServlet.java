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
 * Servlet implementation class DoctorListServlet
 */
public class DoctorListServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

	public DoctorListServlet() {
		super();
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
		String s1 = request.getParameter("DOC_CODE");
		String s2= request.getParameter("PAT_DATE");

		try {
			PreparedStatement pstmt = con.prepareStatement("select * from doctor_details dd join patient pt on dd.DOC_CODE=pt.DOC_CODE where dd.DOC_CODE=? and pt.PAT_DATE=?");
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rm=rs.getMetaData();
			int n=rm.getColumnCount();
			PrintWriter pw = response.getWriter();
			pw.println("<html><body style=background-color:cyan color:tomato><center>");
  
			pw.println("<h1>doctor list details .</h1>");
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
					pw.println("<td>" + rs.getString(6) + "</td>");
					pw.println("<td>" + rs.getString(7) + "</td>");
					pw.println("<td>" + rs.getString(8) + "</td>");
					pw.println("<td>" + rs.getString(9) + "</td>");
					pw.println("<td>" + rs.getInt(10) + "</td>");
					pw.println("<td>" + rs.getString(11) + "</td>");
					pw.println("<td>" + rs.getString(12) + "</td>");
					pw.println("<td>" + rs.getString(13) + "</td>");
					pw.println("<td>" + rs.getString(14) + "</td>");
					pw.println("<td>" + rs.getInt(15) + "</td>");
					pw.println("<td>" + rs.getString(16) + "</td>");
					pw.println("</tr>");
				}
		
				pw.println("<h2>if this page shows empty then there is no patient alloted to this doctor</h2>");
			pw.println("</table></center></body></html>");
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
