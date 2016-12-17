package Store;

import java.util.Date;

public class Order {
	
	private int orderId;
	private String name;
	private String email;
	private Date date;
	private int quantity;
	private int total_qty;
	private double price;
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getTotalQty() {
		return total_qty;
	}
	
	public void setTotalQty(int total_qty) {
		this.total_qty = total_qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Order(int orderId, String name, String email, Date date) {
		super();
		this.orderId = orderId;
		this.name = name;
		this.email = email;
		this.date = date;
		this.quantity = 0;
		this.total_qty = 0;
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
