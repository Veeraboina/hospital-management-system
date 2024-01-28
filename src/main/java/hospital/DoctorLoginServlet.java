package hospital;

import jakarta.servlet.RequestDispatcher;
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
 * Servlet implementation class DoctorLoginServlet
 */
public class DoctorLoginServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorLoginServlet() {
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
			String s1 = request.getParameter("USERNAME");
			String s2 = request.getParameter("PWORD");
			PreparedStatement pstmt = con.prepareStatement("select * from doctor_details where USERNAME=? and PWORD=?");
			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			ResultSet rs = pstmt.executeQuery();
			PrintWriter pw = response.getWriter();
			pw.println("<html><body bgcolor=red text=red><h1>");
			if (rs.next()) {
//		response.sendRedirect("welcome.html");
				RequestDispatcher rd = request.getRequestDispatcher("doctorview.html");
				rd.forward(request, response);
			} else {
				pw.println("<p>invalid username/password</p>");
				RequestDispatcher rd = request.getRequestDispatcher("doctorlogin.html");
				rd.include(request, response);

			}
			pw.println("</h1></body></html>");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
