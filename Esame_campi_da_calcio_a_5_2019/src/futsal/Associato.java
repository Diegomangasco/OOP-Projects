package futsal;

import java.util.HashMap;
import java.util.Map;

public class Associato {
	private String nome;
	private String cognome;
	private String numTel;
	private Map<Integer, String> Prenotazioni = new HashMap<>();
	public Associato(String nome, String cognome, String numTel) {
		this.nome = nome;
		this.cognome = cognome;
		this.numTel = numTel;
	}
	
	public String getNumTel(){
		return this.numTel;
	}
	public String getNome(){
		return this.nome;
	}
	public String getCognome(){
		return this.cognome;
	}
	
	public void setPrenotato(int campo, String time) {
		this.Prenotazioni.put(campo, time);
	}
	
	public Map<Integer, String> getPrenotazioni(){
		return this.Prenotazioni;
	}
}
