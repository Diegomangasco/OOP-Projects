package elective;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private String nome;
	private double media;
	
	private ArrayList<String> Electives = new ArrayList<>();
	
	private Elective Assigned = null;
	
	public Student(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
	public double getMedia() {
		return this.media;
	}
	
	public void setElectives(List<String> courses) {
		Electives.addAll(courses);
	}
	public List<String> getElectives(){
		return this.Electives;
	}
	public void setMedia(double newMedia) {
		this.media = newMedia;
	}
	public void setAssigned(Elective E) {
		this.Assigned = E;
	}
	public Elective getAssigned() {
		return this.Assigned;
	}

}
