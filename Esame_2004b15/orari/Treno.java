package orari;

import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Treno {

	private Percorso P;
	private int Giorno;
	private int Mese;
	private int Anno;
	private boolean Arrivato;
	private Passaggio PassaggioFinale;
	private HashMap<String, Passaggio> Passaggi = new HashMap<>();
	public Treno(Percorso P, int Giorno, int Mese, int Anno) {
		this.P = P;
		this.Anno = Anno;
		this.Giorno = Giorno;
		this.Mese = Mese;
	}
	  public Percorso getPercorso() {
	    // TODO Auto-generated method stub
	    return this.P;
	  }
	
	  public int getGiorno() {
	    // TODO Auto-generated method stub
	    return this.Giorno;
	  }
	
	  public int getMese() {
	    // TODO Auto-generated method stub
	    return this.Mese;
	  }
	
	  public int getAnno() {
	    // TODO Auto-generated method stub
	    return this.Anno;
	  }
	
	  public Passaggio registraPassaggio(String string, int i, int j) 
	  	throws StazioneNonValida {
	    // TODO Auto-generated method stub
		  boolean T = false;
		  Fermata Fer = null;
		  for(Fermata F : this.P.getFermate()) {
			  if(F.getStazione().compareTo(string)==0) {
				  T=true;
				  Fer = F;
				  break;
			  }
		  }
		  if(T==true) {
			  Passaggio P = new Passaggio(string, i, j);
			  P.setRitardo(Fer.getOre(), Fer.getMinuti());
			  Passaggi.put(string, P);
			  PassaggioFinale = P;
			  return P;
		  }
		  else
			  throw new StazioneNonValida();
	  }
	
	  public boolean arrivato() {
	    // TODO Auto-generated method stub
		 if(this.Passaggi.containsKey(this.P.getFermate().get(this.P.getFermate().size()-1).getStazione()))
			 return true;
		 return false;
	  }
	  public HashMap<String, Passaggio> getPassaggi(){
		  return this.Passaggi;
	  }
	
	  public int ritardoMassimo() {
	    // TODO Auto-generated method stub
		  return this.Passaggi.values().stream().max(new Comparator<Passaggio>(){
			  @Override
			  public int compare(Passaggio P1, Passaggio P2) {
				  if(P1.ritardo()>=P2.ritardo())
					  return P1.ritardo();
				  else
					  return -P2.ritardo();
			  }
		  }).get().ritardo();
	  }
	
	  public int ritardoFinale() {
	    // TODO Auto-generated method stub
		  return this.PassaggioFinale.ritardo();
	  }

}
