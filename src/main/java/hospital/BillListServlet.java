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
 * Servlet implementation class BillListServlet
 */
public class BillListServlet extends HttpServlet {
	Connection con;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BillListServlet() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String s1 = request.getParameter("PAT_ID");

		try {
			PreparedStatement pstmt = con.prepareStatement("select * from BILL where PAT_ID=?");
			pstmt.setString(1, s1);
			ResultSet rs = pstmt.executeQuery();
			PrintWriter pw = response.getWriter();
			pw.println("<html><body style=background-color:cyan color:tomato><center>");

			pw.println("<h1>BILL list details .</h1>");
			pw.println("<table border=1>");
			pw.println(
					"<tr><th>PAT_ID</th><th>DIAG_DETAILS</th><th>OPERATION</th><th>OPERATION_STATUS</th><th>DIAG_DATE</th></tr>");
			while (rs.next()) {

				pw.println("<tr><td>" + rs.getInt(1) + "</td>");
				pw.println("<td>" + rs.getInt(2) + "</td>");
				pw.println("<td>" + rs.getString(3) + "</td>");
				pw.println("<td>" + rs.getString(4) + "</td>");
				pw.println("<td>" + rs.getInt(5) + "</td></tr>");

			}
			pw.println("<a href=welcome.html>home page</a>");
			pw.println("</table></center></body></html>");
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
