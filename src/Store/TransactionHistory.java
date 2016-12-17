package Store;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/History")
public class TransactionHistory extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Create list for the items that will be displayed on the store

		if (request.getParameter("id") == null) {
			try {
				String[] credentials = (String[]) getServletContext().getAttribute("credentials");
				Connection c = DriverManager.getConnection(credentials[0], credentials[1], credentials[2]);
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("select * from orders order by id");
				List<Order> orders = new ArrayList<Order>();

				while (rs.next()) {
					int id = rs.getInt("id");

					Order order = new Order(id, rs.getString("name"), rs.getString("email"), rs.getDate("date"));

					ResultSet rs0 = c.createStatement().executeQuery("SELECT COUNT(*) FROM po" + id);
					rs0.next();

					int qty = rs0.getInt("COUNT(*)");

					rs0 = c.createStatement().executeQuery("SELECT SUM(qty) FROM po" + id);
					rs0.next();

					int totalQty = rs0.getInt("SUM(qty)");

					rs0 = c.createStatement().executeQuery("SELECT SUM(qty*price) FROM po" + id);
					rs0.next();

					double price = rs0.getDouble("SUM(qty*price)");

					order.setQuantity(qty);
					order.setTotalQty(totalQty);
					order.setPrice(price);
					orders.add(order);
				}

				c.close();

				request.setAttribute("orders", orders);
				request.getRequestDispatcher("/WEB-INF/Store/TransactionHistory.jsp").forward(request, response);
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		} else {
			try {
				String[] credentials = (String[]) getServletContext().getAttribute("credentials");
				Connection c = DriverManager.getConnection(credentials[0], credentials[1], credentials[2]);
				int po_id = Integer.parseInt(request.getParameter("id"));

				ResultSet rs = c.createStatement().executeQuery("SELECT * FROM orders WHERE id=" + po_id);
				rs.next();
				String name = rs.getString("name");
				String email = rs.getString("email");
				double subtotal = 0;

				ResultSet rs0 = c.createStatement()
						.executeQuery("select * from po" + Integer.parseInt(request.getParameter("id")));
				List<StoreItem> items = new ArrayList<StoreItem>();

				while (rs0.next()) {
					int id = rs0.getInt("id");

					ResultSet rs1 = c.createStatement().executeQuery("SELECT name FROM items WHERE id=" + id);
					String i_name = "[Deleted Item]";
					if (rs1.next())
						i_name = rs1.getString("name");

					int qty = rs0.getInt("qty");
					double price = rs0.getDouble("price");

					subtotal += qty * price;

					items.add(new StoreItem(id, i_name, "", qty, (float) price));
				}

				c.close();

				request.setAttribute("id", request.getParameter("id"));
				request.setAttribute("name", name);
				request.setAttribute("email", email);
				request.setAttribute("items", items);
				request.setAttribute("subtotal", subtotal);
				request.getRequestDispatcher("/WEB-INF/Store/Transaction.jsp").forward(request, response);
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
