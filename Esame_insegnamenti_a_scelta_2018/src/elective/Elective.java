package elective;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Elective {

	private String nome;
	private int numPosti;
	private Long firstChoise = Long.valueOf(0);
	private Long secondChoise = Long.valueOf(0);
	private Long thirdChoise = Long.valueOf(0);
	private ArrayList<Student> Students = new ArrayList<>();
	
 	public Elective(String nome, int numPosti) {
		this.nome = nome;
		this.numPosti = numPosti;
	}

	public String getNome() {
		return this.nome;
	}
	public int getNumPosti() {
		return this.numPosti;
	}
	public void setFirstChoise() {
		this.firstChoise++;
	}
	public void setSecondChoise() {
		this.secondChoise++;
	}
	public void setThirdChoise() {
		this.thirdChoise++;
	}
	public Long getFirstChoise() {
		return this.firstChoise;
	}
	public Long getSecondChoise() {
		return this.secondChoise;
	}
	public Long getThirdChoise() {
		return this.thirdChoise;
	}
	
	public boolean addStudent(Student S) {
		if(this.numPosti>this.Students.size()) {
			this.Students.add(S);
			S.setAssigned(this);
			return true;
		}
		return false;
	}
	
	public List<String> getStudents(){
		return this.Students.stream().sorted((S1, S2) -> { return (int) -(S1.getMedia()-S2.getMedia());}).map(S -> S.getNome()).collect(Collectors.toList());
	}
}
