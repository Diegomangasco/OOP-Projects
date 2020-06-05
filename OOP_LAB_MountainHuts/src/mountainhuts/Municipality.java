package mountainhuts;

/**
 * Represents a municipality
 *
 */
public class Municipality {

	/**
	 * Name of the municipality.
	 * 
	 * Within a region the name of a municipality is unique
	 * 
	 * @return name
	 */
	private String name;
	private String province;
	private Integer altitude;
	public Municipality(String name, String province, Integer altitude) {
		this.name = name;
		this.province = province;
		this.altitude = altitude;
	}
	public String getName() {
		return this.name;
	}

	/**
	 * Province of the municipality
	 * 
	 * @return province
	 */
	public String getProvince() {
		return this.province;
	}

	/**
	 * Altitude of the municipality
	 * 
	 * @return altitude
	 */
	public Integer getAltitude() {
		return this.altitude;
	}
	public String returnIfE(String name) {
		if(this.name.compareTo(name) == 0) {
			return this.getProvince();
		}
		return null;
	}

}
