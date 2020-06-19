package clinic;

public class Doctor {
	
	private String nome;
	private String cognome;
	private String SSN;
	private int docID;
	private String spec;
	
	public Doctor(String nome, String cognome, String SSN, int docID, String spec) {
		this.nome = nome;
		this.cognome = cognome;
		this.SSN = SSN;
		this.docID = docID;
		this.spec = spec;
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
	
	public String getSpec() {
		return this.spec;
	}
	
	public int getDocID() {
		return this.docID;
	}

}
