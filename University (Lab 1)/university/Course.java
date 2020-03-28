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
		private String first;
		private String last;
		
		private S(String name, String surname, int N)
		{
			first=name;
			last=surname;
			m = N;
			next=null;
		}
	}
	 private S head = null;
	 private int max=0;
	 public int setS(int mat, String name, String surname) //insertion in queue
	 {
		if(max>=100) {return -1;}
		S N =new S(name, surname, mat); 
		if(head==null)	//empty list
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
	
	 public String getList()	//scroll the list and memorize all in a Buffer
	 {
		 StringBuffer elenco = new StringBuffer();
		 S x = this.head;
		 while(x!=null)
		 {
			 elenco.append(x.m).append(" ").append(x.first).append(" ").append(x.last)
			 .append("\n");
			 x=x.next;
		 }
		 return elenco.toString();
	 }
	 public int rollList(int mat)	//scroll the list and search the student
	 {
		 S x = this.head;
		 while(x!=null)
		 {
			 if(x.m==mat)
			 {
				 return 1;
			 }	 
			 x=x.next;
		 }
		 return 0;
	 }
	
}

