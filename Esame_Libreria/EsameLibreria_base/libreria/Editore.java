package libreria;

public class Editore {
	
	private String Nome;
	private int TempoConsegna;
	private String Email;
    public Editore(String Nome, int TempoConsegna, String Email) {
    	this.Nome = Nome;
    	this.TempoConsegna = TempoConsegna;
    	this.Email = Email;
    }
    public String getNome(){
        return this.Nome;
    }
    
    public int getTempoConsegna(){
        return this.TempoConsegna;
    }
    
    public String getEmail(){
        return this.Email;
    }
 
}
