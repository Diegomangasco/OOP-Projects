package milliways;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Race {
	
	private String nome;
	private ArrayList<String> Req = new ArrayList<>();
	public Race(String nome) {
		this.nome = nome;
	}
    
	public void addRequirement(String requirement) throws MilliwaysException {
		if(this.Req.contains(requirement))
			throw new MilliwaysException();
		this.Req.add(requirement);
	}
	
	public List<String> getRequirements() {
        return this.Req.stream().sorted((R1,R2) -> R1.compareTo(R2)).collect(Collectors.toList());
	}
	
	public String getName() {
        return this.nome;
	}
}

