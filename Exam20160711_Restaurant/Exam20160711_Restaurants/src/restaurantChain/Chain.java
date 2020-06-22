package restaurantChain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Chain {	

	private Map<String, Restaurant> Ristoranti = new HashMap<>();
		public void addRestaurant(String name, int tables) throws InvalidName{
			if(this.Ristoranti.containsKey(name))
				throw new InvalidName();
			this.Ristoranti.put(name, new Restaurant(name, tables));
		}
		
		public Restaurant getRestaurant(String name) throws InvalidName{
			if(!this.Ristoranti.containsKey(name))
				throw new InvalidName();
			return this.Ristoranti.get(name);
		}
		
		public List<Restaurant> sortByIncome(){
			return this.Ristoranti.values().stream()
					.sorted((R1, R2) -> {return (int) ( R2.getIncome() - R1.getIncome());})
					.collect(Collectors.toList());
		}
		
		public List<Restaurant> sortByRefused(){
			return this.Ristoranti.values().stream()
					.sorted((R1, R2) -> {return (R1.getRefused() - R2.getRefused());})
					.collect(Collectors.toList());
		}
		
		public List<Restaurant> sortByUnusedTables(){
			return this.Ristoranti.values().stream()
					.sorted((R1, R2) -> {return R1.getUnusedTables() - R2.getUnusedTables();})
					.collect(Collectors.toList());
		}
}
