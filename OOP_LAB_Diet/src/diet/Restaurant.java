package diet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.Stream;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant in the take-away system
 *
 */
public class Restaurant {
	
	/**
	 * Constructor for a new restaurant.
	 * 
	 * Materials and recipes are taken from
	 * the food object provided as argument.
	 * 
	 * @param name	unique name for the restaurant
	 * @param food	reference food object
	 */
	private String name;
	private Food food;
	private ArrayList<gestOrari> orari = new ArrayList<>();
	private ArrayList<Menu> menu = new ArrayList<>();
	private ArrayList<Order> ordiniPerRistorante = new ArrayList<>();	
	public Restaurant(String name, Food food) {
		this.name = name;
		this.food = food;
	}

	public class gestOrari{
		private Ora hApertura;
		private Ora hChiusura;
		
		public gestOrari(Ora O1, Ora O2) {
			this.hApertura = O1;
			this.hChiusura = O2;
		}
		public Ora getHApertura()
		{
			return this.hApertura;
		}
		public Ora getHChiusura()
		{
			return this.hChiusura;
		}
	}
	
	public void setOrdine(Order O) {
		this.ordiniPerRistorante.add(O);
	}
	/**
	 * gets the name of the restaurant
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * Define opening hours.
	 * 
	 * The opening hours are considered in pairs.
	 * Each pair has the initial time and the final time
	 * of opening intervals.
	 * 
	 * for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00, 
	 * is thoud be called as {@code setHours("08:15", "14:00", "19:00", "00:00")}.
	 * 
	 * @param hm a list of opening hours
	 */
	public void setHours(String ... hm) {
		String [] minuti = new String[2];
		Integer numero = null;
		Integer altroNumero = null;
		int counter = 0;
		Ora OraApertura = null;
		Ora OraChiusura = null;
		
		for(String s : hm) {
			minuti = s.split(":");
			numero = Integer.valueOf(minuti[0]);
			altroNumero = Integer.valueOf(minuti[1]);
			
			if(counter%2==0) {
				OraApertura = new Ora(numero.intValue(), altroNumero.intValue());
				counter++;
				continue;
			}
			if(counter%2!=0)
			{
				OraChiusura = new Ora(numero.intValue(), altroNumero.intValue());
				counter++;
				this.orari.add(new gestOrari(OraApertura, OraChiusura));
			}
		}
	}
	
	public Menu getMenu(String name) {
		for(Menu m : this.menu) {
			if(m.getName().compareTo(name)==0)
				return m;
		}
		return null;
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu m = new Menu(name, this.food);
		this.menu.add(m);
		return m;
	}
	
	public ArrayList<gestOrari> getOrari()
	{
		return this.orari;
	}

	/**
	 * Find all orders for this restaurant with 
	 * the given status.
	 * 
	 * The output is a string formatted as:
	 * <pre>
	 * Napoli, Judi Dench : (19:00):
	 * 	M6->1
	 * Napoli, Ralph Fiennes : (19:00):
	 * 	M1->2
	 * 	M6->1
	 * </pre>
	 * 
	 * The orders are sorted by name of restaurant, name of the user, and delivery time.
	 * 
	 * @param status the status of the searched orders
	 * 
	 * @return the description of orders satisfying the criterion
	 */
	public String ordersWithStatus(OrderStatus status) {
		StringBuffer sb = new StringBuffer();
		this.ordiniPerRistorante.stream().filter((e)->e.getStatus().compareTo(status)==0)
				.sorted(new Comparator<Order>() {
					public int compare(Order O1, Order O2) {
						int n;
						if((n=O1.getUser().getFirstName().compareTo(O2.getUser().getLastName()))!=0)
								return -n;
						return -(O1.getHour().getH()-O2.getHour().getH());
					}
				}).forEach((e)->sb.append(e.toString()));
		return sb.toString();
	}
}
