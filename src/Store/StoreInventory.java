package Store;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StoreInventory
 */
@WebServlet("/Inventory")
public class StoreInventory extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);

		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu57";
			String username = "cs3220stu57";
			String password = "ezT*7o7x";
			
			String[] credentials = {url, username, password};
			
			getServletContext().setAttribute("credentials", credentials);
			
		} catch (ClassNotFoundException e) {
			throw new ServletException(e);
		}
		
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Create list for the items in the inventory
		List<StoreItem> items = new ArrayList<StoreItem>();
		
		try {
			
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu57";
			String username = "cs3220stu57";
			String password = "ezT*7o7x";

			Connection c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from items order by name");
			
			while (rs.next()) {
				
				StoreItem item = new StoreItem(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("quantity"), rs.getFloat("price"));
				items.add(item);				
				
			}
			
			c.close();
			
		}	
		catch (SQLException e) {
			throw new ServletException(e);
		}
		
		//Create session for items
		request.setAttribute("items", items);
		
		request.getRequestDispatcher("/WEB-INF/Store/Inventory.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
