package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem implements SimulationObserver{
	
	@Override
	public void notifyFlow(String type, String name, double inFlow, double... outFlow) {}
	/**
	 * Adds a new element to the system
	 * @param elem
	 */
	protected Element [] system = new Element [50];
	protected static int num_elements=0;
	public void addElement(Element elem){
		system[num_elements]= elem;
		num_elements++;
	}
	
	/**
	 * returns the element added so far to the system.
	 * If no element is present in the system an empty array (length==0) is returned.
	 * 
	 * @return an array of the elements added to the hydraulic system
	 */
	public Element[] getElements(){
		Element [] system2 = new Element [num_elements]; 
		int i;
		for(i=0;i<num_elements;i++)
			system2[i]=system[i];
		return system2;
	}
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		// TODO: to be implemented
		return null;
	}
	private void simulateT(SimulationObserver observer, Element[] T, Element u, double portata)
	{
		Split e1 = null;
		Sink e2 = null;
		Tap e3 = null;
		Multisplit e4 = null;
		String type = null;
		int i = 0;
		while(u!=null)
		{
			if (u instanceof Tap) {
				type = "Tap"; 
				e3 = (Tap)u;
				e3.setPortataI(portata);
				observer.notifyFlow(type, e3.getName(), e3.getPortataI(), e3.getPortataU());
				portata = e3.getPortataU();
				u = u.getOutput();
			}
			if (u instanceof Sink) {
				type = "Sink"; 
				e2 = (Sink)u;
				e2.setPortataI(portata);
				observer.notifyFlow(type, e2.getName(), e2.getPortataI(), NO_FLOW);
				u = u.getOutput();
			}
			if ((u instanceof Split) || (u instanceof Multisplit)) {
				if(u instanceof Multisplit){
					type = "Multisplit";
					e4 = (Multisplit)u;
					e4.setPortataI(portata);
					observer.notifyFlow(type, e4.getName(), e4.getPortataI(), e4.getP());
					T = e4.getOutputs();
				}
				else if(u instanceof Split){
					type = "Split";
					e1 = (Split)u;
					e1.setPortataI(portata);
					observer.notifyFlow(type, e1.getName(), e1.getPortataI(), e1.getPortataU1(), e1.getPortataU2());
					T = e1.getOutputs();
				}
				for(Element a : T) {
					simulateT(observer, T, T[i], type.compareTo("Split") == 0 ? e1.getPortataU1():e4.getPortataByIndex(i));
					i++;
				}
				return;
			}
		}
	}
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		int i;
		Element u;
		Source e;
		String type;
		Element []T = null;
		double portata = 0.0;
		for(i=0;i<num_elements;i++)
		{
			if(system[i] instanceof Source)
			{
				u = system[i];
				type = "Source";
				e = (Source)u;
				observer.notifyFlow(type, e.getName(), NO_FLOW, e.getPortata());
				portata=e.getPortata();
				u = u.getOutput();
				simulateT(observer, T, u, portata);
			}
		}
	}

}
