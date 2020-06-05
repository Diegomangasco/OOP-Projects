package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut.
 * 
 * It is linked to a {@link Municipality}
 *
 */
public class MountainHut {

	/**
	 * Unique name of the mountain hut
	 * @return name
	 */
	private String name;
	private String category;
	private Integer bedsNumber;
	private Municipality Muni;
	private Optional<Integer> altitude;
	public MountainHut(String name, String category, Integer bedsNumber, Municipality Muni) {
		this.name = name;
		this.category = category;
		this.bedsNumber = bedsNumber;
		this.Muni = Muni;
	}
	public String getName() {
		return this.name;
	}

	/**
	 * Altitude of the mountain hut.
	 * May be absent, in this case an empty {@link java.util.Optional} is returned.
	 * 
	 * @return optional containing the altitude
	 */
	public void setAltitude(Integer altitude) {
		if(altitude.intValue()>=0) 
			this.altitude = Optional.of(altitude);
		else 
			this.altitude = Optional.empty();
	}
	public Optional<Integer> getAltitude() {
		if(this.altitude.isPresent())
			return this.altitude;
		return Optional.empty();
	}

	/**
	 * Category of the hut
	 * 
	 * @return the category
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * Number of beds places available in the mountain hut
	 * @return number of beds
	 */
	public Integer getBedsNumber() {
		return this.bedsNumber;
	}

	/**
	 * Municipality where the hut is located
	 *  
	 * @return municipality
	 */
	public Municipality getMunicipality() {
		return this.Muni;
	}
	public String getNameM() {
		return this.Muni.getName();
	}
	public String getNameP() {
		return this.Muni.getProvince();
	}

}
