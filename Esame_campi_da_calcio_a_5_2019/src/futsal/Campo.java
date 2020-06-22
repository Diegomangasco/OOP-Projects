package futsal;

import futsal.Fields.Features;

public class Campo implements FieldOption{

	@Override
	public int getField() {
		// TODO Auto-generated method stub
		return this.code;
	}

	@Override
	public int getOccupation() {
		// TODO Auto-generated method stub
		return this.occupation;
	}
	private Features Feat;
	private int code;
	private int occupation = 0;
	
	public Campo(int code, Features F) {
		this.code = code;
		this.Feat = F;
	}
	
	public void setOccupation() {
		this.occupation++;
	}
	public Features getF() {
		return this.Feat;
	}
}
