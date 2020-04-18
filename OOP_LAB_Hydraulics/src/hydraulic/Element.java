package hydraulic;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 */
public class Element {
	
	/**
	 * Constructor
	 * @param name the name of the element
	 */
	private String name;
	private Element uscita;
	private double portataI;
	public Element(String name){
		this.name=name;
	}

	/**
	 * getter method
	 * @return the name of the element
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * @param elem the element that will be placed downstream
	 */
	public void setPortataI(double portata)
	{
		this.portataI = portata;
	}
	public void connect(Element elem){
		this.uscita = elem;
	}
	
	/**
	 * Retrieves the element connected downstream of this
	 * @return downstream element
	 */
	public Element getOutput(){
		return this.uscita;
	}
	
	public double getPortataI()
	{
		return this.portataI;
	}
	
}
