package delivery;

public class Menu {

	private String descrizione;
	private double prezzo;
	private String categoria;
	private int time;
	
	public Menu(String descrizione, double prezzo, String categoria, int time) {
		this.descrizione = descrizione;
		this.categoria = categoria;
		this.prezzo = prezzo;
		this.time = time;
	}
	
	public String getDescrizione() {
		return this.descrizione;
	}
	public String getCategoria() {
		return this.categoria;
	}
	public int getTime() {
		return this.time;
	}
	public double getPrezzo() {
		return this.prezzo;
	}
}
