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
 * Servlet implementation class DiagnosisListServlet1
 */
public class DiagnosisListServlet1 extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DiagnosisListServlet1() {
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
		String s1 = request.getParameter("PAT_ID");

		try {
			PreparedStatement pstmt = con.prepareStatement("select * from patient_diagnosis where PAT_ID=?");
			pstmt.setString(1, s1);
			ResultSet rs = pstmt.executeQuery();
			PrintWriter pw = response.getWriter();
			pw.println("<html><body style=background-color:cyan color:tomato><center>");

			pw.println("<h1>diagnosis list details .</h1>");
			pw.println("<table border=1>");
			pw.println(
					"<tr><th>PAT_ID</th><th>DIAG_DETAILS</th><th>OPERATION</th><th>OPERATION_STATUS</th><th>DIAG_DATE</th></tr>");
			while (rs.next()) {

				pw.println("<tr><td>" + rs.getInt(1) + "</td>");
				pw.println("<td>" + rs.getString(2) + "</td>");
				pw.println("<td>" + rs.getString(3) + "</td>");
				pw.println("<td>" + rs.getString(4) + "</td>");
				pw.println("<td>" + rs.getString(5) + "</td></tr>");

			}
			pw.println("</table></center></body></html>");
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
