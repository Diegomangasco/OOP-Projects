package applications;

import java.util.ArrayList;
import java.util.List;

public class Richiedente {

	private String name;
	private ArrayList<String> Capacità = new ArrayList<>();
	public Richiedente(String name, String capacità) {
		this.name = name;
		if(capacità.compareTo("")==0)
			this.Capacità.add("");
		else {
			String [] O = capacità.split(",");
			for(String S : O) {
				this.Capacità.add(S);
			}
		}
	}
	public String getName() {
		return this.name;
	}
	public int getSum(List<Skill> S) {
		int n = 0;
		for(Skill R : S) {
			for(String H : this.Capacità) {
				String [] O = H.split(":");
				if(R.getName().compareTo(O[0])==0)
					n+=Integer.valueOf(O[1]);
			}
		}
		return n;
	}
	public List<String> getC(){
		List<String> Net = new ArrayList<>();
		for(String S : this.Capacità) {
			String[] O = S.split(":");
			Net.add(O[0]);
		}
		return Net;
	}
	public boolean isC() {
		if(this.Capacità.size()>1 || this.Capacità.get(0).compareTo("")!=0)
			return true;
		return false;
	}
}
