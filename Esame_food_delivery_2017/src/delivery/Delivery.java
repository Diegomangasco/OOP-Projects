package delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Delivery {
    
    public static enum OrderStatus { NEW, CONFIRMED, PREPARATION, ON_DELIVERY, DELIVERED } 
    private HashMap<Integer, Client> Clienti = new HashMap<>();
    private HashMap<String, Menu> Menu = new HashMap<>();
    private HashMap<Integer, Order> Ordini = new HashMap<>();
    private HashMap<Integer, OrderStatus> OrdiniConStato = new HashMap<>();
    
    /**
     * Creates a new customer entry and returns the corresponding unique ID.
     * 
     * The ID for the first customer must be 1.
     * 
     * 
     * @param name name of the customer
     * @param address customer address
     * @param phone customer phone number
     * @param email customer email
     * @return unique customer ID (positive integer)
     */
    public int newCustomer(String name, String address, String phone, String email) throws DeliveryException {
        for(Integer I : this.Clienti.keySet()) {
        	if(this.Clienti.get(I).getEmail().compareTo(email)==0)
        		throw new DeliveryException();
        }
        Client C = new Client(name, address, email, phone);
        this.Clienti.put(Integer.valueOf(C.getId()), C);
        return C.getId();
    }
    
    /**
     * Returns a string description of the customer in the form:
     * <pre>
     * "NAME, ADDRESS, PHONE, EMAIL"
     * </pre>
     * 
     * @param customerId
     * @return customer description string
     */
    public String customerInfo(int customerId){
        return this.Clienti.get(customerId).getName() + ", " + 
        		this.Clienti.get(customerId).getIndirizzo() + ", " +
        		this.Clienti.get(customerId).getTelefono() + ", " +
        		this.Clienti.get(customerId).getEmail();
    }
    
    /**
     * Returns the list of customers, sorted by name
     * 
     */
    public List<String> listCustomers(){
        return (List<String>) this.Clienti
        		.values()
        		.stream()
        		.sorted((C1, C2) -> C1.getName().compareTo(C2.getName()))
        		.map(C -> customerInfo(C.getId()))
        		.collect(Collectors.toList());
    }
    
    /**
     * Add a new item to the delivery service menu
     * 
     * @param description description of the item (e.g. "Pizza Margherita", "Bunet")
     * @param price price of the item (e.g. 5 EUR)
     * @param category category of the item (e.g. "Main dish", "Dessert")
     * @param prepTime estimate preparation time in minutes
     */
    public void newMenuItem(String description, double price, String category, int prepTime){
        this.Menu.put(description, new Menu(description, price, category, prepTime));
    }
    
    /**
     * Search for items matching the search string.
     * The items are sorted by category first and then by description.
     * 
     * The format of the items is:
     * <pre>
     * "[CATEGORY] DESCRIPTION : PRICE"
     * </pre>
     * 
     * @param search search string
     * @return list of matching items
     */
    public List<String> findItem(String search){
        return this.Menu
        		.entrySet()
        		.stream()
        		.filter(E -> E.getKey().toLowerCase().contains(search.toLowerCase())==true || search.compareTo("")==0)
        		.sorted((E1, E2) -> {
        			int n;
        			if((n = E1.getValue().getCategoria().compareTo(E2.getValue().getCategoria()))==0)
        				return E1.getKey().compareTo(E2.getKey());
        			else
        				return n;
        		})
        		.collect(Collector.of(ArrayList<String>::new, 
        				(List<String> a, Entry<String, Menu> b) -> {a.add(Formattatore(b));}, 
        				(List<String> a, List <String> b) -> {return a;}, (List<String> a) -> {return a;}));
    }
    private String Formattatore(Entry<String, Menu> E) {
    	return "[" + E.getValue().getCategoria() + "]" + " " + E.getValue().getDescrizione() + " : " + String.format("%.2f", E.getValue().getPrezzo());
    }
    
    /**
     * Creates a new order for the given customer.
     * Returns the unique id of the order, a progressive
     * integer number starting at 1.
     * 
     * @param customerId
     * @return order id
     */
    public int newOrder(int customerId){
    	Order O = new Order(customerId);
        this.Ordini.put(Integer.valueOf(O.getId()), O);
        this.OrdiniConStato.put(Integer.valueOf(O.getId()), OrderStatus.NEW);
        return O.getId();
    }
    
    /**
     * Add a new item to the order with the given quantity.
     * 
     * If the same item is already present in the order is adds the
     * given quantity to the current quantity.
     * 
     * The method returns the final total quantity of the item in the order. 
     * 
     * The item is found through the search string as in {@link #findItem}.
     * If none or more than one item is matched, then an exception is thrown.
     * 
     * @param orderId the id of the order
     * @param search the item search string
     * @param qty the quantity of the item to be added
     * @return the final quantity of the item in the order
     * @throws DeliveryException in case of non unique match or invalid order ID
     */
    public int addItem(int orderId, String search, int qty) throws DeliveryException {
        if(!this.Ordini.containsKey(orderId))
        	throw new DeliveryException();
        int pres = 0;
        Menu M = null;
        for(String S : this.Menu.keySet()) {
        	if(S.toLowerCase().contains(search.toLowerCase())) {
        		pres++;
        		M = this.Menu.get(S);
        	}
        }
        if(pres!=1)
        	throw new DeliveryException();
        this.Ordini.get(orderId).setMenu(M, qty);
        return this.Ordini.get(orderId).getQuantity(M);
        
    }
    
    /**
     * Show the items of the order using the following format
     * <pre>
     * "DESCRIPTION, QUANTITY"
     * </pre>
     * 
     * @param orderId the order ID
     * @return the list of items in the order
     * @throws DeliveryException when the order ID in invalid
     */
    public List<String> showOrder(int orderId) throws DeliveryException {
        return this.Ordini
        		.values()
        		.stream()
        		.filter(O -> O.getId()==orderId)
        		.map(O -> O.getMenu())
        		.flatMap(M -> M.entrySet().stream())
        		.collect(Collector.of(ArrayList<String>::new, 
        				(List<String> a, Entry<Menu, Integer> e) -> {a.add(e.getKey().getDescrizione() + ", " + e.getValue().intValue());},
        				(List<String> a, List<String> b) -> {a.addAll(b); return a;}, 
        				(List<String> a) -> {return a;}));
    }
    
    /**
     * Retrieves the total amount of the order.
     * 
     * @param orderId the order ID
     * @return the total amount of the order
     * @throws DeliveryException when the order ID in invalid
     */
    public double totalOrder(int orderId) throws DeliveryException {
        if(!this.Ordini.containsKey(orderId))
        	throw new DeliveryException();
        double sum = 0.0;
        for(Menu M : this.Ordini.get(orderId).getMenu().keySet()) {
        	sum += M.getPrezzo()*this.Ordini.get(orderId).getQuantity(M);
        }
        return sum;
    }
    
    /**
     * Retrieves the status of an order
     * 
     * @param orderId the order ID
     * @return the current status of the order
     * @throws DeliveryException when the id is invalid
     */
    public OrderStatus getStatus(int orderId) throws DeliveryException {
    	if(!this.Ordini.containsKey(orderId))
    		throw new DeliveryException();
        return this.OrdiniConStato.get(orderId);
    }
    
    /**
     * Confirm the order. The status goes from {@code NEW} to {@code CONFIRMED}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>start-up delay (conventionally 5 min)
     * <li>preparation time (max of all item preparation time)
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code NEW}
     */
    public int confirm(int orderId) throws DeliveryException {
        if(!this.Ordini.containsKey(orderId) || this.OrdiniConStato.get(orderId)!=OrderStatus.NEW)
        	throw new DeliveryException();
        this.OrdiniConStato.put(orderId, OrderStatus.CONFIRMED);
        return 5 + 15 + this.Ordini
        		.get(orderId)
        		.getMenu()
        		.keySet()
        		.stream()
        		.map(M -> M.getTime())
        		.max((T1, T2) -> {return T1 - T2;})
        		.get()
        		.intValue();
    }

    /**
     * Start the order preparation. The status goes from {@code CONFIRMED} to {@code PREPARATION}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>preparation time (max of all item preparation time)
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code CONFIRMED}
     */
    public int start(int orderId) throws DeliveryException {
    	if(!this.Ordini.containsKey(orderId) || this.OrdiniConStato.get(orderId)!=OrderStatus.CONFIRMED)
        	throw new DeliveryException();
    	this.OrdiniConStato.put(orderId, OrderStatus.PREPARATION);
        return  15 + this.Ordini
        		.get(orderId)
        		.getMenu()
        		.keySet()
        		.stream()
        		.map(M -> M.getTime())
        		.max((T1, T2) -> {return T1 - T2;})
        		.get()
        		.intValue();
    }

    /**
     * Begins the order delivery. The status goes from {@code PREPARATION} to {@code ON_DELIVERY}
     * 
     * Returns the delivery time estimate computed as the sum of:
     * <ul>
     * <li>transportation time (conventionally 15 min)
     * </ul>
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code PREPARATION}
     */
    public int deliver(int orderId) throws DeliveryException {
    	if(!this.Ordini.containsKey(orderId) || this.OrdiniConStato.get(orderId)!=OrderStatus.PREPARATION)
    		throw new DeliveryException();
    	this.OrdiniConStato.put(orderId, OrderStatus.ON_DELIVERY);
    	return  this.Ordini
        		.get(orderId)
        		.getMenu()
        		.keySet()
        		.stream()
        		.map(M -> M.getTime())
        		.max((T1, T2) -> {return T1 - T2;})
        		.get()
        		.intValue();
    }
    
    /**
     * Complete the delivery. The status goes from {@code ON_DELIVERY} to {@code DELIVERED}
     * 
     * 
     * @param orderId the order id
     * @return delivery time estimate in minutes
     * @throws DeliveryException when order ID invalid to status not {@code ON_DELIVERY}
     */
    public void complete(int orderId) throws DeliveryException {
    	if(!this.Ordini.containsKey(orderId) || this.OrdiniConStato.get(orderId)!=OrderStatus.ON_DELIVERY)
    		throw new DeliveryException();
    	this.OrdiniConStato.put(orderId, OrderStatus.DELIVERED);
    }
    
    /**
     * Retrieves the total amount for all orders of a customer.
     * 
     * @param customerId the customer ID
     * @return total amount
     */
    public double totalCustomer(int customerId){
    	double sum = 0.0;
        for(Integer I : this.Ordini.keySet()) {
        	if((this.Ordini.get(I).getClientId()==customerId) &&(this.OrdiniConStato.get(I) == OrderStatus.CONFIRMED || 
        			this.OrdiniConStato.get(I) == OrderStatus.PREPARATION ||
        			this.OrdiniConStato.get(I) == OrderStatus.ON_DELIVERY ||
        			this.OrdiniConStato.get(I) == OrderStatus.DELIVERED))
				try {
					sum+=totalOrder(I.intValue());
				} catch (DeliveryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return sum;
    }
    
    /**
     * Computes the best customers by total amount of orders.
     *  
     * @return the classification
     */
    public SortedMap<Double,List<String>> bestCustomers(){
        return this.Ordini
        		.values()
        		.stream()
        		.collect(Collectors.groupingBy(O -> totalCustomer(O.getClientId()),
        				() -> {
        					return new TreeMap<>(Collections.reverseOrder());
        				},
        				Collector.of(ArrayList<String>::new,
        						(List<String> a, Order O) -> {a.add(customerInfo(O.getClientId()));},
        						(List<String> a, List<String> b) -> {a.addAll(b); return a;}, 
        						(List<String> a) -> {return a;})));
    }
    
// NOT REQUIRED
//
//    /**
//     * Computes the best items by total amount of orders.
//     *  
//     * @return the classification
//     */
//    public List<String> bestItems(){
//        return null;
//    }
//    
//    /**
//     * Computes the most popular items by total quantity ordered.
//     *  
//     * @return the classification
//     */
//    public List<String> popularItems(){
//        return null;
//    }

}
