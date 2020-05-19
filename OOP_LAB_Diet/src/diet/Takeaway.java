package diet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Represents the main class in the
 * take-away system.
 * 
 * It allows adding restaurant, users, and creating orders.
 *
 */
public class Takeaway {

	/**
	 * Adds a new restaurant to the take-away system
	 * 
	 * @param r the restaurant to be added
	 */
	private ArrayList<Restaurant> ristoranti = new ArrayList<>();
	private ArrayList<User> utenti = new ArrayList<>();
	public void addRestaurant(Restaurant r) {
		this.ristoranti.add(r);
	}
	
	/**
	 * Returns the collections of restaurants
	 * 
	 * @return collection of added restaurants
	 */
	public Collection<String> restaurants() {
		ArrayList<String> sb = new ArrayList<>();
		for(Restaurant r : this.ristoranti) {
			sb.add(r.getName());
		}
		return sb;
	}
	
	
	/**
	 * Define a new user
	 * 
	 * @param firstName first name of the user
	 * @param lastName  last name of the user
	 * @param email     email
	 * @param phoneNumber telephone number
	 * @return
	 */
	public User registerUser(String firstName, String lastName, String email, String phoneNumber) {
		User u = new User(firstName, lastName);
		u.SetEmail(email);
		u.setPhone(phoneNumber);
		this.utenti.add(u);
		return u;
	}
	
	/**
	 * Gets the collection of registered users
	 * 
	 * @return the collection of users
	 */
	public Collection<User> users(){
		ArrayList<User> filtrati = new ArrayList<>();
		this.utenti.stream().sorted(new Comparator<User>(){
			public int compare(User a, User b)
			{
				int res;
				if ((res=a.getLastName().compareTo(b.getLastName())) != 0)
					return res;
				else 
					return (a.getFirstName().compareTo(b.getFirstName()));
			}
		}).forEach((r) -> filtrati.add(r));
		return filtrati;
	}
	
	/**
	 * Create a new order by a user to a given restaurant.
	 * 
	 * The order is initially empty and is characterized
	 * by a desired delivery time. 
	 * 
	 * @param user				user object
	 * @param restaurantName	restaurant name
	 * @param h					delivery time hour
	 * @param m					delivery time minutes
	 * @return
	 */
	public Order createOrder(User user, String restaurantName, int h, int m) {
		Ora H = new Ora(h,m);
		boolean ok = false;
		Restaurant Re = null;
		Order O = null;
		for(Restaurant r : this.ristoranti)
		{
			if(r.getName().compareTo(restaurantName)!=0)
				continue;
			else {
				Re = r;
				break;
			}
		}
		for(int i = 0; i<Re.getOrari().size(); i++) {
			if(H.getH()>Re.getOrari().get(i).getHApertura().getH() && 
					H.getH()<Re.getOrari().get(i).getHChiusura().getH()) {
				ok = true;
				break;
			}
			else if(H.getH()==Re.getOrari().get(i).getHApertura().getH() && 
					H.getM()>=Re.getOrari().get(i).getHApertura().getM()) {
				ok = true;
				break;
			}
			else if(H.getH()==Re.getOrari().get(i).getHChiusura().getH() && 
					H.getM()<Re.getOrari().get(i).getHChiusura().getM()) {
				ok = true;
				break;
			}
		}
		if(ok == true)	//orario nel range giusto
			 O = new Order(user, restaurantName, H);
		else 
		{	//assegno all'orario di apertura successivo
			Ora HNew = null;
			for(int i = 0; i<Re.getOrari().size(); i++) {
				if(H.getH()<=Re.getOrari().get(i).getHApertura().getH()) {
					HNew = new Ora(Re.getOrari().get(i).getHApertura().getH(), 
							Re.getOrari().get(i).getHApertura().getM());
				}
			}
			O = new Order(user, restaurantName, HNew);
		}
		for(int i = 0; i<this.ristoranti.size(); i++){
			if(this.ristoranti.get(i).getName().compareTo(restaurantName)==0) {
				this.ristoranti.get(i).setOrdine(O);
				break;
			}
		}
		return O;
	}
	
	/**
	 * Retrieves the collection of restaurant that are open
	 * at the given time.
	 * 
	 * @param time time to check open
	 * 
	 * @return collection of restaurants
	 */
	public Collection<Restaurant> openedRestaurants(String time){
		ArrayList<Restaurant> filtrati = new ArrayList<>();
		String [] minuti = new String[2];
		minuti = time.split(":");
		Integer numero = Integer.parseInt(minuti[0]);
		Integer altroNumero = Integer.parseInt(minuti[1]);
		Stream<Restaurant> st = this.ristoranti.stream().filter(new Predicate<Restaurant>() {
			public boolean test(Restaurant R) {
				for(int i = 0; i<R.getOrari().size(); i++)
				{
					if(R.getOrari().get(i).getHApertura().getH()<numero || 
							(R.getOrari().get(i).getHApertura().getH()==numero &&
							R.getOrari().get(i).getHApertura().getM()<=altroNumero))
					{
						if(R.getOrari().get(i).getHChiusura().getH()>numero) {
							return true;
						}
						else if (R.getOrari().get(i).getHChiusura().getH()<numero
								|| (R.getOrari().get(i).getHChiusura().getH()==numero && 
										R.getOrari().get(i).getHChiusura().getM()<altroNumero))
							return false;
					}
				}
				return false;
			}
		});
		
		st.sorted((r1, r2)->r1.getName().compareTo(r2.getName())).forEach((r) -> filtrati.add(r));
		return filtrati;
	}
	
}
