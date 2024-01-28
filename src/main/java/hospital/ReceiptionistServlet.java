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
 * Servlet implementation class ReceiptionistServlet
 */
public class ReceiptionistServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceiptionistServlet() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String s1 = request.getParameter("NAME");
			String s2 = request.getParameter("ADDRESS");
			String s3 = request.getParameter("R_NUMBER");
			String s4 = request.getParameter("USERNAME");
			String s5 = request.getParameter("PWORD");

			PreparedStatement pstmt = con.prepareStatement("select * from RECEIPTIONIST where USERNAME=?");
			pstmt.setString(1, s4);
			ResultSet resultSet = pstmt.executeQuery();
			PrintWriter pw = response.getWriter();
			pw.println("<html><body style=background-color:cyan color:tomato><center>");
			if (resultSet.next()) {
				pw.println("<h1>you have allready registered  plz login.</h1>");
			} else {
				PreparedStatement pstmt1 = con.prepareStatement("insert into RECEIPTIONIST values(?,?,?,?,?)");

				pstmt1.setString(1, s1);
				pstmt1.setString(2, s2);
				pstmt1.setString(3, s3);
				pstmt1.setString(4, s4);
				pstmt1.setString(5,s5);
				int rowsInserted = pstmt1.executeUpdate();

				if (rowsInserted > 0) {
					pw.println("<h1>you have  successfully Registered.</h1>");
				} else {
					pw.println("<h1>Failed to register.</h1>");
				}
			}
			pw.println("<a href=homepage1.html>Home</a>");
			pw.println("</center></body></html>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
