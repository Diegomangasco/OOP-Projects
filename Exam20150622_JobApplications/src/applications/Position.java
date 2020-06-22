package applications;

import java.util.*;
import java.util.stream.Collectors;

public class Position {

	private String name;
	private Richiedente vincitore = null;
	private List<Skill> Skills = new ArrayList<>();
	private List<Richiedente> Richiedenti = new ArrayList<>();
	public Position(String name) {
		this.name = name;
	}
	public void setSkill(Skill S) {
		this.Skills.add(S);
	}
	public List<Skill> getS(){
		return this.Skills;
	}
	public List<String> getSS(){
		return this.Skills.stream().map(S -> S.getName()).collect(Collectors.toList());
	}
	public void setR(Richiedente R) {
		this.Richiedenti.add(R);
	}
	public String getName() {return this.name;}
	
	public List<String> getApplicants() {
		return this.Richiedenti.stream().map(R -> R.getName())
				.sorted((S1, S2) -> S1.compareTo(S2))
				.collect(Collectors.toList());
	}
	public Richiedente getV() {
		return this.vincitore;
	}
	public void setVincitore(Richiedente R) {
		this.vincitore = R;
	}
	public String getWinner() {
		if(this.vincitore==null)
			return null;
		return this.vincitore.getName(); 
	}
}