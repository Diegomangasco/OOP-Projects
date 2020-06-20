package managingProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Collector;

public class PropertyManager {

	/**
	 * Add a new building 
	 */
	private Map<String, Edificio> Edifici = new HashMap<>(); 	
	private Map<String, List<Appartamento>> Proprietari = new HashMap<>();
	private Map<String, List<String>> Professionisti = new HashMap<>();
	private Map<Integer, Richiesta> Richieste = new HashMap<>();
	private Map<Integer, String> Assegnate = new HashMap<>();
	public void addBuilding(String building, int n) throws PropertyException {
		if(this.Edifici.containsKey(building))
			throw new PropertyException();
		if(n<1 || n>100)
			throw new PropertyException();
		Edificio E = new Edificio(building, n);
		this.Edifici.put(building, E);
	}

	public void addOwner(String owner, String... apartments) throws PropertyException {
		if(this.Proprietari.containsKey(owner))
			throw new PropertyException();
		String [] linea;
		for(String S : apartments) {
			linea = S.split(":");
			if(!this.Edifici.containsKey(linea[0]))
				throw new PropertyException();
			if(Integer.valueOf(linea[1]).intValue()<0 || Integer.valueOf(linea[1]).intValue()>this.Edifici.get(linea[0]).getN())
				throw new PropertyException();
		}
		List<Appartamento> Net = new ArrayList<>();
		this.Proprietari.put(owner, Net);
		for(String H : apartments) {
			for(String S : this.Proprietari.keySet()) {
				for(Appartamento A : this.Proprietari.get(S)) {
					if(H.compareTo(A.toString())==0)
						throw new PropertyException();
				}
			}
			String [] linea1 = H.split(":");
			this.Proprietari.get(owner).add(new Appartamento(linea1[0], Integer.valueOf(linea1[1]).intValue()));
		}
	}

	/**
	 * Returns a map ( number of apartments => list of buildings ) 
	 * 
	 */
	public SortedMap<Integer, List<String>> getBuildings() {
		
		return this.Edifici.values().stream().collect(Collectors.groupingBy(
				E -> E.getN(),
				TreeMap::new,
				Collector.of(ArrayList<String>::new, 
						(List<String> a, Edificio E) -> {a.add(E.getId());}, 
						(List<String> a, List<String> b) -> {a.addAll(b); return a;}, 
						(List<String> a) -> {a.sort((S1, S2) -> S1.compareTo(S2)); return a;})));
	}

	private static List<String> Professioni = new ArrayList<>();
	public void addProfessionals(String profession, String... professionals) throws PropertyException {
		if(Professioni.contains(profession))
			throw new PropertyException();
		for(String H : professionals) {
			for(String S : this.Professionisti.keySet()) {
				if(this.Professionisti.get(S).contains(H))
					throw new PropertyException();
			}
		}
		List<String> Net = new ArrayList<>();
		for(String S : professionals)
			Net.add(S);
		this.Professionisti.put(profession, Net);
		PropertyManager.Professioni.add(profession);
				
	}

	/**
	 * Returns a map ( profession => number of workers )
	 *
	 */
	public SortedMap<String, Integer> getProfessions() {
		
		return this.Professionisti.entrySet().stream().collect(Collectors.groupingBy(
				E -> E.getKey(),
				TreeMap::new,
				Collector.of(ArrayList<Integer>::new, 
						(List<Integer> a, Entry<String, List<String>> E) -> {a.add(E.getValue().size());}, 
						(List<Integer> a, List<Integer> b) -> {a.addAll(b); return a;}, 
						(List<Integer> a) -> {
							int n = 0;
							for(Integer I : a)
								n+=I.intValue();
							return n;
						})));
	}

	private static int idR = 0;
	public int addRequest(String owner, String apartment, String profession) throws PropertyException {
		if(!this.Proprietari.containsKey(owner))
			throw new PropertyException();
		String [] linea = apartment.split(":");
		boolean c = false;
		Appartamento B = null;
		for(Appartamento A : this.Proprietari.get(owner)) {
			if(apartment.compareTo(A.toString())==0)
			{
				c = true;
				B = A;
				break;
			}
		}
		if(c==false)
			throw new PropertyException();
		if(!PropertyManager.Professioni.contains(profession))
			throw new PropertyException();
		idR++;
		this.Richieste.put(idR, new Richiesta(idR, owner, profession, B));
		return idR;	
	}

	public void assign(int requestN, String professional) throws PropertyException {
		if(!this.Richieste.containsKey(requestN))
			throw new PropertyException();
		if(this.Richieste.get(requestN).getState().compareTo("pending")!=0)
			throw new PropertyException();
		if(!this.Professionisti.get(this.Richieste.get(requestN).getProfession()).contains(professional))
			throw new PropertyException();
		this.Richieste.get(requestN).setProfessional(professional);
		this.Richieste.get(requestN).stateChanging("assigned");
	}

	public List<Integer> getAssignedRequests() {
		
		return this.Richieste.values().stream()
				.filter(R -> R.getState().compareTo("assigned")==0)
				.sorted((R1, R2) -> {return R1.getId() - R2.getId();})
				.map(R -> R.getId())
				.collect(Collectors.toList());
	}

	
	public void charge(int requestN, int amount) throws PropertyException {
		if(!this.Richieste.containsKey(requestN))
			throw new PropertyException();
		if(this.Richieste.get(requestN).getState().compareTo("assigned")!=0)
			throw new PropertyException();
		if(amount<0 || amount >1000)
			throw new PropertyException();
		this.Richieste.get(requestN).stateChanging("completed");
		this.Richieste.get(requestN).setAmount(amount);
		
	}

	/**
	 * Returns the list of request ids
	 * 
	 */
	public List<Integer> getCompletedRequests() {
		
		return this.Richieste.values().stream()
				.filter(R -> R.getState().compareTo("completed")==0)
				.sorted((R1, R2) -> {return R1.getId() - R2.getId();})
				.map(R -> R.getId())
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns a map ( owner => total expenses )
	 * 
	 */
	public SortedMap<String, Integer> getCharges() {
		
		return this.Richieste.values().stream()
				.filter(R -> R.getState().compareTo("completed") == 0)
				.collect(Collectors.groupingBy(
						R -> R.getOwner(),
						TreeMap::new,
						Collector.of(ArrayList<Integer>::new, 
								(List<Integer> a, Richiesta R) -> {
									a.add(R.getAmount());
								}, 
								(List<Integer> a, List<Integer> b) -> {
									a.addAll(b);
									return a;
								},
								(List<Integer> a) -> {
									int n = 0;
									for(Integer I : a) {
										n+=I;
									}
									return n;
								})));
	}

	/**
	 * Returns the map ( building => ( profession => total expenses) ).
	 * Both buildings and professions are sorted alphabetically
	 * 
	 */
	public SortedMap<String, Map<String, Integer>> getChargesOfBuildings() {
		
		return this.Richieste.values().stream()
				.filter(R -> R.getState().compareTo("completed")==0)
				.collect(Collectors.groupingBy(
						R -> R.getApp().getIdE(),
						TreeMap::new,
						Collectors.groupingBy(
								R -> R.getProfessional(),
								TreeMap::new,
								Collector.of(ArrayList<Integer>:: new, 
										(List<Integer> a, Richiesta R) -> {
											a.add(R.getAmount());
										}, 
										(List<Integer> a, List<Integer> b) -> {a.addAll(b); return a;}, 
										(List<Integer> a) -> {
											int n = 0;
											for(Integer I : a) {
												n+=I;
											}
											return n;
										}))));
	}

}
