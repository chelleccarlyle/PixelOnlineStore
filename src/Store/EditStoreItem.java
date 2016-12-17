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


@WebServlet("/EditStoreItem")
public class EditStoreItem extends HttpServlet {
	
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
		
		request.getRequestDispatcher("/WEB-INF/Store/EditStoreItem.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get current id
		int currentId = Integer.parseInt(request.getParameter("id"));
		
		//Get user input
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String quantity = request.getParameter("quantity");
		String price = request.getParameter("price");
		
		//Check for invalid input
		boolean isValid = true;
		
		if (name == null || name.trim().length() <= 0) {
			
			request.setAttribute("errorName", "Please enter a name for the item.");
			isValid = false;
			
		}
		if (description == null || description.trim().length() <= 0) {
			
			request.setAttribute("errorDescription", "Please enter a description of the item.");
			isValid = false;
			
		}
		if (quantity == null || quantity.trim().length() <= 0 || !isPositiveInteger(quantity)) {
			
			request.setAttribute("errorQuantity", "Please enter a valid quantity of the item.");
			isValid = false;
			
		}
		if (price == null || price.trim().length() <= 0 || !isPositiveFloat(price)) {
			
			request.setAttribute("errorPrice", "Please enter a valid price for the item.");
			isValid = false;
			
		}
		
		if (!isValid) {
			
			doGet(request, response);
			return;
			
		}
		
		//Inputs are valid; update item
		try {
			
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu57";
			String username = "cs3220stu57";
			String password = "ezT*7o7x";

			Connection c = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = c.prepareStatement("UPDATE items SET name = ?, description = ?, quantity = ?, price = ? where id = ?");
			
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			pstmt.setInt(3, Integer.parseInt(quantity));
			pstmt.setFloat(4, Float.parseFloat(price));
			pstmt.setInt(5, currentId);
			
			pstmt.executeUpdate();
			
			c.close();
			
		} 
		catch (SQLException e) {
			throw new ServletException(e);
		}
		
		response.sendRedirect("Inventory");
		
	}
	
	//Checks if input is an integer
	public static boolean isPositiveInteger(String s) {
		
	    try { 
	    	
	        int num = Integer.parseInt(s);
	        
	        if (num >= 0) {
	        	return true;
	        }
	        else {
	        	return false;
	        }
	        
	    } 
	    catch(NumberFormatException e) { 
	        return false; 
	    }
	    
	}
	
	//Checks if input is a float
	public static boolean isPositiveFloat(String s) {
		
		try {
			
			float num = Float.parseFloat(s);
			
			if (num >= 0f) {
				return true;
			}
			else {
				return false;
			}
			
			
		}
		catch(NumberFormatException e) {
			return false;
		}
		
	}

}
