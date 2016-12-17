package Store;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Details")
public class ItemDetails extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//Get id parameter
		int id = Integer.parseInt(request.getParameter("id"));
		
		StoreItem currentItem = null;
		
		try {
			
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu57";
			String username = "cs3220stu57";
			String password = "ezT*7o7x";

			Connection c = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = c.prepareStatement("select * from items where id = ?");
			
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				currentItem = new StoreItem(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("quantity"), rs.getFloat("price"));				
			}
			
			c.close();
			
		}	
		catch (SQLException e) {
			throw new ServletException(e);
		}
		
		request.setAttribute("currentItem", currentItem);
		
		request.getRequestDispatcher("/WEB-INF/Store/ItemDetails.jsp").forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
