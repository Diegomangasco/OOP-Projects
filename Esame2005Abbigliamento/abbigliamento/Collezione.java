package abbigliamento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class Collezione {

	private ArrayList <Capo> Capi = new ArrayList<>();
	public void add(Capo capo) {
		// TODO Auto-generated method stub
		this.Capi.add(capo);
		
	}

	public Collection <Capo> trova(Colore colore) {
		// TODO Auto-generated method stub
		return this.Capi.stream()
				.filter((C) -> C.getColore().getNome().compareTo(colore.getNome())==0)
				.collect(Collectors.toList());
	}

	public Collection <Capo> trova(Materiale materiale) {
		// TODO Auto-generated method stub
		return this.Capi.stream()
				.filter((C) -> C.getMateriale().getNome().compareTo(materiale.getNome())==0)
				.collect(Collectors.toList());
	}

	public Collection <Capo> trova(Modello modello) {
		// TODO Auto-generated method stub
		return this.Capi.stream()
				.filter((C) -> C.getModello().getNome().compareTo(modello.getNome())==0)
				.collect(Collectors.toList());
	}

}
