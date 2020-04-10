package university;

import java.util.logging.Logger;

public class UniversityExt extends University{
	
	private final static Logger logger = Logger.getLogger("University");

	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}
	
	public void exam(int studentId, int courseID, int grade) {
		vector[studentId-number].setVoto(courseID,grade);
		writer.println("Student: " + studentId + " " + "took an exam in course " + courseID + " and won " + grade + " points");
	}

	public String studentAvg(int studentId) {
		float result=vector[studentId-number].getAVG();
		if(result==-1)
		{
			return "Student" + " " + studentId + " " + "hasn't taken any exams";
		}
		else return "Student" + " " + studentId + " " + result;
	}
	
	public String courseAvg(int courseId) {
		int k;
		float sum=0;
		int voto;
		int count=0;
		for(k=0;k<i;k++)
		{
			if((voto=vector[k].getVoto(courseId))>=0)	//per ogni studente prendo il voto dell'esame cd
			{
				sum+=(float)voto;
				count++;
			}
		}
		if (count==0) return "No student has taken the exam in course" + " " + c[courseId-number2].getName();
		
		return "The average for the course" + " " + c[courseId-number2].getName() + " is: " + sum/(float)count;
	}
	private static void sort(float [] best, int [] cls)
	{
		float tmp;
		int tmp1;
		for(int k=0;k<best.length;k++)
		{
			for(int t=0;t<best.length-k-1;t++)
			{
				if(best[t]<best[t+1])
				{
					tmp=best[t];
					best[t]=best[t+1];
					best[t+1]=tmp;
					tmp1=cls[t];
					cls[t]=cls[t+1];
					cls[t+1]=tmp1;
				}
			}
		}
	}
	
	public String topThreeStudents() {
		float []best = new float[i];
		int []cls = new int[i];
		int k;
		StringBuffer sol=new StringBuffer();
		for(k=0;k<i;k++)	//per ogni studente memorizzo la media e la posizione nel vettore di studenti
		{
			best[k]=(vector[k].getAVG()+(vector[k].getPass()/vector[k].getCount()))*10;
			cls[k]=k;
		}
		sort(best,cls);	//ordino in modo decrescente e tengo aggiornata la classifica con cls
		for(k=0;k<3 && k<best.length;k++)
		{
			sol.append(vector[cls[k]].getName()).append(" ").append(vector[cls[k]].getSurname()).append(":")
			.append(" ").append(best[k]).append("\n");
		}
		return sol.toString();
	}
}
