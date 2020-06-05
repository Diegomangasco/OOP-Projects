package Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ExerciseChapter {

	private String title;
	private int numPages;
	private ArrayList<Question> Questions = new ArrayList<>();
    
	public List<Topic> getTopics() {
		return this.Questions.stream().map(Q -> Q.getMainTopic()).distinct().sorted(new Comparator<Topic>() {
        	@Override
        	public int compare(Topic T1, Topic T2) {
        		return T1.getKeyword().compareTo(T2.getKeyword());
        	}
        }).collect(Collectors.toList());
	}
	
	public List<Question> getQuestion(){
		return this.Questions;
	}
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String newTitle) {
    	this.title = newTitle;
    }

    public int getNumPages() {
        return this.numPages;
    }
    
    public void setNumPages(int newPages) {
    	this.numPages = newPages;
    }
    

	public void addQuestion(Question question) {
		this.Questions.add(question);
	}	
}
