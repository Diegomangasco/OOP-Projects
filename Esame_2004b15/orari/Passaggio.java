package orari;


public class Passaggio {

	private String Stazione;
	private int Ora;
	private int Minuti;
	private int Ritardo;
	public Passaggio(String Stazione, int Ora, int Minuti) {
		this.Stazione = Stazione;
		this.Ora = Ora;
		this.Minuti = Minuti;
	}
	  public String getStazione() {
	    // TODO Auto-generated method stub
	    return this.Stazione;
	  }
	
	  public int getOra() {
	    // TODO Auto-generated method stub
	    return this.Ora;
	  }
	
	  public int getMinuti() {
	    // TODO Auto-generated method stub
	    return this.Minuti;
	  }
	
	  public void setRitardo(int Or, int Min) {
		  this.Ritardo = (this.Ora*60-Or*60) + (this.Minuti-Min);
	  }
	  public int ritardo() {
	    // TODO Auto-generated method stub
	    return this.Ritardo;
	  }

}
