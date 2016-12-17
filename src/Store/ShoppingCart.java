package Store;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ShoppingCart")
public class ShoppingCart extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get cart from session
		List<StoreItem> cart = (ArrayList<StoreItem>)request.getSession().getAttribute("cart");
		
		if (cart == null) {
			cart = new ArrayList<StoreItem>();
		}
		
		if (request.getParameter("action") != null && request.getParameter("action").equals("add")) {
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			StoreItem item = null;
			
			for (int i=0; i<cart.size(); i++) {
				StoreItem temp = cart.get(i);
				if (temp.getId() == id) {
					item = temp;
					i = cart.size();
				}
			}
			
			try {
				String[] credentials = (String[])getServletContext().getAttribute("credentials");
				Connection c = DriverManager.getConnection(credentials[0], credentials[1], credentials[2]);
				ResultSet rs = c.createStatement().executeQuery("SELECT * FROM items WHERE id=" + id);
				
				rs.next();
				StoreItem temp = new StoreItem(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("quantity"), rs.getFloat("price"));
				
				if (temp.getQuantity() > 0) {
					if (item == null) {
						item = temp;
						cart.add(item);
					}
					
					item.setSelectedQty(item.getSelectedQty() +1);
				}
				
				c.close();
			} 
			catch (SQLException e) {
				throw new ServletException(e);
			}
			
			request.getSession().setAttribute("cart", cart);
		}
		
		double subtotal = 0;
		
		for (int i=0; i<cart.size(); i++)
			subtotal += cart.get(i).getPrice() * cart.get(i).getSelectedQty();
		
		request.setAttribute("subtotal", subtotal);
		request.getRequestDispatcher("/WEB-INF/Store/ShoppingCart.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		int id = Integer.parseInt(request.getParameter("id"));
		List<StoreItem> cart = (ArrayList<StoreItem>)request.getSession().getAttribute("cart");
		
		if (action.equals("update")) { // gotta query inventory to make sure the quantity is valid, otherwise choose maximum
			try {
				int qty = Integer.parseInt(request.getParameter("selectedQty"));
				StoreItem item = null;
				
				for (int i=0; i<cart.size(); i++) {
					if (cart.get(i).getId() == id) {
						item = cart.get(i);
						i = cart.size();
					}
				}
				
				String[] credentials = (String[])getServletContext().getAttribute("credentials");
				Connection c = DriverManager.getConnection(credentials[0], credentials[1], credentials[2]);
				ResultSet rs = c.createStatement().executeQuery("SELECT * FROM items WHERE id=" + id);
				
				rs.next();
				StoreItem temp = new StoreItem(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getInt("quantity"), rs.getFloat("price"));
				
				if (temp.getQuantity() != item.getQuantity())
					item.setQuantity(temp.getQuantity());
				
				if (item.getQuantity() < qty)
					item.setSelectedQty(item.getQuantity());
				else
					item.setSelectedQty(qty);
				
				c.close();
			}
			catch (Exception e) {
				// ignore errors here since we're not messing with the database
			}
			
			request.getSession().setAttribute("cart", cart);
		}
		else if (action.equals("remove")) {
			for (int i=0; i<cart.size(); i++) {
				if (cart.get(i).getId() == id) {
					cart.remove(i);
					i = cart.size() + 1;
				}
			}
			
			request.getSession().setAttribute("cart", cart);
		}
		
		doGet(request, response);
	}

}
