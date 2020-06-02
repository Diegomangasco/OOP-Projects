package abbigliamento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Abbigliamento {
	
	private HashMap <String, Modello> Modelli = new HashMap<>();
	private HashMap <String, Colore> Colori = new HashMap<>();
	private HashMap <String, Materiale> Materiali = new HashMap<>();
	private HashMap <String, Capo> Capi = new HashMap<>();
	private HashMap <String, Collezione> Collezioni = new HashMap<>();
	public void leggiFile(String fileName) throws IOException{
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new IOException();
		}
		int i = 0;
		String linea = bf.readLine();
		while(linea!=null) {
			String [] array = linea.split(";");
			switch(array[0]) {
			case "MOD":
				this.Modelli.put(array[1], new Modello(array[1], Double.valueOf(array[2]).doubleValue(), Double.valueOf(array[3]).doubleValue()));
				break;
			case "COL":
				this.Colori.put(array[1], new Colore(array[1]));
				break;
			case "MAT":
				this.Materiali.put(array[1], new Materiale(array[1], Double.valueOf(array[2]).doubleValue()));
				break;
			case "CAP":
				if(this.Modelli.containsKey(array[2]) && this.Materiali.containsKey(array[3]) && this.Colori.containsKey(array[4]))
					this.Capi.put(array[1], new Capo(this.Modelli.get(array[2]), this.Materiali.get(array[3]), this.Colori.get(array[4])));
				break;
			case "COLL":
				i++;
				Collezione C = new Collezione();
				this.Collezioni.put("Collezione" + i, C);
				for(int j = 2; j<array.length; j++) {
					if(this.Capi.containsKey(array[j])) {
						C.add(this.Capi.get(array[j]));
					}
				}
				break;
			}
			linea = bf.readLine();
		}
		bf.close();
	}

	public Modello getModello(String name){
		return this.Modelli.get(name);
	}

	public Colore getColore(String name){
		return this.Colori.get(name);
	}

	public Materiale getMateriale(String name){
		return this.Materiali.get(name);
	}

	public Capo getCapo(String name){
		return this.Capi.get(name);
	}

	public Collezione getCollezione(String name){
		return this.Collezioni.get(name);
	}

}
