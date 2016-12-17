package Store;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/RemoveStoreItem")
public class RemoveStoreItem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get id parameter
		int id = Integer.parseInt(request.getParameter("id"));
				
		try {
			
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu57";
			String username = "cs3220stu57";
			String password = "ezT*7o7x";

			Connection c = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = c.prepareStatement("DELETE FROM items WHERE id = ?");
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			pstmt = c.prepareStatement("DELETE FROM items_images WHERE id = ?");
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
				
			c.close();
			
		} 
		catch (SQLException e) {
			throw new ServletException(e);
		}
		
		response.sendRedirect("Inventory");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
