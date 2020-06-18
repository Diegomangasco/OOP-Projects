package warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Product {

	private String code;
	private String description;
	private int quantity;
	private ArrayList<Supplier> Fornitori = new ArrayList<>();
	private ArrayList<Order> Ordini = new ArrayList<>();
	public Product(String code, String description) {
		this.code = code;
		this.description = description;
	}
	public String getCode(){
		// TODO: completare!
		return this.code;
	}

	public String getDescription(){
		// TODO: completare!
		return this.description;
	}
	
	public void setQuantity(int quantity){
		// TODO: completare!
		this.quantity = quantity;
	}

	public void decreaseQuantity(){
		// TODO: completare!
		this.quantity--;
	}

	public int getQuantity(){
		// TODO: completare!
		return this.quantity;
	}

	public void setSupplier(Supplier S) {
		this.Fornitori.add(S);
	}
	public List<Supplier> suppliers(){
		// TODO: completare!
		return this.Fornitori.stream().sorted((S1, S2) -> S1.getNome().compareTo(S2.getNome())).collect(Collectors.toList());
	}

	public void setOrder(Order O) {
		this.Ordini.add(O);
	}
	public List<Order> getAllOrders(){
		return this.Ordini;
	}
	public List<Order> pendingOrders(){
		// TODO: completare
		return this.Ordini.stream().filter(O -> O.delivered()==false)
				.sorted((O1, O2) -> O2.getQuantity() - O1.getQuantity())
				.collect(Collectors.toList());
	}
}
