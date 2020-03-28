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
	
}
