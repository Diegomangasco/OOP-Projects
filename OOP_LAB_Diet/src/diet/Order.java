package diet;


import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * Represents an order in the take-away system
 */
public class Order {
	
	private User U;
	private String R;
	private Ora O;
	private OrderStatus OrderS = OrderStatus.ORDERED;
	private PaymentMethod PM = PaymentMethod.CASH;
	private double price;
	private TreeMap<String, Integer> listaMenu = new TreeMap<>();	
	
	
	public Order(User U, String Restaurant, Ora O)
	{
		this.U = U;
		this.R = Restaurant;
		this.O = O;
	}
 
	/**
	 * Defines the possible order status
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED;
	}
	/**
	 * Defines the possible valid payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD;
	}
	
	/**
	 * Total order price
	 * @return order price
	 */
	public double Price() {
		return this.price;
	}
	
	/**
	 * define payment method
	 * 
	 * @param method payment method
	 */
	public User getUser() {
		return this.U;
	}
	public Ora getHour() {
		return this.O;
	}
	public void setPaymentMethod(PaymentMethod method) {
		this.PM = method;
	}
	
	/**
	 * get payment method
	 * 
	 * @return payment method
	 */
	public PaymentMethod getPaymentMethod() {
		return this.PM;
	}
	
	/**
	 * change order status
	 * @param newStatus order status
	 */
	public void setStatus(OrderStatus newStatus) {
		this.OrderS = newStatus;
	}
	
	/**
	 * get current order status
	 * @return order status
	 */
	public OrderStatus getStatus(){
		return this.OrderS;
	}
	
	/**
	 * Add a new menu with the relative order to the order.
	 * The menu must be defined in the {@link Food} object
	 * associated the restaurant that created the order.
	 * 
	 * @param menu     name of the menu
	 * @param quantity quantity of the menu
	 * @return this order to enable method chaining
	 */
	public Order addMenus(String menu, int quantity) {
		Integer I = Integer.valueOf(quantity);
		if(this.listaMenu.containsKey(menu)==true) {
			this.listaMenu.replace(menu, I);			
		}
		else
			this.listaMenu.put(menu, I);
		return this;
	}
	
	/**
	 * Converts to a string as:
	 * <pre>
	 * RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
	 * 	MENU_NAME_1->MENU_QUANTITY_1
	 * 	...
	 * 	MENU_NAME_k->MENU_QUANTITY_k
	 * </pre>
	 */
	@Override
	public String toString() {
		String Users = this.R + ", " + this.U.getFirstName() + " " + this.U.getLastName() + " : "
				+ "(" + (this.O.getH()<10? "0" + this.O.getH():this.O.getH()) + ":" 
				+ (this.O.getM()<10? "0" + this.O.getM(): this.O.getM()) + ")" + ":\n";
		StringBuffer Sb = new StringBuffer();
		this.listaMenu.entrySet().stream().forEach((e) -> Sb.append('\t').append(e.getKey())
				.append("->").append(e.getValue()).append('\n'));
		Users += Sb.toString();
		return Users;
	}
	
}
