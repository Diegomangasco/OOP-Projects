package groups;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Collector;


public class GroupHandling {
//R1	
	private HashMap <String, Prodotto> Prodotti = new HashMap<>();
	private HashMap <String, Fornitore> Fornitori = new HashMap<>();
	private HashMap <String, Gruppo> Gruppi = new HashMap<>();
	public void addProduct (String productTypeName, String... supplierNames) 
			throws GroupHandlingException {
		if(this.Prodotti.containsKey(productTypeName))
			throw new GroupHandlingException();
		Prodotto P = new Prodotto(productTypeName);
		P.setFornitori(supplierNames);
		this.Prodotti.put(productTypeName, P);
		for(String S : supplierNames) {
			if(!this.Fornitori.containsKey(S))
				this.Fornitori.put(S, new Fornitore(S));
		}
	}
	
	public List<String> getProductTypes (String supplierName) {
		return this.Prodotti.values().stream().filter(P -> {
			for(String S : P.getFornitori()) {
				if(S.compareTo(supplierName)==0)
					return true;
			}
			return false;
		}).map(P -> P.getName()).sorted((S1, S2) -> S1.compareTo(S2)).collect(Collectors.toList());
	}
	
//R2	
	public void addGroup (String name, String productName, String... customerNames) 
			throws GroupHandlingException {
		if(this.Gruppi.containsKey(name) || !this.Prodotti.containsKey(productName))
			throw new GroupHandlingException();
		this.Gruppi.put(name, new Gruppo(name, productName, customerNames));
	}
	
	public List<String> getGroups (String customerName) {
        return this.Gruppi.values().stream().filter(G -> {
        	for(String S : G.getClienti()) {
        		if(S.compareTo(customerName)==0)
        			return true;
        	}
        	return false;
        }).map(G -> G.getName()).sorted((S1, S2) -> S1.compareTo(S2)).collect(Collectors.toList());
	}

//R3
	public void setSuppliers (String groupName, String... supplierNames)
			throws GroupHandlingException {
		for(String S : supplierNames) {
			if(!this.Fornitori.containsKey(S))
				throw new GroupHandlingException();
		}
		int n = 0;
		for(String H : supplierNames)
		{
			for(String S : this.Prodotti.get(this.Gruppi.get(groupName).getProductName()).getFornitori()) {
				if(S.compareTo(H)==0) {
					n++;
					break;
				}
			}
		}
		if(n!=supplierNames.length)
			throw new GroupHandlingException();
		this.Gruppi.get(groupName).setFornitori(supplierNames);
	}
	
	public void addBid (String groupName, String supplierName, int price)
			throws GroupHandlingException {
		boolean contains = false;
		for(String S : this.Gruppi.get(groupName).getFornitori())
		{
			if(S.compareTo(supplierName)==0) {
				contains = true;
				break;
			}
		}
		if(contains==false)
			throw new GroupHandlingException();
		this.Gruppi.get(groupName).setOfferta(this.Fornitori.get(supplierName), price);
	}
	
	public String getBids (String groupName) {
        StringBuffer sb = new StringBuffer();
        
        List<String> Net = this.Gruppi.get(groupName).getOfferte().entrySet().stream().sorted((E1, E2) -> {
        	if(E1.getValue().intValue()==E2.getValue().intValue())
        		return E1.getKey().getName().compareTo(E2.getKey().getName());
        	return E1.getValue().intValue() - E2.getValue().intValue();
        }).collect(Collector.of(ArrayList<String>::new, (List<String> a,
        		Entry<Fornitore, Integer> b) -> {a.add(b.getKey().getName() + ":" + b.getValue().intValue());},
        		(List<String> a, List<String> b) -> {a.addAll(b); return a;}, (List<String> a) -> {return a;}));
        
        for(String S : Net) {
        	sb.append(S).append(",");
        }
        return sb.toString();
        
	}
	
	
//R4	
	public void vote (String groupName, String customerName, String supplierName)
			throws GroupHandlingException {
		boolean cliente = false;
		for(String S : this.Gruppi.get(groupName).getClienti()) {
			if(customerName.compareTo(S)==0)
			{
				cliente = true;
				break;
			}	
		}
		if(cliente == false)
			throw new GroupHandlingException();
		
		boolean fornitore = false;
		for(String S : this.Gruppi.get(groupName).getFornitori()) {
			if(supplierName.compareTo(S)==0)
			{
				fornitore = true;
				break;
			}	
		}
		if(fornitore == false)
			throw new GroupHandlingException();
		
		this.Fornitori.get(supplierName).setVoti(this.Gruppi.get(groupName));;
		
	}
	
	public String  getVotes (String groupName) {
        StringBuffer sb = new StringBuffer();
        List<Fornitore> Net = this.Gruppi.get(groupName).getOfferte().keySet().stream().sorted((F1, F2) ->
        	F1.getName().compareTo(F2.getName())
        ).collect(Collectors.toList());
        for(Fornitore F : Net) {
        	if(F.getVoti(this.Gruppi.get(groupName))>0)
        		sb.append(F.getName() + ":" + F.getVoti(this.Gruppi.get(groupName)) + ",");
        }
        return sb.toString();
        
	}
	
	public String getWinningBid (String groupName) {
        Fornitore F = this.Gruppi.get(groupName)
        			.getOfferte()
        			.keySet()
        			.stream()
        			.max((F1, F2) -> {
        				if((F1.getVoti(this.Gruppi.get(groupName)) - F2.getVoti(this.Gruppi.get(groupName)))==0)
        					return - (this.Gruppi.get(groupName).getOfferte().get(F1).intValue() - this.Gruppi.get(groupName).getOfferte().get(F2).intValue());
        				return F1.getVoti(this.Gruppi.get(groupName)) - F2.getVoti(this.Gruppi.get(groupName));
        				})
        			.orElse(null);
        if(F!=null)
        	return F.getName() + ":" + F.getVoti(this.Gruppi.get(groupName));
        else 
        	return null;
	}
	
//R5
	public SortedMap<String, Integer> maxPricePerProductType() { //serve toMap
		return this.Gruppi.values().stream().collect(Collectors.groupingBy(
        		(Gruppo G) -> G.getProductName(), 
        		TreeMap::new, 
        		Collector.of(ArrayList<Gruppo>::new, 
        				(List<Gruppo> a, Gruppo G) -> a.add(G), 
        				(List<Gruppo> a, List<Gruppo> b) -> {a.addAll(b); return a;}, 
        				(List<Gruppo> a) -> {
        					a.sort((G1, G2) -> G2.maxOff().intValue()-G1.maxOff().intValue());
        					return a.get(0).maxOff();
        				})
        		));
        
	}
	
	public SortedMap<Integer, List<String>> suppliersPerNumberOfBids() {
        return this.Fornitori.values().stream().collect(Collectors.groupingBy(
        		(Fornitore F) -> {
        			int n = 0;
        			for(Gruppo G : this.Gruppi.values()) {
        				for(Fornitore Fo : G.getOfferte().keySet()) {
        					if(F.getName().compareTo(Fo.getName())==0)
        						n++;
        				}
        			}
    				return n;
        		}, HashMap::new,
        		Collector.of(ArrayList<String>::new, 
        				(List<String> a, Fornitore F) ->{a.add(F.getName());}, 
        				(List<String> a, List<String> b) -> {a.addAll(b); return a;}, 
        				(List<String> a) -> {a.sort((S1, S2) -> S1.compareTo(S2)); return a;})))
        		.entrySet()
        		.stream()
        		.filter(E -> E.getKey()>0)
        		.collect(Collectors.groupingBy(E -> E.getKey(), () -> {
        			return new TreeMap<>(Collections.reverseOrder());
        			}, Collector.of(ArrayList<String>::new, 
        				(List<String> a, Entry<Integer, List<String>> c) ->{a.addAll(c.getValue());}, 
        				(List<String> a, List<String> b) -> {a.addAll(b); return a;}, 
        				(List<String> a) -> {return a;})));
        		
	}
	
	public SortedMap<String, Long> numberOfCustomersPerProductType() {
        return this.Gruppi.values().stream().collect(Collectors.groupingBy(
        		G -> G.getProductName(),
        		TreeMap::new,
        		Collector.of(ArrayList<Gruppo>::new, 
        				(List<Gruppo> a, Gruppo G) -> {a.add(G);}, 
        				(List<Gruppo> a, List<Gruppo> b) -> {a.addAll(b); return a;}, 
        				(List<Gruppo> a) -> {
        					long l;
        					List<String> b = new ArrayList<>();
        					for(Gruppo G : a)
        					{
        						for(String S : G.getClienti())
        							b.add(S);
        					}
        					l = b.stream().count();
    						return l;
        				})));
	}
	
}

