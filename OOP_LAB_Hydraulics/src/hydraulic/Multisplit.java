package hydraulic;

import java.util.ArrayList;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {

	/**
	 * Constructor
	 * @param name
	 * @param numOutput
	 */
	private String name;
	private int numOutput;
	private ArrayList <Element> Elem = new ArrayList<Element>();
	private double portataI;
	private double [] portateU;
	private double [] proportions;
	private double maxFlow;
	public Multisplit(String name, int numOutput) {
		super(name);
		this.numOutput = numOutput;
		portateU = new double[this.numOutput];
		proportions = new double[this.numOutput];
	}
	
	public double[] getP()
	{
		return this.portateU;
	}
	public double getPortataI()
	{
		return this.portataI;
	}
	public double getPortataByIndex(int index)
	{
		return this.portateU[index];
	}
	public void setPortataI(double portata)
	{
		this.portataI = portata;
		for(int i = 0; i<this.numOutput; i++)
			this.portateU[i] = this.proportions[i]*this.portataI;
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
    	Element [] out = new Element[this.numOutput];
    	for(int i = 0; i<this.numOutput; i++)
    	{
    		if(Elem.get(i)==null)
    			out[i] = null;
    		else
    			out[i] = Elem.get(i);
    	}
    	return out;
    }

    /**
     * connect one of the outputs of this split to a
     * downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
	public void connect(Element elem, int noutput){
		if(noutput>=this.numOutput)
		{
			System.out.println("Errore nel numero!");
			return;
		}
		Elem.add(noutput, elem);
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		int lung = proportions.length;
		for(int i = 0; i<lung && i<this.numOutput; i++)
		{
			this.proportions[i] = proportions[i];
		}
	}
		
}
