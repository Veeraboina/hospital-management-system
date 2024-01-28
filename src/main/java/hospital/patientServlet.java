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

public class patientServlet extends HttpServlet {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String s1 = request.getParameter("PAT_ID");
			String s2 = request.getParameter("PAT_NAME");
			String s3 = request.getParameter("PAT_GENDER");
			String s4 = request.getParameter("PAT_ADDRESS");
			String s5 = request.getParameter("PAT_NUMBER");
			String s6 = request.getParameter("DOC_CODE");
			String s7 = request.getParameter("PAT_DATE");

			PreparedStatement pstmt = con.prepareStatement("select * from patient where PAT_ID=?");
			pstmt.setString(1, s1);
			ResultSet resultSet = pstmt.executeQuery();
			PrintWriter pw = response.getWriter();
			pw.println("<html><body style=background-color:cyan color:tomato><center>");
			if (resultSet.next()) {
				pw.println("<h1>PAT_ID is already present.</h1>");
			} else {
				PreparedStatement pstmt1 = con.prepareStatement(
						"insert into patient (PAT_ID,PAT_NAME,PAT_GENDER,PAT_ADDRESS,PAT_NUMBER,DOC_CODE,PAT_DATE)values(?,?,?,?,?,?,?)");
				int patid = Integer.parseInt(s1);
//				int number = Integer.parseInt(s5);
				int DOC_CODE = Integer.parseInt(s6);

				pstmt1.setInt(1, patid);
				pstmt1.setString(2, s2);
				pstmt1.setString(3, s3);
				pstmt1.setString(4, s4);
				pstmt1.setString(5,s5);
				pstmt1.setInt(6, DOC_CODE);
				pstmt1.setString(7,s7);
				int rowsInserted = pstmt1.executeUpdate();

				if (rowsInserted > 0) {
					pw.println("<h1>patient data inserted successfully.</h1>");
				} else {
					pw.println("<h1>Failed to insert patient data.</h1>");
				}
			}
			pw.println("<a href=welcome.html>Home</a>");
			pw.println("<a href=patient.html>TO ADD NEW PATIENT</a>");
			pw.println("<a href=patientlist.html>view patient details</a>");
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
