package libreria;

import java.util.ArrayList;
import java.util.HashMap;

public class Libro {
	
	private String Titolo;
	private String Autore;
	private int Anno;
	private double Prezzo;
	private Editore Edit;
	private int quantity = 0;
	private HashMap<Integer, Integer> copiePerMese = new HashMap<>();
	private HashMap<Integer, Integer> copiePerSettimana = new HashMap<>();
	private ArrayList<Ordine> Ordini = new ArrayList<>();
	private int Soglia;
	private int quantitaRiordino;
	
	public Libro(String Titolo, String Autore, int Anno, double Prezzo, Editore Edit) {
		this.Titolo = Titolo;
		this.Autore = Autore;
		this.Anno = Anno;
		this.Prezzo = Prezzo;
		this.Edit = Edit;
	}
    public String getTitolo(){
        return this.Titolo;
    }
    
    public String getAutore(){
        return this.Autore;
    }
    
    public int getAnno(){
        return this.Anno;
    }

    public double getPrezzo(){
        return this.Prezzo;
    }
    
    public Editore getEditore(){
        return this.Edit;
    }

    public void setQuantita(int q){   
    	this.quantity += q;
    }
    
    public int getQuantita(){
        return this.quantity;	
    }
    
    private static int Numero = 0;
    public static int generateNumber() {
    	Numero++;
    	return Numero;
    }
    
    public HashMap<Integer, Integer> getClassificaMese()
    {
    	return this.copiePerMese;
    }
    public HashMap<Integer, Integer> getClassificaSettimana()
    {
    	return this.copiePerSettimana;
    }
    public ArrayList<Ordine> getOrdini()
    {
    	return this.Ordini;
    }

    public void registraVendita(int settimana, int mese){
    	this.quantity--;
    	if(this.quantity==this.Soglia)
    	{
    		this.Ordini.add(new Ordine(this.Edit, this, this.quantity, generateNumber()));
    	}
    	Integer Mese = Integer.valueOf(mese);
    	Integer Settimana = Integer.valueOf(settimana);
    	if(this.copiePerMese.get(Mese) == null) {
    		this.copiePerMese.put(Mese, Integer.valueOf(1));
    	}
    	else {
    		this.copiePerMese.put(Mese, Integer.valueOf(this.copiePerMese.get(Mese)+1));
    	}
    	if(this.copiePerSettimana.get(Settimana) == null) {
    		this.copiePerSettimana.put(Settimana, Integer.valueOf(1));
    	}
    	else {
    		this.copiePerSettimana.put(Settimana, Integer.valueOf(this.copiePerSettimana.get(Settimana)+1));
    	}
    }
    

    public void setParametri(int soglia, int quantitaRiordino){   
    	this.Soglia = soglia;
    	this.quantitaRiordino = quantitaRiordino;
    }
}
