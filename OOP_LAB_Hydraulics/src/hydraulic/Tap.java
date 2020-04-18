package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {
	
	private Element uscita = null;
	private double portataI;
	private double portataU;
	private boolean open1;
	public Tap(String name) {
		super(name);
		//TODO: complete
	}
	
	/**
	 * Defines whether the tap is open or closed.
	 * 
	 * @param open  opening level
	 */
	@Override
	public void setPortataI(double portata)
	{
		this.portataI=portata;
		if(this.open1==true) this.portataU=this.portataI;
		else this.portataU = 0.0;
	}
	public void setOpen(boolean open){
		if(open == true)
		{
			this.open1=true;
		}
		//TODO: complete
	}
	@Override
	public double getPortataI()
	{
		return this.portataI;
	}
	
	public double getPortataU()
	{
		return this.portataU;
	}
}
