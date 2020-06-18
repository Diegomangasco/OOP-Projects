package warehouse;

public class Order {
	
	private String code;
	private Product product;
	private Supplier supplier;
	private int quantity;
	private boolean state = false;
	
	public Order(String code, Product P, Supplier S, int quantity) {
		this.code = code;
		this.product = P;
		this.supplier = S;
		this.quantity = quantity;
	}
	public String getCode(){
		// TODO: Completare!
		return this.code;
	}
	public Product getProduct() {
		return this.product;
	}
	public Supplier getSupplier() {
		return this.supplier;
	}
	public int getQuantity() {
		return this.quantity;
	}
	
	public boolean delivered(){
		// TODO: Completare!
		return this.state;
	}

	public void setDelivered() throws MultipleDelivery {
		// TODO: Completare!
		if(this.state==true)
			throw new MultipleDelivery();
		this.state = true;
		this.product.setQuantity(this.quantity);
	}
	
	public String toString(){
		// TODO: Completare!
		return "Order " + this.code + " for " + this.quantity + " of " + this.product.getCode() + " : " + this.product.getDescription()
				+ " from " + this.supplier.getNome();
	}
}
