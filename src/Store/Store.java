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

@WebServlet("/Store")
public class Store extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	List<Integer> itemIds;
	
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
		
		Connection c = null;
		
		//Create list for the items that will be displayed on the store
		List<StoreItem> items = new ArrayList<StoreItem>();
		
		//Get parameter for search
		String query = request.getParameter("query");
		
		try {
			
			String url = "jdbc:mysql://cs3.calstatela.edu:3306/cs3220stu57";
			String username = "cs3220stu57";
			String password = "ezT*7o7x";

			c = DriverManager.getConnection(url, username, password);
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from items order by name");
			
			while (rs.next()) {
				
				if (rs.getInt("quantity") >= 1) {
					
					StoreItem item = new StoreItem(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("quantity"), rs.getFloat("price"));
					items.add(item);	
					
				}	
				
			}
			
			//Go through items list
			//display specific items based on keyword if there is one
			if (query != null && !query.trim().equals("")) {
				
				List<StoreItem> queryItems = new ArrayList<StoreItem>();
				
				for (StoreItem item: items) {
					
					if (item.getName().toLowerCase().contains(query.toLowerCase())) {
						queryItems.add(item);
					}
					
				}
				
				//Replace items with queryItems
				items = queryItems;
				
			}
			
		}	
		catch (SQLException e) {
			throw new ServletException(e);
		}
		finally {
			
			try {
				if (c != null) {
					c.close();
				}	
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			
		}
		
		//Create session for items
		request.setAttribute("items", items);
		
		request.getRequestDispatcher("/WEB-INF/Store/Store.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
