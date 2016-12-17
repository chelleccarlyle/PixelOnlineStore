package Store;

public class StoreItem {

	int id;
	String name;
	String description;
	int quantity;
	int selected_qty;
	float price;
	
	public StoreItem(int id, String name, String description, int quantity, float price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getSelectedQty() {
		return selected_qty;
	}
	
	public void setSelectedQty(int selected_qty) {
		this.selected_qty = selected_qty;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
}
