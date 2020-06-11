package elective;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {
	
	private HashMap<String, Elective> Electives = new HashMap<>();
	private HashMap<String, Student> Students = new HashMap<>();
	private ArrayList<Notifier> Notifiers = new ArrayList<>();
	private ArrayList<Student> NotAssigned = new ArrayList<>();
    /**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
	
    public void addCourse(String name, int availablePositions) {
        this.Electives.put(name, new Elective(name, availablePositions));
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
    	return 	this.Electives.
    			entrySet().
    			stream().
    			sorted((E1, E2) -> E1.getKey().compareTo(E2.getKey())).
    			map(E -> E.getKey()).
    			collect(Collectors.toCollection(TreeSet<String>::new));
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, double gradeAverage){
        if(this.Students.containsKey(id)) {
        	this.Students.get(id).setMedia(gradeAverage);
        }
        else {
        	this.Students.put(id, new Student(id));
        	this.Students.get(id).setMedia(gradeAverage);
        }
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
        return this.Students.
        		entrySet().
        		stream().
        		map(E -> E.getKey()).
        		collect(Collectors.toList());
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
        return this.Students.
        		entrySet().
        		stream().
        		filter(E -> E.getValue().getMedia()>=inf && E.getValue().getMedia()<=sup).
        		map(E -> E.getKey()).
        		collect(Collectors.toList());
    }


    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {
        if(!this.Students.containsKey(id))
        	throw new ElectiveException();
        for(String S: courses) {
        	if(!this.Electives.containsKey(S)) {
        		throw new ElectiveException();
        	}
        }
        if(!(courses.size()>=1 && courses.size()<=3))
        	throw new ElectiveException();
        
        for(Notifier N : this.Notifiers) {
        	N.requestReceived(id);
        }
        for(int i = 0; i<courses.size(); i++) {
        	switch(i) {
        	case 0:
        		this.Electives.get(courses.get(i)).setFirstChoise();
        		break;
        	case 1:
        		this.Electives.get(courses.get(i)).setSecondChoise();
        		break;
        	case 2:
        		this.Electives.get(courses.get(i)).setThirdChoise();
        		break;
        	}
        }
        this.Students.get(id).setElectives(courses);
        return courses.size();
        
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
       return this.Electives.
       		values().
       		stream().
       		collect(Collectors.groupingBy(E -> E.getNome(),
       				HashMap::new,
       				Collector.of(ArrayList<Long>::new, 
       						(List<Long> a, Elective E) -> {
       							a.add(E.getFirstChoise());
       							a.add(E.getSecondChoise());
       							a.add(E.getThirdChoise());
       						}, (List<Long> a, List<Long> b) -> {return a;}, a -> {return a;})));
       
    }
    
    
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
    	long z = 0;
    	List<String> Net = this.Students.values().stream().sorted((S1, S2) -> {return (int) -(S1.getMedia()-S2.getMedia());}).map(S -> S.getNome()).collect(Collectors.toList());
        for(String S : Net) {
        	if(this.Students.get(S).getElectives().size()==0) {
        		z++;
				this.NotAssigned.add(this.Students.get(S));
        	}
        	else if(this.Electives.get(this.Students.get(S).getElectives().get(0)).addStudent(this.Students.get(S))==false) {
        			if(this.Students.get(S).getElectives().size()<=1) {
        				z++;
						this.NotAssigned.add(this.Students.get(S));
        			}
        			else if(this.Electives.get(this.Students.get(S).getElectives().get(1)).addStudent(this.Students.get(S))==false) {
        					if(this.Students.get(S).getElectives().size()<=2) {
        						z++;
        						this.NotAssigned.add(this.Students.get(S));
        					}
        					else if(this.Electives.get(this.Students.get(S).getElectives().get(2)).addStudent(this.Students.get(S))==false) {
        							z++;
        							this.NotAssigned.add(this.Students.get(S));
        						}
        					}
        				}
        		for(Notifier N : this.Notifiers) {
        			if(this.Students.get(S).getAssigned()!=null)
        				N.assignedToCourse(S, this.Students.get(S).getAssigned().getNome());
            	}
        	}
        return z;
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
        return this.Electives.values().stream().collect(Collectors.groupingBy(
        		E -> E.getNome(),
        		HashMap::new,
        		Collector.of(ArrayList<String>::new,
        				(List<String> a, Elective E) -> {a.addAll(E.getStudents());}, 
        				(List<String> a, List<String> b) -> {a.addAll(b); return a;}, 
        				a -> {return a;})));
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
        this.Notifiers.add(listener);
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
    	double success = 0.0;
    	double sum = 0.0;
        switch(choice) {
        case 1:
        	for(String S : this.Students.keySet()) {
        		if(this.Students.get(S).getAssigned()!=null && 
        				this.Students.get(S).getElectives().size()!=0 &&
        				this.Students.get(S).getAssigned().getNome().compareTo(this.Students.get(S).getElectives().get(0))==0) {
        			sum++;
        		}
        	}
        	success = sum/this.Students.keySet().size();
        	break;
        case 2:
        	for(String S : this.Students.keySet()) {
        		if(this.Students.get(S).getAssigned()!=null && 
        				this.Students.get(S).getElectives().size()>1 &&
        				this.Students.get(S).getAssigned().getNome().compareTo(this.Students.get(S).getElectives().get(1))==0) {
        			sum++;
        		}
        	}
        	success = sum/this.Students.keySet().size();
        	break;
        case 3:
        	for(String S : this.Students.keySet()) {
        		if(this.Students.get(S).getAssigned()!=null && 
        				this.Students.get(S).getElectives().size()>2 &&
        				this.Students.get(S).getAssigned().getNome().compareTo(this.Students.get(S).getElectives().get(2))==0) {
        			sum++;
        		}
        	}
        	success = sum/this.Students.keySet().size();
        	break;
        }
        return success;
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        return this.NotAssigned.stream().map(S -> S.getNome()).collect(Collectors.toList());
    }
    
    
}
