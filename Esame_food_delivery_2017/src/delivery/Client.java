package delivery;

public class Client {
	private int Id;
	private String name;
	private String indirizzo;
	private String email;
	private String telefono;
	private static int progressive = 0;
	public Client(String name, String indirizzo, String email, String telefono) {
		this.name = name;
		this.telefono = telefono;
		this.email = email;
		this.indirizzo = indirizzo;
		progressive++;
		this.Id = progressive;
	}
	
	public String getName() {
		return this.name;
	}
	public int getId() {
		return this.Id;
	}
	public String getIndirizzo() {
		return this.indirizzo;
	}
	public String getEmail() {
		return this.email;
	}
	public String getTelefono() {
		return this.telefono;
	}
}
