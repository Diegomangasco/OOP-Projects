package abbigliamento;

public class Capo {

	private Modello mod;
	private Materiale mat;
	private Colore col;
	public Capo(Modello modello, Materiale materiale, Colore colore) {
		// TODO Auto-generated constructor stub
		this.col = colore;
		this.mat = materiale;
		this.mod = modello;
	}
	
	public Colore getColore() {
		return this.col;
	}
	public Modello getModello() {
		return this.mod;
	}
	public Materiale getMateriale() {
		return this.mat;
	}
	public double prezzo() {
		return this.mod.getCosto() + (this.mat.getCosto() * this.mod.getQuantita());
	}
	public String toString() {
		return this.mod.getNome() + " " + this.col.getNome() + " di " + this.mat.getNome();
	}

}
