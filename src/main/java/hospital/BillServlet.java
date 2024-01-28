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
 * Servlet implementation class BillServlet
 */
public class BillServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BillServlet() {
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
		String s1 = request.getParameter("PAT_ID");
		String s2 = request.getParameter("BILL_AMOUNT");
		String s3 = request.getParameter("BILL_STATUS");
		String s4 = request.getParameter("BILL_FORMAT");
		String s5 = request.getParameter("BALANCE");
		try {
			PreparedStatement pstmt = con.prepareStatement("select * from BILL where PAT_ID=?");
			pstmt.setString(1, s1);
			ResultSet resultSet = pstmt.executeQuery();
			PrintWriter pw = response.getWriter();
			pw.println("<html><body style=background-color:cyan color:tomato><center>");
			if (resultSet.next()) {
				pw.println("<h1>PATIENT DIAGNOSIS  is already present.</h1>");
			} else {
				PreparedStatement pstmt1 = con.prepareStatement("insert into BILL values(?,?,?,?,?)");
				int code = Integer.parseInt(s1);
				int amount = Integer.parseInt(s2);
				int balance = Integer.parseInt(s5);

				pstmt1.setInt(1, code);
				pstmt1.setInt(2, amount);
				pstmt1.setString(3, s3);
				pstmt1.setString(4, s4);
				pstmt1.setInt(5, balance);

				int rowsInserted = pstmt1.executeUpdate();

				if (rowsInserted > 0) {
					pw.println("<h1>patient diagnosis data inserted successfully.</h1>");
				} else {
					pw.println("<h2>Failed to insert patient data.</h2>");
				}
			}

			pw.println("<a href=welcome.html>Home</a>");
			pw.println("<br>");
			pw.println("<a href=welcome.html>TO ADD AGAIN NEW DIAGNOSIS DETAILS OF PATIENT</a>");
			pw.println("<br>");
			pw.println("<a href=patientlist.html> view Patient details</a>");
			pw.println("<br>");
			pw.println("<a href=bill.html> TO ADD BILL details</a>");
			pw.println("<br>");
			pw.println("<a href=diagnosislist.html>view diagnosis list details</a>");
			pw.println("<br>");
			pw.println("<a href=billlist.html>view bill list details</a>");
			pw.println("</center></body></html>");
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
 *      response)
 */
