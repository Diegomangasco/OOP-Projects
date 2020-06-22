package restaurantChain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Restaurant {
	private String name;
	private int nTab;
	private int tavoliDisp;
	private int refused = 0;
	private Map<String, Menu> Menu = new HashMap<>();
	private Map<String, Integer> TavoliR = new HashMap<>();
	private Map<String, Integer> Gruppi = new HashMap<>();
	private Map<String, List<Menu>> Ordini = new HashMap<>();
	private ArrayList<String> OrdiniEffettuati = new ArrayList<>();
	private ArrayList<String> OrdiniPagati = new ArrayList<>();
	private double money = 0.0;
	public Restaurant(String name, int nTab) {
		this.name = name;
		this.nTab = nTab;
		this.tavoliDisp = nTab;
	}
	public String getName(){
		return this.name;
	}
	
	public void addMenu(String name, double price) throws InvalidName{
		if(this.Menu.containsKey(name))
			throw new InvalidName();
		this.Menu.put(name, new Menu(name, price));
	}
	
	public int reserve(String name, int persons) throws InvalidName{
		if(this.TavoliR.containsKey(name))
			throw new InvalidName();
		int n = persons/4;
		if(persons%4!=0)
			n++;
		if(n > this.tavoliDisp) {
			this.refused+=persons;
			return 0;
		}
		this.TavoliR.put(name, n);
		this.Gruppi.put(name, persons);
		this.tavoliDisp-=n;
		return n;
		
	}
	
	public int getRefused(){
		return this.refused;
	}
	
	public int getUnusedTables(){
		return this.tavoliDisp;
	}
	
	public boolean order(String name, String... menu) throws InvalidName{
		if(!this.Gruppi.containsKey(name))
			throw new InvalidName();
		List<String> Net = new ArrayList<>();
		for(String S : menu) {
			Net.add(S);
		}
		if(!this.Menu.keySet().containsAll(Net))
			throw new InvalidName();
		this.OrdiniEffettuati.add(name);
		List<Menu> Ret = new ArrayList<>();
		for(String S : menu) {
			Ret.add(this.Menu.get(S));
		}
		this.Ordini.put(name, Ret);
		if(this.Gruppi.get(name) > menu.length)
			return false;
		return true;
		
	}
	
	public List<String> getUnordered(){
		return this.Gruppi.keySet().stream()
				.filter(G -> this.OrdiniEffettuati.contains(G) == false && this.TavoliR.containsKey(G) == true)
				.sorted((G1, G2) -> G1.compareTo(G2))
				.collect(Collectors.toList());
	}
	
	public double pay(String name) throws InvalidName{
		if(!this.TavoliR.containsKey(name))
			throw new InvalidName();
		if(!this.OrdiniEffettuati.contains(name))
			return 0.0;
		double d = 0.0;
		for(Menu M : this.Ordini.get(name)) {
			d+=M.getPrice();
		}
		this.OrdiniPagati.add(name);
		this.money+=d;
		return d;
	}
	
	public List<String> getUnpaid(){
		return this.OrdiniEffettuati.stream()
				.filter(S -> this.OrdiniPagati.contains(S)==false)
				.sorted((S1, S2) -> S1.compareTo(S2))
				.collect(Collectors.toList());
	}
	
	public double getIncome(){
		return this.money;
	}

}
