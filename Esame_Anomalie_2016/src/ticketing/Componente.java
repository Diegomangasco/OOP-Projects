package ticketing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Componente {
	private String name;
	private Map<String, Componente> subComponents = new HashMap<>();
	public Componente(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public void addSub(String name) {
		this.subComponents.put(name, new Componente(name));
	}
	public Map<String, Componente> getSub(){
		return this.subComponents;
	}
}
