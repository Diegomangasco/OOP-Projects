package abbigliamento;

public class Modello {

	private String nome;
	private double costoFisso;
	private double quantitaTessuto;
	public Modello(String nome, double costoFisso, double quantitaTessuto) {
		// TODO Auto-generated constructor stub
		this.nome = nome;
		this.costoFisso = costoFisso;
		this.quantitaTessuto = quantitaTessuto;
	}

	public String getNome(){
		return this.nome;
	}
	public double getCosto(){
		return this.costoFisso;
	}
	
	public double getQuantita() {
		return this.quantitaTessuto;
	}

}
