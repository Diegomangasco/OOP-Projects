

import applications.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;
public class Test {
	public static void main(String[] args) throws ApplicationException {
		HandleApplications ha = new HandleApplications();
		
		ha.addSkills("java", "c++", "javascript", "sql", "html", "uml", "bpmn", "sw design");
		try {
			ha.addSkills("c#", "sql"); //sql duplicated
		} catch (ApplicationException e) {System.out.println(e.getMessage());}
		ha.addPosition("programmer", "java", "sql");
		ha.addPosition("programmer1","java", "c++");
		ha.addPosition("gui designer junior", "javascript", "html");
		ha.addPosition("gui designer senior", "javascript", "java", "html");
		ha.addPosition("sw team leader", "sw design", "uml", "bpmn", "sql");
		try {
			ha.addPosition("programmer", "c++", "sql"); //programmer duplicated
		} catch (ApplicationException e) {System.out.println(e.getMessage());}
		try {
			ha.addPosition("programmer2", "c", "sql"); //c undefined
		} catch (ApplicationException e) {System.out.println(e.getMessage());}
		Skill javaSkill = ha.getSkill("java");
		List<Position> positionsWithJava = javaSkill.getPositions();
		System.out.println(positionsWithJava.get(0).getName()); //gui designer senior
		Position programmerPos = ha.getPosition("programmer");
		
		ha.addApplicant("john", "java:9,sql:7");
		ha.addApplicant("david", "sql:8,java:7");
		ha.addApplicant("mary", "javascript:6,html:9,java:7");
		ha.addApplicant("jane", "javascript:7,html:4");
		ha.addApplicant("james", "uml:7,bpmn:7,sw design:9");
		try {
			ha.addApplicant("james", "sql:8,java:7");//james duplicated
		} catch (ApplicationException e) {System.out.println(e.getMessage());}
		try {
			ha.addApplicant("bob", "sql:11,java:7");//11 invalid level
		} catch (ApplicationException e) {System.out.println(e.getMessage());}
		try {
			ha.addApplicant("ted", "sql:9,xml:7");//xml undefined
		} catch (ApplicationException e) {System.out.println(e.getMessage());}
		String maryCpb = ha.getCapabilities("mary");
		System.out.println(maryCpb);  //html:9,java:7,javascript:6
		
		ha.enterApplication("john", "programmer");
		ha.enterApplication("david", "programmer");
		ha.enterApplication("mary", "gui designer junior");
		ha.enterApplication("jane", "gui designer junior");
		try {
			ha.enterApplication("james", "sw team leader"); //james is not competent in sql
		} catch (ApplicationException e) {System.out.println(e.getMessage());}
		try {
			ha.enterApplication("mary", "gui designer senior"); //mary has already applied for a position
		} catch (ApplicationException e) {System.out.println(e.getMessage());}
		
		System.out.println(programmerPos.getApplicants()); //[david, john]
		
		int capSum = ha.setWinner("john", "programmer"); //16
		System.out.println(capSum);
		System.out.println(programmerPos.getWinner()); //john
		try {
			ha.setWinner("david", "programmer"); //winner already set
		} catch (ApplicationException e) {System.out.println(e.getMessage());}
		try {
			ha.setWinner("jane", "gui designer junior"); //11 instead of 12+
		} catch (ApplicationException e) {System.out.println(e.getMessage());}

		
		SortedMap<String, Long> skill_nApplicants = ha.skill_nApplicants();
		System.out.println(skill_nApplicants);
		//{bpmn=1, html=2, java=3, javascript=2, sql=3, sw design=1, uml=1}
		String maxPosition = ha.maxPosition();
		System.out.println(maxPosition); //gui designer junior
	}

    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * This function serves the only purpose of checking whether
     * your entered the required personal information.
     */
    private static void loadStudentInfo() {
        Properties p = new Properties();
        try{
            p.load(new FileInputStream("Student.txt"));
        }catch(IOException e){
            System.err.println("Could not open the file Student.txt");
            System.err.flush();
            JOptionPane.showMessageDialog(null, "Missing student information!\n\nPlease fill in the Student.txt file before submitting the final version.",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
        if( p.getProperty("ID","").equals("")){
            System.err.println("Missing student information. Please fill in the Student.txt file");
            System.err.flush();
            JOptionPane.showMessageDialog(null, "Missing student information!\n\nPlease fill in the Student.txt file before submitting the final version.",
                                            "Error",JOptionPane.ERROR_MESSAGE);
        }else{
            System.out.println("Project by " + p.getProperty("FirstName") + " " 
                                             + p.getProperty("LastName") 
                                             + " (" + p.getProperty("ID") + ")");
            System.out.flush();
        }
    }
    static{
        loadStudentInfo(); // this is to remember you to fill in the Student.txt file
    }
}

