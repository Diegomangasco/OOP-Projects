package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * The status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {
	
	private Element uscita = null;
	private double portata;
	public Source(String name) {
		super(name);
		//TODO: complete
	}

	/**
	 * defines the flow produced by the source
	 * 
	 * @param flow
	 */
	public void setFlow(double flow){
		this.portata=flow;
	}
	public double getPortata()
	{
		return this.portata;
	}
	
}
