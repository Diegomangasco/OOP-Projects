package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends ElementExt {

	/**
	 * Constructor
	 * @param name
	 */
	private double portataI;
	private double maxFlow;
	public Sink(String name) {
		super(name);
		//TODO: complete
	}
	public void setPortataI(double portata)
	{
		this.portataI=portata;
	}
	
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
	
}
