package milliways;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Restaurant {

	private HashMap<String, Race> Razze = new HashMap<>();
	private HashMap<Integer, Hall> Saloni = new HashMap<>();
	private ArrayList<Hall> SaloniInOrdine = new ArrayList<>();
    public Restaurant() {
	}
	
	public Race defineRace(String name) throws MilliwaysException{
	    if(this.Razze.containsKey(name))
	    	throw new MilliwaysException();
	    Race R = new Race(name);
	    this.Razze.put(name, R);
	    return R;
	}
	
	public Party createParty() {
	    Party P = new Party();
	    return P;
	}
	
	public Hall defineHall(int id) throws MilliwaysException{
	    if(this.Saloni.containsKey(Integer.valueOf(id)))
	    	throw new MilliwaysException();
	    Hall H;
	    this.Saloni.put(Integer.valueOf(id), H = new Hall(id));
	    this.SaloniInOrdine.add(H);
	    return H;
	}

	public List<Hall> getHallList() {
		return this.SaloniInOrdine;
	}

	public Hall seat(Party party, Hall hall) throws MilliwaysException {
        if(hall.isSuitable(party))
        	hall.setGruppo(party);
        else
        	throw new MilliwaysException();
        return hall;
	}

	public Hall seat(Party party) throws MilliwaysException {
		Hall Ret = null;
        for(Hall H : this.SaloniInOrdine) {
        	if(H.isSuitable(party))
        	{
        		H.setGruppo(party);
        		Ret = H;
        		break;
        	}
        }
        if(Ret == null)
        	throw new MilliwaysException();
        return Ret;
	}

	public Map<Race, Integer> statComposition() {
        return this.Saloni
        		.values()
        		.stream()
        		.map(E -> E.getGruppi())
        		.flatMap(E -> E.stream())
        		.map(E -> E.getRazze())
        		.flatMap(E -> E.entrySet().stream())
        		.collect(Collectors.groupingBy(E -> E.getKey(), HashMap::new,  Collectors.summingInt(E -> E.getValue())));
	}

	public List<String> statFacility() {
        return this.Saloni
        		.values()
        		.stream()
        		.map(S -> S.getFacilities())
        		.flatMap(S -> S.stream())
        		.sorted((F1, F2) -> {
        			long z1 = 0;
        			long z2 = 0;
        			for(Integer I : this.Saloni.keySet()) {
        				if(this.Saloni.get(I).getFacilities().contains(F1))
        					z1++;
        				if(this.Saloni.get(I).getFacilities().contains(F2))
        					z2++;
        			}
        			if(z1>z2)
        				return -1;
        			if(z1<z2)
        				return 1;
        			return F1.compareTo(F2);
        		})
        		.collect(Collectors.toList());
				
	}
	
	public Map<Integer,List<Integer>> statHalls() {
        return this.Saloni
        		.values()
        		.stream()
        		.collect(Collectors.groupingBy((Hall H) -> H.getFacilities().size(),
        				TreeMap::new,
        				Collector.of(ArrayList<Integer>::new,
        						(List<Integer> a, Hall H) -> {a.add(H.getId());},
        						(List<Integer> a, List<Integer> b) -> {a.addAll(b); return a;},
        						(List<Integer> a) -> {
        							a.sort((E1, E2) -> 
        								{
        									return E1.intValue() - E2.intValue();
        								}); 
        							return a;})));
	}

}

