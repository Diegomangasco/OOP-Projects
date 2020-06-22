package applications;

import java.util.ArrayList;
import java.util.List;

public class Richiedente {

	private String name;
	private ArrayList<String> Capacit� = new ArrayList<>();
	public Richiedente(String name, String capacit�) {
		this.name = name;
		if(capacit�.compareTo("")==0)
			this.Capacit�.add("");
		else {
			String [] O = capacit�.split(",");
			for(String S : O) {
				this.Capacit�.add(S);
			}
		}
	}
	public String getName() {
		return this.name;
	}
	public int getSum(List<Skill> S) {
		int n = 0;
		for(Skill R : S) {
			for(String H : this.Capacit�) {
				String [] O = H.split(":");
				if(R.getName().compareTo(O[0])==0)
					n+=Integer.valueOf(O[1]);
			}
		}
		return n;
	}
	public List<String> getC(){
		List<String> Net = new ArrayList<>();
		for(String S : this.Capacit�) {
			String[] O = S.split(":");
			Net.add(O[0]);
		}
		return Net;
	}
	public boolean isC() {
		if(this.Capacit�.size()>1 || this.Capacit�.get(0).compareTo("")!=0)
			return true;
		return false;
	}
}
