package groups;

import java.util.HashMap;

public class Fornitore {

	private String name;
	private HashMap<Gruppo, Integer> Voti = new HashMap<>();
	public Fornitore(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	public void setVoti(Gruppo G) {
		if(this.Voti.containsKey(G))
			this.Voti.put(G, this.Voti.get(G).intValue()+1);
		else 
			this.Voti.put(G, 1);
	}
	public int getVoti(Gruppo G) {
		if(this.Voti.containsKey(G))
			return this.Voti.get(G).intValue();
		return -1;
	}
}
