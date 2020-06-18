package groups;

import java.util.ArrayList;
import java.util.List;

public class Prodotto {
	private String name;
	private String[] Fornitori;
	public Prodotto(String name) {
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setFornitori(String[] F) {
		this.Fornitori = F;
	}
	public String[] getFornitori() {
		return this.Fornitori;
	}
}
