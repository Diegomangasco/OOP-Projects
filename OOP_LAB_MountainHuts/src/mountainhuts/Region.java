package mountainhuts;

import static java.util.stream.Collectors.toList;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	private String name;
	private ArrayList<Altitudine> Altitudini = new ArrayList<>();
	private HashMap<String, Municipality> Municipi = new HashMap<>();
	private HashMap<String, MountainHut> MH = new HashMap<>();
	public Region(String name) {
		this.name = name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public class Altitudine{
		private Integer first;
		private Integer last;
		public Altitudine(Integer first, Integer last) {
			this.first = first;
			this.last = last;
		}
		public Integer getFirst()
		{
			return this.first;
		}
		public Integer getLast() {
			return this.last;
		}
	}
	public void setAltitudeRanges(String... ranges) {
		for(String s : ranges) {
			String [] valori = s.split("-");
			this.Altitudini.add(new Altitudine(
								Integer.valueOf(valori[0]),
								Integer.valueOf(valori[1]))
								);
		}
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		for(Altitudine A : this.Altitudini) {
			if(A.getFirst().compareTo(altitude)<=0 && A.getLast().compareTo(altitude)>=0) {
				return A.getFirst().toString() + "-" + A.getLast().toString();
			}
		}
		return "0-INF";
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		int i = 0;
		Municipality Mu = null;
		for(String S : this.Municipi.keySet()) {
			if(S.compareTo(name) == 0)
				return this.Municipi.get(S);
		}
		this.Municipi.put(name, Mu = new Municipality(name, province, altitude));
		return Mu;
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return this.Municipi.values();
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		if(this.MH.containsKey(name)) {
			return this.MH.get(name);
		}
		MountainHut MM = null;
		this.MH.put(name, MM = new MountainHut(name,category,bedsNumber,municipality));
		MM.setAltitude(Integer.valueOf(-1));
		return MM;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		if(this.MH.containsKey(name)) {
			return this.MH.get(name);
		}
		MountainHut MM = null;
		this.MH.put(name, MM = new MountainHut(name, category,bedsNumber,municipality));
		MM.setAltitude(altitude);
		return MM;
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return this.MH.values();
	}

	/**
	 * Factory methods that creates a new region by loadomg its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region R = new Region(name);
		List<String> linea;
		int counter = 0;
		linea = Region.readData(file);
		for(String S : linea) {
			if(counter==0) {
				counter++;
			}
			else {
				String [] L = S.split(";");
				if(!L[4].isEmpty()) {
					R.createOrGetMountainHut(L[3], Integer.valueOf(L[4]), L[5], Integer.valueOf(L[6]), 
									R.createOrGetMunicipality(L[1], L[0], Integer.valueOf(L[2])));
				}
				else {
					R.createOrGetMountainHut(L[3], L[5], Integer.valueOf(L[6]), 
									R.createOrGetMunicipality(L[1], L[0], Integer.valueOf(L[2])));
				}
			}
		}
		return R;
	}

	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	@SuppressWarnings("unused")
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	
	public Map<String, Long> countMunicipalitiesPerProvince() {
		return this.Municipi.values().stream().collect(Collectors.groupingBy(Municipality::getProvince,
				HashMap::new, Collectors.counting()));
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return this.MH.values().stream().collect(Collectors.groupingBy(MountainHut::getNameP, HashMap::new,
				Collectors.groupingBy(MountainHut::getNameM, HashMap::new, Collectors.counting())));
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		return this.MH.values().stream().collect(Collectors.groupingBy(E -> {
			if(E.getAltitude().isPresent())
				return this.getAltitudeRange(E.getAltitude().get());
			else
				return this.getAltitudeRange(E.getMunicipality().getAltitude());
		}, HashMap::new, Collectors.counting()));
		
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	//Giusto
	
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return this.MH.values().stream().collect(Collectors.groupingBy(
				E -> E.getNameP(), HashMap::new, Collectors.summingInt(E -> E.getBedsNumber())));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		HashMap<String, Optional<MountainHut>> Ret = this.MH.values().stream().collect(Collectors.groupingBy(E -> {
			if(E.getAltitude().isPresent())
				return this.getAltitudeRange(E.getAltitude().get());
			else
				return this.getAltitudeRange(E.getMunicipality().getAltitude());
		}, HashMap::new, Collectors.maxBy((E1,E2) -> E1.getBedsNumber().compareTo(E2.getBedsNumber()))));
		HashMap <String, Optional<Integer>> Z = new HashMap<>();
		Iterator<Entry<String, Optional<MountainHut>>> It = Ret.entrySet().iterator();
		while(It.hasNext()) {
			Entry<String, Optional<MountainHut>> next = It.next();
			Z.put(next.getKey(), Optional.ofNullable(next.getValue().get().getBedsNumber()));
		}
		return Z;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		return this.MH.values().stream().collect(Collectors.groupingBy(
				E -> {
					long z = 0;
					for(MountainHut M : this.MH.values()) {
						if(E.getMunicipality().getName().compareTo(M.getMunicipality().getName())==0)
							z++;
					}
					return z;
				}, HashMap::new, Collector.of(ArrayList<String>::new, 
						(List<String> a, MountainHut M) -> a.add(M.getMunicipality().getName()),
						(a,b) -> {a.addAll(b); return a;}, a -> {a.sort((M1, M2) -> M1.compareTo(M2)); return a;})));
	}
	
}
