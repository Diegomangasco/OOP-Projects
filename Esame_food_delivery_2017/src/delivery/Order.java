package delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delivery.Delivery.OrderStatus;

public class Order {
	private int Id;
	private int clientId;
	private static int progressive = 0;
	private HashMap<Menu, Integer> Menu = new HashMap<>();
	public Order(int clientId) {
		this.clientId = clientId;
		progressive++;
		this.Id = progressive;
	}
	
	public int getClientId() {
		return this.clientId;
	}
	public int getId() {
		return this.Id;
	}
	
	public void setMenu(Menu M, int q) {
		if(this.Menu.containsKey(M))
			this.Menu.put(M, Integer.valueOf(this.Menu.get(M).intValue() + q));
		else 
			this.Menu.put(M, Integer.valueOf(q));
	}
	public int getQuantity(Menu M) {
		return this.Menu.get(M).intValue();
	}
	public Map<Menu, Integer> getMenu(){
		return this.Menu;
	}
}
