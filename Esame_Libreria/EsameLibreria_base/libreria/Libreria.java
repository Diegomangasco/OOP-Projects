package libreria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Collection;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class Libreria {
	
	private TreeMap<String, Editore> editori = new TreeMap<>();
	private ArrayList<Libro> libri = new ArrayList<>();
    public Editore creaEditore(String nome, int tempoConsegna, String email){
        Editore E = new Editore(nome, tempoConsegna, email);
        this.editori.put(nome, E);
        return E;
    }

    public Editore getEditore(String nome){
        return this.editori.get(nome);
    }

    public Collection<Editore> getEditori(){
    	ArrayList<Editore> get = new ArrayList<>();
    	for(String s : this.editori.keySet()) {	//ordinamento delle chiavi implicito avendo usato una TreeMap
    		get.add(getEditore(s));
    	}
        return get;
    }

    public Libro creaLibro(String titolo, String autore, int anno, double prezzo, String nomeEditore)
    			throws EditoreInesistente {
    	Libro L = null;
        if(this.editori.get(nomeEditore)==null) {
        	throw new EditoreInesistente();
        }
        else {
        	L = new Libro(titolo,autore,anno,prezzo,this.editori.get(nomeEditore));
        	this.libri.add(L);
        }
        return L;
    }
    
    public Libro getLibro(String autore, String titolo){
        if(autore == null) {
        	for(Libro l : this.libri) {
        		if(titolo.compareTo(l.getTitolo())==0)
        			return l;
        	}
        }
        if(titolo == null) {
        	for(Libro l : this.libri) {
        		if(autore.compareTo(l.getAutore())==0)
        			return l;
        	}
        }
        for(int i = 0; i<this.libri.size(); i++) {
        	if(autore.compareTo(this.libri.get(i).getAutore())==0) {
        		if(titolo.compareTo(this.libri.get(i).getTitolo())==0) {
        			return this.libri.get(i);
        		}
        	}
        }
        return null;
    }
    
    public Collection<Libro> getClassificaSettimana(final int settimana){
       return this.libri.stream().filter(
        							(L) -> L.getClassificaSettimana().
        									get(Integer.valueOf(settimana)).intValue()!=0)
							.sorted(
									(L1, L2) ->	
									-(L1.getClassificaSettimana().get(Integer.valueOf(settimana)).intValue()-
											L2.getClassificaSettimana().get(Integer.valueOf(settimana)).intValue())
									)
							.collect(Collectors.toList());
    }
    
    public Collection<Libro> getClassificaMese(final int mese){
    	return this.libri.stream().filter(
				(L) -> L.getClassificaMese().
						get(Integer.valueOf(mese)).intValue()!=0)
		.sorted(
				(L1, L2) ->	
				-(L1.getClassificaMese().get(Integer.valueOf(mese)).intValue()-
						L2.getClassificaMese().get(Integer.valueOf(mese)).intValue())
				)
		.collect(Collectors.toList());
    }
    
    public Collection<Ordine> getOrdini(){
        return this.libri.stream().flatMap((L) -> L.getOrdini().stream()).collect(Collectors.toList());
    }
    
    public void ordineRicevuto(int numOrdine){
    	this.libri.stream().flatMap((L) -> L.getOrdini().stream()).filter((O) -> O.getNumero()==numOrdine).forEach((O) -> O.setConsegnato());
    	this.libri.stream().flatMap((L) -> L.getOrdini().stream()).filter((O) -> O.getNumero()==numOrdine).forEach((O) -> O.getLibro().setQuantita(O.getQuantita()));
    }
    
    public void leggi(String file) throws IOException, EditoreInesistente{
    	BufferedReader bf;
    	try {
    		bf = new BufferedReader(new FileReader(file));
    	}catch(IOException IO) {
    		throw new IOException();
    	}
    	char[] linea = bf.readLine().toCharArray();
    	String linea2;
    	
    	while(linea!=null) {
    		if(linea[0] == 'E') {
    			linea2 = linea.toString();
    			String [] input = linea2.split(";");
    			this.creaEditore(input[0], Integer.valueOf(input[1]).intValue(), input[2]);
    		}
    		else if (linea[0] == 'L') {
    			linea2 = linea.toString();
    			String [] input = linea2.split(";");
    			try {
    				this.creaLibro(input[0], input[1], Integer.valueOf(input[2]).intValue(), 
    						Double.valueOf(input[3]).doubleValue(), input[4]);
    			}catch(EditoreInesistente E) {
    				System.err.println("ERRORE editore inesistente nel file di input");
    			}
    		}
    		linea = bf.readLine().toCharArray();
    	}
    	bf.close();
    }
}
