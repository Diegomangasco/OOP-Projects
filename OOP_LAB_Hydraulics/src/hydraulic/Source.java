package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * The status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends ElementExt {
	private static final double MAX = 1000.0;
	private double portata;
	private double maxFlow;
	public Source(String name) {
		super(name);
		//TODO: complete
	}

	/**
	 * defines the flow produced by the source
	 * 
	 * @param flow
	 */
	public double getPortata()
	{
		return this.portata;
	}
	public void setFlow(double portata)
	{
		this.portata=portata;
	}
	
	@Override
	public void setMaxFlow(double maxFlow) {
		this.maxFlow = MAX;
	}
	
}
