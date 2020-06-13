package milliways;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Party {
	
	private HashMap<Race, Integer> Razze = new HashMap<>(); 
    public void addCompanions(Race race, int num) {
    	if(this.Razze.containsKey(race))
    		this.Razze.put(race, Integer.valueOf(this.Razze.get(race).intValue() + num));
    	else
    		this.Razze.put(race, Integer.valueOf(num));
	}

	public int getNum() {
		int sum = 0;
        for(Race R : this.Razze.keySet()) {
        	sum+=this.Razze.get(R).intValue();
        }
        return sum;
	}

	public int getNum(Race race) {
	    int sum = this.Razze.get(race).intValue();
	    return sum;
	}

	public List<String> getRequirements() {
        return this.Razze
        		.entrySet()
        		.stream()
        		.map(E -> E.getKey().getRequirements())
        		.flatMap(T -> T.stream())
        		.distinct()
        		.sorted((E1, E2) -> E1.compareTo(E2))
        		.collect(Collectors.toList());
	}

    public Map<String,Integer> getDescription(){
        return this.Razze
        		.entrySet()
        		.stream()
        		.collect(Collectors.toMap(E -> E.getKey().getName(), E -> E.getValue()));
    }
    public Map<Race, Integer> getRazze(){
    	return this.Razze;
    }

}

