package warehouse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Collector;

public class Warehouse {
	
	private HashMap<String, Product> Prodotti = new HashMap<>();
	private HashMap<String, Supplier> Fornitori = new HashMap<>();
	private HashMap<String, Order> Ordini = new HashMap<>();
	private int progressive = 0;
	public Product newProduct(String code, String description){
		// TODO: completare
		Product P = new Product(code, description);
		this.Prodotti.put(code, P);
		return P;
	}
	
	public Collection<Product> products(){
		// TODO: completare
		return this.Prodotti.values();
	}

	public Product findProduct(String code){
		// TODO: completare
		return this.Prodotti.get(code);
	}

	public Supplier newSupplier(String code, String name){
		// TODO: completare
		Supplier S = new Supplier(code, name);
		this.Fornitori.put(code, S);
		return S;
	}
	
	public Supplier findSupplier(String code){
		// TODO: completare
		return this.Fornitori.get(code);
	}

	public Order issueOrder(Product prod, int quantity, Supplier supp)
		throws InvalidSupplier {
		// TODO: completare
		if(!supp.supplies().contains(prod))
			throw new InvalidSupplier();
		progressive++;
		String code = "ORD" + progressive;
		Order O = new Order(code, prod, supp, quantity);
		this.Ordini.put(code, O);
		prod.setOrder(O);
		supp.setOrder(O);
		return O;
	}

	public Order findOrder(String code){
		// TODO: completare
		return this.Ordini.get(code);
	}
	
	public List<Order> pendingOrders(){
		// TODO: completare
		return this.Ordini.values().stream()
				.filter(O -> O.delivered()==false).sorted((O1, O2) -> O1.getProduct().getCode().compareTo(O2.getProduct().getCode()))
				.collect(Collectors.toList());
	}

	public Map<String,List<Order>> ordersByProduct(){
	    return this.Ordini.values().stream().collect(Collectors.groupingBy(O -> O.getProduct().getCode(),
	    		HashMap::new,
	    		Collector.of(ArrayList<Order>::new, 
	    				(List<Order> a, Order O) -> {a.add(O);}, 
	    				(List<Order> a, List<Order> b) -> {a.addAll(b); return a;}, 
	    				(List<Order> a) -> {return a;})));
	}
	
	public Map<String,Long> orderNBySupplier(){
	    return this.Ordini.values().stream().filter(O -> O.delivered()==true)
	    		.collect(Collectors.groupingBy(O -> O.getSupplier().getNome(),
	    				TreeMap::new,
	    				Collector.of(ArrayList<Long>::new, 
	    						(List<Long> a, Order O) -> {
	    							a.add(O.getSupplier().deliveryRate().stream().count());
	    						},
	    						(List<Long> a, List<Long> b) -> {a.addAll(b); return a;}, 
	    						(List<Long> a) -> {
	    							long l = 0;
	    							for(Long L : a)
	    								l+=L;
    								return l;
	    						})));
	}
	
	private List<String> Formattatore(Map<String, Integer> M){
		return M.entrySet().stream().sorted((E1, E2) -> {return E2.getValue() - E1.getValue();})
		.collect(Collector.of(ArrayList<String>::new, 
				(List<String> a, Entry<String, Integer> E) -> {a.add(E.getKey() + " - " + E.getValue());},
				(List<String> a, List<String> b) -> {a.addAll(b); return a;},
				(List<String> a) -> {return a;}));
	}
	public List<String> countDeliveredByProduct(){
	    return Formattatore(this.Prodotti.values().stream()
				    		.collect(Collectors.groupingBy((Product P) -> P.getCode(),
				    				HashMap:: new,
				    				Collector.of(ArrayList<Integer>::new, 
				    						(List<Integer> a, Product P) -> {
				    							int n = 0;
						    					for(Order O : P.getAllOrders()) {
						    						if(!P.pendingOrders().contains(O))
						    							n++;
						    					}
						    					a.add(n);
				    						}, 
				    						(List<Integer> a, List<Integer> b) -> {a.addAll(b); return a;}, 
				    						(List<Integer> a) -> {
				    							int n = 0;
				    							for(Integer I : a)
				    								n+=I.intValue();
				    							return n;
				    						}))));
	}
}
