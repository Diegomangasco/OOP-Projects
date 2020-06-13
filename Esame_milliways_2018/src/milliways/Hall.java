package milliways;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hall {
	
	private int Id;
	private ArrayList<String> Facilities = new ArrayList<>();
	private int numF = 0;
	private ArrayList<Party> Gruppi = new ArrayList<>();
	public Hall(int Id) {
		this.Id = Id;
	}
	public int getId() {
		return this.Id;
	}

	public void addFacility(String facility) throws MilliwaysException {
		if(this.Facilities.contains(facility))
			throw new MilliwaysException();
		this.Facilities.add(facility);
		this.numF++;
	}

	public List<String> getFacilities() {
        return this.Facilities.stream().sorted((F1, F2) -> F1.compareTo(F2)).collect(Collectors.toList());
	}
	
	int getNumFacilities(){
        return this.numF;
	}

	public boolean isSuitable(Party party) {
		if(this.Facilities.containsAll(party.getRequirements()))
			return true;
		return false;
	}
	
	public void setGruppo(Party P) {
		this.Gruppi.add(P);
	}
	public List<Party> getGruppi(){
		return this.Gruppi;
	}

}

