package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * @param name
	 */
	private final Element uscita = null;
	private double portataI;
	public Sink(String name) {
		super(name);
		//TODO: complete
	}
	@Override
	public void setPortataI(double portata)
	{
		this.portataI=portata;
	}
	
	@Override
	public double getPortataI()
	{
		return this.portataI;
	}
	
	@Override
	public void connect(Element elem)
	{
		System.out.println("The connection is not possible!");
		return;
	}
	
	@Override
	public Element getOutput(){
		System.out.println("You arrived at the exit of the water flow!");
		return this.uscita;
	}
}
