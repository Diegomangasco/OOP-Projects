package hydraulic;

public abstract class ElementExt extends Element{

	private double maxFlow;
	public ElementExt(String name) {
		super(name);
	}

	public void setMaxFlow(double maxFlow) {
		this.maxFlow = maxFlow;
	}
	public double getMaxFlow() {
		return this.maxFlow;
	}

}
