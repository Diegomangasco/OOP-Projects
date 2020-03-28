package university;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	

	private String name;
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.name=name;
	}
	
	/**
	 * Getter for the name of the university
	 * @return name of university
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first
	 * @param last
	 */
	private String first;
	private String last;
	public void setRector(String first, String last)
	{
		this.first=first;
		this.last=last;
	}
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return
	 */
	public String getRector(){
		return this.first + " " + this.last;
	}
	
	/**
	 * Enroll a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * @return
	 */
	private final int number = 10000;	//start ID
	private int i = 0;	//counter
	private Student [] vector = new Student [1000]; 
	public int enroll(String first, String last){
		if (i>=1000) return -1;
		vector[i] = new Student(first, last);
		i++;
		return (i-1)+number;
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the id of the student
	 * @return information about the student
	 */
	public String student(int id){
		int n = id - number;
		return id + " " + this.vector[n].getName() + " " + this.vector[n].getSurname();
	}
	
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * @return the unique code assigned to the course
	 */
	private final int number2 = 10;	//start number
	private int j = 0;	//counter
	private Course [] c = new Course[50]; 
	public int activate(String title, String teacher){
		if(j>=50) return -1;
		c[j] = new Course(title, teacher);
		j++;
		return (j-1)+number2;
	}
	
	/**
	 * Retrieve the information for a given course
	 * 
	 * @param code unique code of the course
	 * @return information about the course
	 */
	public String course(int code){
		int n = code-number2;
		return n + "," + this.c[n].getName() + "," + this.c[n].getTeacher();
	}
	
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		int i = c[courseCode-number2].setS((studentID),vector[studentID-number].getName(),
				vector[studentID-number].getSurname());
		if(i==-1) 
		{
			System.out.println("Corso pieno!");
		}
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		return c[courseCode-number2].getList();
	}

	/**
	 * Retrieves the study plan for a student
	 * 
	 * @param studentID id of the student
	 * @return list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		int k;
		StringBuffer student=new StringBuffer();
		for(k=0;k<j;k++)
		{
			if(c[k].rollList(studentID)==1)
			{
				student.append(k+number2).append(",").append(c[k].getName()).append(",")
				.append(c[k].getTeacher()).append("\n");
			}
		}
		return student.toString();
	}
}
