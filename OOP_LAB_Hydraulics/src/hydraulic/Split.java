package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {

	/**
	 * Constructor
	 * @param name
	 */
	private Element [] Tconnection = new Element[] {null, null};
	private double portataI;
	private double portataU1;
	private double portataU2;
	public Split(String name) {
		super(name);
		//TODO: complete
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs(){
    	return this.Tconnection;
    }

    /**
     * connect one of the outputs of this split to a
     * downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
	public void connect(Element elem, int noutput){
		if(noutput==0) {
			this.Tconnection[0]= elem;
			return;
		}
		if(noutput==1)
		{
			this.Tconnection[1]=elem;
			return;
		}
		System.out.println("Incorrect value for noutput!");	
	}
	public void setPortataI(double portata)
	{
		this.portataI = portata;
		this.portataU1=portata/2.0;
		this.portataU2=this.portataU1;
	}
	public double getPortataU1()
	{
		return this.portataU1;
	}
	public double getPortataU2()
	{
		return this.portataU2;
	}
	public double getPortataI()
	{
		return this.portataI;
	}
	
}

