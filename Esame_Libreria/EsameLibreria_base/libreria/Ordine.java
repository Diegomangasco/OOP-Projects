package libreria;

public class Ordine {
	
	private Editore Editore;
	private Libro Lib;
	private int Quantita;
	private boolean Consegnato;
	private int Numero;
	
	public Ordine(Editore Editore, Libro Lib, int Quantita, int Numero) {
		this.Editore = Editore;
		this.Lib = Lib;
		this.Quantita = Quantita;
		this.Numero = Numero;
	}
	public void setConsegnato() {
		this.Consegnato = true;
	}
    public Editore getEditore(){
        return this.Editore;
    }
    
    public Libro getLibro(){
        return this.Lib;
    }
    
    public int getQuantita(){
        return this.Quantita;
    }

    public boolean isConsegnato(){
        return this.Consegnato;
    }

    public int getNumero(){
        return this.Numero;
    }
}
