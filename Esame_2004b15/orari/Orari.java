package orari;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


public class Orari {

	private HashMap<String, Percorso> Percorsi = new HashMap<>();
	private ArrayList<Treno> Treni = new ArrayList<>(); 
	  public Percorso creaPercorso(String codice, String categoria) {
	    // TODO Auto-generated method stub
		  Percorso P = new Percorso(codice, categoria);
		  Percorsi.put(codice, P);
	    return P;
	  }
	
	  public Collection<Percorso> getPercorsi() {
	    // TODO Auto-generated method stub
	    return this.Percorsi.values();
	  }
	
	  public Percorso getPercorso(String codice) {
	    // TODO Auto-generated method stub
		  if(this.Percorsi.containsKey(codice))
			  return this.Percorsi.get(codice);
		  return null;
	  }
	
	  public Treno nuovoTreno(String codice, int giorno, int mese, int anno) 
	  	throws PercorsoNonValido {
	    if(!this.Percorsi.containsKey(codice)) {
	    	throw new PercorsoNonValido();
	    }
	    Treno T = new Treno(this.Percorsi.get(codice), giorno, mese, anno);
	    T.getPercorso().aggiungiTreno(T);
	    return T;
	  }
	
	  public List<Treno> getTreni() {
	    // TODO Auto-generated method stub
	    this.Treni.sort(new Comparator<Treno>() {
	    	@Override
	    	public int compare(Treno T1, Treno T2) {
	    		if(T1.getAnno()>T2.getAnno())
	    		{
	    			return -1;
	    		}
	    		else if (T1.getAnno()==T2.getAnno()) {
	    			if(T1.getMese()>T2.getMese()) {
	    				return -1;
	    			}
	    			else if(T1.getMese() == T2.getMese()) {
	    				if(T1.getGiorno()>T2.getGiorno()) {
		    				return -1;
		    			}
	    				else if(T1.getGiorno()==T2.getGiorno()) {
	    					return 0;
	    				}
	    				else return 1;
	    			}
	    			else return 1;
	    		}
	    		return 1;
	    	}
	    });
	    return this.Treni;
	  }

}
