package clinic;

public class Patient {

	private String nome;
	private String cognome;
	private String SSN;
	public Patient(String nome, String cognome, String SSN) {
		this.nome = nome;
		this.cognome = cognome;
		this.SSN = SSN;
	}
	
	public String getNome() {
		return this.nome;
	}
	public String getCognome() {
		return this.cognome;
	}

	public String getSSN() {
		return this.SSN;
	}
}
