package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystemExt extends HSystem {
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	private String stampaLayout(Element system)
	{
		StringBuffer s = new StringBuffer();
		String type = null;
		int i;
		int l=0;
		while(system!=null)
		{
			if((system instanceof Split) || (system instanceof Multisplit))
			{
				Element [] direzioni = null;
				if(system instanceof Multisplit) {
					Multisplit spM = (Multisplit)system;
					direzioni = spM.getOutputs();
					s.append("-> [" + spM.getName() + "] " + "Multisplit ");
					l=s.length() + 12;
				}
				else if(system instanceof Split) {
					Split sp = (Split)system;
					direzioni = sp.getOutputs();
					s.append("-> [" + sp.getName() + "] " + "Split ");
					l=s.length() + 7;
				}
				int notlast = direzioni.length;
				for(Element a : direzioni) {
					s.append("+").append(stampaLayout(a));	//ricorsione
					if(notlast>1) {
						s.append("\n");
						i=0;
						while(i<l) {	
							s.append(" ");
							i++;
						}
						s.append("|").append("\n");
						i=0;
						while(i<l) {	
							s.append(" ");
							i++;
						}
					}
					notlast--;
				}
				return s.toString();
			}
			else
			{
				if(system instanceof Tap) type = "Tap";
				if(system instanceof Sink) type = "Sink";
				s.append("-> [" + system.getName() + "] " + type + " ");
			}
			system = system.getOutput();
		}
		s.append(" *");
		return s.toString();
	}
	
	public String layout(){
		int i;
		StringBuffer s = new StringBuffer();
		for(i = 0; i<num_elements; i++)
		{
			if (system[i] instanceof Source )
			{
				s.append("[" + system[i].getName() + "]" + "Source ");
				s.append(stampaLayout(system[i].getOutput()) + "\n");
			}
		}
		return s.toString();
	}
	
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public void deleteElement(String name) {
		int i;
		Element nameI;
		Element nameU;
		for (i = 0; i<num_elements; i++)
		{
			if(system[i].getName().equals(name))
			{
				if((system[i] instanceof Split) || (system[i] instanceof Multisplit)) //non è richiesta la gestione degli split
				{
					system[i] = null;
					return;
				}
				if((nameU = system[i].getOutput())!=null) //caso rimozione tap e source
				{
					if((nameI = trovaElemento(system[i])) != null)
						nameI.connect(nameU);
				}
				//caso rimozione sink
				else
				{
					if((nameI = trovaElemento(system[i])) != null)
						nameI.connect(null);	//la simulazione si blocca in un vicolo cieco
				}
				system[i] = null;
				break;
			}
		}
		if (i>=num_elements)
			System.out.println("Elemento non trovato");
	}

	private Element trovaElemento(Element element) {
		int j;
		for(j=0; j<num_elements; j++)
		{
			if(system[j].getOutput() == element)
				return system[j];
		}
		return null;
	}

	/**
	 * starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	private void simulateTAlt(SimulationObserverExt observer, Element[] T, ElementExt u, double portata)
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
				if(u.getMaxFlow()<portata)
				{
					observer.notifyFlowError(type, u.getName(), portata, u.getMaxFlow());
				}
				type = "Tap"; 
				e3 = (Tap)u;
				e3.setPortataI(portata);
				observer.notifyFlow(type, e3.getName(), e3.getPortataI(), e3.getPortataU());
				portata = e3.getPortataU();
				u = (ElementExt)u.getOutput();
			}
			if (u instanceof Sink) {
				if(u.getMaxFlow()<portata)
				{
					observer.notifyFlowError(type, u.getName(), portata, u.getMaxFlow());
				}
				type = "Sink"; 
				e2 = (Sink)u;
				e2.setPortataI(portata);
				observer.notifyFlow(type, e2.getName(), e2.getPortataI(), NO_FLOW);
				u = (ElementExt)u.getOutput();
			}
			if ((u instanceof Split) || (u instanceof Multisplit)) {
				if(u instanceof Multisplit){
					if(u.getMaxFlow()<portata)
					{
						observer.notifyFlowError(type, u.getName(), portata, u.getMaxFlow());
					}
					type = "Multisplit";
					e4 = (Multisplit)u;
					e4.setPortataI(portata);
					observer.notifyFlow(type, e4.getName(), e4.getPortataI(), e4.getP());
					T = e4.getOutputs();
				}
				else if(u instanceof Split){
					if(u.getMaxFlow()<portata)
					{
						observer.notifyFlowError(type, u.getName(), portata, u.getMaxFlow());
					}
					type = "Split";
					e1 = (Split)u;
					e1.setPortataI(portata);
					observer.notifyFlow(type, e1.getName(), e1.getPortataI(), e1.getPortataU1(), e1.getPortataU2());
					T = e1.getOutputs();
				}
				for(Element a : T) {
					simulateTAlt(observer, T, (ElementExt)T[i], type.compareTo("Split") == 0 ? e1.getPortataU1():e4.getPortataByIndex(i));
					i++;
				}
				return;
			}
		}
	}
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		if(enableMaxFlowCheck == false) {
			simulate((SimulationObserver)observer); 
			return;
		}
		int i;
		ElementExt u;
		Source e;
		String type;
		Element []T = null;
		double portata = 0.0;
		for(i=0;i<num_elements;i++)
		{
			if(system[i] instanceof Source)
			{
				u = (ElementExt)system[i];
				type = "Source";
				e = (Source)u;
				observer.notifyFlow(type, e.getName(), NO_FLOW, e.getPortata());
				portata=e.getPortata();
				u = (ElementExt)u.getOutput();
				simulateTAlt(observer, T, u, portata);
			}
		}
		
		
	}
	
}
