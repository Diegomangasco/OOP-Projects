package abbigliamento;

public class Materiale {


	private String nome;
	private double costo;
	public Materiale(String nome, double costo) {		
		// TODO Auto-generated constructor stub
		this.costo = costo;
		this.nome = nome;
	}

	public String getNome(){
		return this.nome;
	}

	public double getCosto(){
		return this.costo;
	}
}
