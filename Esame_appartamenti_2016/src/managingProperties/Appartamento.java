package managingProperties;

public class Appartamento {

	private String idE;
	private int n;
	
	public Appartamento(String idE, int n) {
		this.idE = idE;
		this.n = n;
	}
	
	public String getIdE() {
		return this.idE;
	}
	public int getN() {
		return this.n;
	}
	
	public String toString() {
		return this.idE + ":" + this.n;
	}
}
