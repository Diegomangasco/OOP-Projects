package managingProperties;

public class Richiesta {
	private int id;
	private String owner;
	private String profession;
	private Appartamento App;
	private String state = "pending";
	private int amount;
	private String professional;
	public Richiesta(int id, String owner, String profession, Appartamento App) {
		this.id = id;
		this.owner = owner;
		this.App = App;
		this.profession = profession;
	}
	
	public String getOwner() {
		return this.owner;
	}
	public Appartamento getApp() {
		return this.App;
	}
	public int getId() {
		return this.id;
	}
	public String getProfession() {
		return this.profession;
	}
	public String getState() {
		return this.state;
	}
	public void stateChanging(String state) {
		this.state = state;
	}
	public void setAmount(int a) {
		this.amount = a;
	}
	public int getAmount() {
		return this.amount;
	}
	
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public String getProfessional(){
		return this.professional;
	}
}
