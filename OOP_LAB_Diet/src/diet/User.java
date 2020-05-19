package diet;

/**
 * Represent a take-away system user
 *  
 */
public class User {
		
	/**
	 * get user's last name
	 * @return last name
	 */
	private String nome;
	private String cognome;
	private String email;
	private String telefono;
	
	public User(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
	}
	public String getLastName() {
		return this.cognome;
	}
	
	/**
	 * get user's first name
	 * @return first name
	 */
	public String getFirstName() {
		return this.nome;
	}
	
	/**
	 * get user's email
	 * @return email
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * get user's phone number
	 * @return  phone number
	 */
	public String getPhone() {
		return this.telefono;
	}
	
	/**
	 * change user's email
	 * @param email new email
	 */
	public void SetEmail(String email) {
		this.email = email;
	}
	
	/**
	 * change user's phone number
	 * @param phone new phone number
	 */
	public void setPhone(String phone) {
		this.telefono = phone;
	}
	
}
