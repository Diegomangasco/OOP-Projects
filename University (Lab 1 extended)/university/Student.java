package university;

public class Student {
	private String first;
	private String last;
	public Student(String first, String last)
	{
		this.first=first;
		this.last=last;
	}
	public String getName()
	{
		return this.first;
	}
	public String getSurname()
	{
		return this.last;
	}
	protected class Courses{
		private int march;
		private int courseID;
		private Courses(int courseID)
		{
			this.march=-1;
			this.courseID=courseID;
		}
		protected int getCourse()
		{
			return this.courseID;
		}
	}
	private final int max = 25;
	private int count = 0;
	private int pass = 0;
	Courses []R = new Courses[25];
	public int getCount()
	{
		return this.count;
	}
	public int getPass()
	{
		return this.pass;
	}
	public int setVector(int st, int cd)	//nuovo vettore di corsi per lo studente
	{
		if(count<max) {
			R[count] = new Courses(cd);
			count++;
			return 1;
		}
		else
		{
			System.out.println("Studente pieno!");
			return -1;
		}
	}
	public void setVoto(int cd, int voto)	//inserisco il voto nel corso
	{
		int i;
		for(i=0;i<count;i++)
		{
			if(R[i].courseID==cd)
			{
				R[i].march=voto;
				this.pass++;
			}
		}
	}
	public float getAVG() {	//calcolo media voti dello studente
		if(count<1) {
			return -1;
		}
		int i;
		float sum=0;
		for(i=0;i<count;i++)
		{
			sum+=(float)R[i].march;
		}
		return sum/(float)count;
	}
	
	public int getVoto(int cd)	//se lo studente frequenta il corse richiesto, ritorno il voto
	{
		for(int i=0;i<count;i++)
		{
			if(R[i].courseID==cd) return R[i].march;
		}
		return -1;
	}
}
