package applications;

import java.util.*;
import java.util.stream.Collectors;


public class Skill {

	private String name;
	private ArrayList<Position> Positions = new ArrayList<>();
	public Skill(String name) {
		this.name = name;
	}
	public void setPosition(Position P) {
		this.Positions.add(P);
	}
	public String getName() {return this.name;}
	public List<Position> getPositions() {
		return this.Positions.stream().sorted((P1, P2) -> P1.getName().compareTo(P2.getName())).collect(Collectors.toList());
	}
}