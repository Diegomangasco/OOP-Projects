package warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Supplier {

	private String code;
	private String nome;
	private ArrayList<Product> Prodotti = new ArrayList<>();
	private ArrayList<Order> Ordini = new ArrayList<>();
	public Supplier(String code, String nome) {
		this.code = code;
		this.nome = nome;
	}
	public String getCodice(){
		// TODO: completare!
		return this.code;
	}

	public String getNome(){
		// TODO: completare!
		return this.nome;
	}
	
	public void newSupply(Product product){
		// TODO: completare!
		this.Prodotti.add(product);
		product.setSupplier(this);
	}
	
	public List<Product> supplies(){
		// TODO: completare!
		return this.Prodotti.stream().sorted((P1, P2) -> P1.getDescription().compareTo(P2.getDescription())).collect(Collectors.toList());
	}
	
	public void setOrder(Order O) {
		this.Ordini.add(O);
	}
	public List<Order> deliveryRate(){
		return this.Ordini.stream().filter(O -> O.delivered()==true).collect(Collectors.toList());
	}
}