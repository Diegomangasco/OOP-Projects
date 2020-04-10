package university;

public class Course {
	private String name;
	private String teacher;
	
	public Course(String name, String teacher)
	{
		this.name=name;
		this.teacher=teacher;
	}
	
	public String getName()
	{
		return this.name;
	}
	public String getTeacher()
	{
		return this.teacher;
	}
	
	private class S{	//inner class
		private S next;
		private int m;
		Student S;
		
		private S(String name, String surname, int N)
		{
			S = new Student(name,surname);
			m = N;
			next=null;
		}
	}
	 private S head = null;
	 private int max=0;
	 public int setS(int mat, String name, String surname) //inserimento in coda alla lista
	 {
		if(max>=100) {return -1;}
		S N =new S(name, surname, mat);
		if(head==null)	//caso della lista vuota
		{
			this.head = N;
			return 1;
		}
		S x = this.head;
		S p = null;
		while(x!=null)
		{
			p=x;
			x = x.next;
		}
		p.next=N;
		max++;
		return 1;
	 }
	
	 public String getList()	//scorro la lista del corso e memorizzo tutto in un buffer
	 {
		 StringBuffer elenco = new StringBuffer();
		 S x = this.head;
		 while(x!=null)
		 {
			 elenco.append(x.m).append(" ").append(x.S.getName()).append(" ").append(x.S.getSurname())
			 .append("\n");
			 x=x.next;
		 }
		 return elenco.toString();
	 }
	
}

