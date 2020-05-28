package orari;


public class Fermata {

	private String Stazione;
	private int Ore;
	private int Minuti;
	public Fermata(String Stazione, int Ore, int Minuti) {
		this.Stazione = Stazione;
		this.Ore = Ore;
		this.Minuti = Minuti;
	}
	  public String getStazione() {
	    // TODO Auto-generated method stub
	    return this.Stazione;
	  }
	
	  public int getOre() {
	    // TODO Auto-generated method stub
	    return this.Ore;
	  }
	
	  public int getMinuti() {
	    // TODO Auto-generated method stub
	    return this.Minuti;
	  }

}
