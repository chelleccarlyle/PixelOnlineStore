package Store;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<StoreItem> cart = (ArrayList<StoreItem>)request.getSession().getAttribute("cart");
		List<StoreItem> r_cart = (ArrayList<StoreItem>)request.getAttribute("cart");
		
		if (cart == null && r_cart == null) // send the user back if there is nothing to check out
			response.sendRedirect("Store");
		else {
			if (cart == null)
				cart = r_cart;
			
			double subtotal = 0;
			
			for (int i=0; i<cart.size(); i++)
				subtotal += cart.get(i).getSelectedQty() * cart.get(i).getPrice();
			
			request.setAttribute("subtotal", subtotal);
			request.getRequestDispatcher("/WEB-INF/Store/Checkout.jsp").forward(request, response);
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		boolean error = false;
		
		if (name != null && name.trim().split(" ").length == 2)
			request.setAttribute("name", name);
		else {
			request.setAttribute("errorName", "Enter only your First and Last names");
			error = true;
		}
		
		if (email != null && email.trim().split("@").length == 2)
			request.setAttribute("email", email);
		else {
			request.setAttribute("errorEmail", "Enter a valid email address");
			error = true;
		}
		
		if (!error) {
			request.setAttribute("confirmed", true);
			
			try {
				String[] credentials = (String[])getServletContext().getAttribute("credentials");
				Connection c = DriverManager.getConnection(credentials[0], credentials[1], credentials[2]);
				
				List<StoreItem> cart = (ArrayList<StoreItem>)request.getSession().getAttribute("cart");
				
				// decrement the item quantities from inventory
				for (int i=0; i<cart.size(); i++)
					c.createStatement().execute("UPDATE items SET quantity=quantity-" + cart.get(i).getSelectedQty() + " WHERE id=" + cart.get(i).getId());
				
				// create the purchase order (now we can show transaction history)
				c.createStatement().execute("CREATE TABLE IF NOT EXISTS orders (id INT UNIQUE NOT NULL AUTO_INCREMENT, name VARCHAR(256) NOT NULL, email VARCHAR(256) NOT NULL, date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, PRIMARY KEY (id))");
				c.createStatement().execute("INSERT INTO orders (name, email) VALUES ('" + name.trim() + "', '" + email.trim() + "')");
				ResultSet rs = c.createStatement().executeQuery("SELECT LAST_INSERT_ID()");
				rs.next();
				int po_id = rs.getInt("LAST_INSERT_ID()");
				c.createStatement().execute("CREATE TABLE IF NOT EXISTS po" + po_id + " (id INT NOT NULL, qty INT NOT NULL, price REAL NOT NULL, PRIMARY KEY (id))");
				
				for (int i=0; i<cart.size(); i++)
					c.createStatement().execute("INSERT INTO po" + po_id + " (id, qty, price) VALUES (" + cart.get(i).getId() + ", " + cart.get(i).getSelectedQty() + ", " + cart.get(i).getPrice() + ")");
				
				c.close();
				
				// remove the cart for good
				request.getSession().removeAttribute("cart");
				// helps with displaying items in the cart one last time (the confirm page)
				request.setAttribute("cart", cart);
				request.setAttribute("id", String.format("%09d", po_id));
			}
			catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		doGet(request, response);
		
	}

}
