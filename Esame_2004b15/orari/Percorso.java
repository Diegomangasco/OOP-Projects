package orari;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class Percorso {

	private String Codice;
	private String Categoria;
	private boolean Straordinario = false;
	private ArrayList<Fermata> Fermate = new ArrayList<>();
	private ArrayList<Treno> Treni = new ArrayList<>();
	public Percorso(String Codice, String Categoria) {
		this.Categoria = Categoria;
		this.Codice = Codice;
	}
	
	 public String getCodice() {
	    // TODO Auto-generated method stub
	    return this.Codice;
	  }
	
	  public String getCategoria() {
	    // TODO Auto-generated method stub
	    return this.Categoria;
	  }
	
	  public boolean isStraordinario() {
	    // TODO Auto-generated method stub
	    return this.Straordinario;
	  }
	
	  public void setStraordinario(boolean b) {
	    // TODO Auto-generated method stub
		  this.Straordinario = b;
	    
	  }
	
	  public Fermata aggiungiFermata(String nomeStazione, int ore, int minuti) {
	    // TODO Auto-generated method stub
		  Fermata F = new Fermata(nomeStazione, ore, minuti);
		  this.Fermate.add(F);
	    return F;
	  }
	
	  public List<Fermata> getFermate() {
	    // TODO Auto-generated method stub
	    this.Fermate.sort(new Comparator<Fermata>() {
	    	@Override
	    	public int compare(Fermata F1, Fermata F2) {
	    		if(F1.getOre()>F2.getOre()) {
	    			return 1;
	    		}
	    		else {
	    			if(F1.getOre()==F2.getOre()) {
	    				if(F1.getMinuti()>F2.getMinuti()) {
	    					return 1;
	    				}
	    				else if (F1.getMinuti()==F2.getMinuti()) {
	    					return 0;
	    				}
	    			}
	    			else
    					return -1;
	    		}
	    		return -1;
	    	}
	    });
	    return this.Fermate;
	  }
	
	  public int ritardoMassimo() {
	    // TODO Auto-generated method stub
		  
		  return this.Treni.stream().max(new Comparator<Treno>() {
			  @Override
			  public int compare(Treno T1, Treno T2) {
				  if(T1.ritardoMassimo()>=T2.ritardoMassimo())
					  return 1;
				  else
					  return -1;
			  }
		  }).get().ritardoMassimo();
	  }
	
	  public int ritardoMedio() {
	    // TODO Auto-generated method stub
		  int somma = 0;
		  int i = 0;
		  for(Treno T : this.Treni) {
			  somma += T.getPassaggi().values().stream().collect(Collectors.summingInt((P -> P.ritardo())));
			  i += T.getPassaggi().size();
		  }
		  System.out.println((double)somma/i);
		  return somma/i;
	  }
	  
	  public void aggiungiTreno(Treno T) {
		  this.Treni.add(T);
	  }

}
