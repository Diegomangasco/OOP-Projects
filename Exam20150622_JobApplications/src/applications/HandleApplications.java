package applications;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Collector;

public class HandleApplications {

	private HashMap<String, Skill> Skills = new HashMap<>();
	private HashMap<String, Position> Positions = new HashMap<>();
	private HashMap<String, Richiedente> Richiedenti = new HashMap<>();
	private HashMap<Richiedente, Position> Candidati = new HashMap<>();
	public void addSkills(String... names) throws ApplicationException {
		for(String S : names) {
			if(this.Skills.containsKey(S))
				throw new ApplicationException();
			this.Skills.put(S, new Skill(S));
		}
	}
	public void addPosition(String name, String... skillNames) throws ApplicationException {
		if(this.Positions.containsKey(name))
			throw new ApplicationException();
		this.Positions.put(name, new Position(name));
		for(String S : skillNames) {
			if(this.Skills.containsKey(S)) {
				this.Positions.get(S).setSkill(this.Skills.get(S));
				this.Skills.get(S).setPosition(this.Positions.get(name));
			}
			else
				throw new ApplicationException();
		}
	}
	public Skill getSkill(String name) {
		if(this.Skills.containsKey(name))
			return this.Skills.get(name);
		return null;
	}
	public Position getPosition(String name) {
		if(this.Positions.containsKey(name))
			return this.Positions.get(name);
		return null;
	}
	
	public void addApplicant(String name, String capabilities) throws ApplicationException {
		if(this.Richiedenti.containsKey(name))
			throw new ApplicationException();
		String [] O = capabilities.split(",");
		for(String S : O) {
			String [] H = S.split(":");
			if(Integer.valueOf(H[1])<1 || Integer.valueOf(H[1])>10)
				throw new ApplicationException();
		}
		this.Richiedenti.put(name, new Richiedente(name, capabilities));
	}
	public String getCapabilities(String applicantName) throws ApplicationException {
		if(!this.Richiedenti.containsKey(applicantName))
			throw new ApplicationException();
		if(this.Richiedenti.get(applicantName).isC()==false)
			return "";
		String Ret = null;
		for(String H : this.Richiedenti.get(applicantName).getC()) {
			Ret+=H + ",";
		}
		return Ret;
	}
	
	public void enterApplication(String applicantName, String positionName) throws ApplicationException {
		if(!this.Richiedenti.containsKey(applicantName))
			throw new ApplicationException();
		if(!this.Positions.containsKey(positionName))
			throw new ApplicationException();
		if(this.Candidati.containsKey(this.Richiedenti.get(applicantName)))
			throw new ApplicationException();
		if(!this.Positions.get(positionName).getSS()
				.containsAll(this.Richiedenti.get(applicantName).getC()))
			throw new ApplicationException();
		this.Candidati.put(this.Richiedenti.get(applicantName), this.Positions.get(positionName));
		this.Positions.get(positionName).setR(this.Richiedenti.get(applicantName));
	}
	
	public int setWinner(String applicantName, String positionName) throws ApplicationException {
		if(this.Candidati.get(this.Richiedenti.get(applicantName)).getName().compareTo(positionName)!=0)
			throw new ApplicationException();
		if(this.Positions.get(positionName).getV()!=null)
			throw new ApplicationException();
		if(this.Positions.get(positionName).getS().size()<=
				this.Richiedenti.get(applicantName).getSum(this.Positions.get(positionName).getS())*6)
			throw new ApplicationException();
		this.Positions.get(positionName).setR(this.Richiedenti.get(applicantName));
		return this.Richiedenti.get(applicantName).getSum(this.Positions.get(positionName).getS());
	}
	
	public SortedMap<String, Long> skill_nApplicants() {
		return this.Skills.values().stream()
				.collect(Collectors.groupingBy(S -> S.getName(),
						TreeMap::new,
						Collector.of(ArrayList<Richiedente>::new, 
								(List<Richiedente> a, Skill S) -> {
									for(Richiedente R : this.Richiedenti.values()) {
										if(R.getC().contains(S.getName()))
											a.add(R);
									}
								}, 
								(List<Richiedente> a, List<Richiedente> b) -> {a.addAll(b); return a;}, 
								(List<Richiedente> a) -> { return (long)a.size();})));
	}
	public String maxPosition() {
		return this.Candidati.entrySet().stream()
				.collect(Collectors.groupingBy(E -> E.getValue().getName(),
						HashMap::new,
						Collector.of(ArrayList<Richiedente>::new, 
								(List<Richiedente> a, Entry<Richiedente, Position> E) -> {
									a.add(E.getKey());
								},
								(List<Richiedente> a, List<Richiedente> b) -> {a.addAll(b); return a;}, 
								(List<Richiedente> a) -> {return a.size();})))
				.entrySet().stream()
				.sorted((E1, E2) -> {return E2.getValue() - E1.getValue();})
				.map(E -> E.getKey())
				.collect(Collectors.toList()).get(0);
		
	}
}

