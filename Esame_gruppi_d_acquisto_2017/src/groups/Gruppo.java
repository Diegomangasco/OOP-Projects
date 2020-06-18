package groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gruppo {
	private String name;
	private String productName;
	private List<String>  clienti = new ArrayList<>();
	private List<String>  fornitori = new ArrayList<>();
	private HashMap <Fornitore, Integer> Offerte = new HashMap<>();
	public Gruppo(String name, String productName, String[] clienti) {
		this.name = name;
		this.productName = productName;
		for(String S : clienti) {
			if(!this.clienti.contains(S))
				this.clienti.add(S);
		}
	}
	
	public Integer maxOff() {
		return this.Offerte.values().stream().mapToInt((I1) ->  I1.intValue()).max().orElse(0);
	}
	
	public String getName() {
		return this.name;
	}
	public String getProductName(){
		return this.productName;
	}
	
	public List<String> getClienti() {
		return this.clienti;
	}
	
	public void setFornitori(String[] S) {
		for(String C : S) {
			if(!this.fornitori.contains(C)) {
				this.fornitori.add(C);
			}
		}
	}
	
	public List<String> getFornitori() {
		return this.fornitori;
	}
	
	public void setOfferta(Fornitore F, int i) {
		this.Offerte.put(F, i);
	}
	
	public Map<Fornitore, Integer> getOfferte(){
		return this.Offerte;
	}
}
