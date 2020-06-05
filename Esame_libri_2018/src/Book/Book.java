package Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Book {

    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	private HashMap<String, Topic> Topics = new HashMap<>();
	private HashMap<String, Question> Questions = new HashMap<>();
	private HashMap<String, TheoryChapter> TheoryChapters = new HashMap<>();
	private HashMap<String, ExerciseChapter> ExerciseChapters = new HashMap<>();
	private HashMap<String, Assignment> Assignments = new HashMap<>();
	public Topic getTopic(String keyword) throws BookException {
	    if(keyword==null || keyword.isEmpty()) {
	    	throw new BookException();
	    }
	    else {
	    	if(this.Topics.containsKey(keyword)) {
	    		return this.Topics.get(keyword);
	    	}
	    	Topic T = null;
	    	this.Topics.put(keyword, T = new Topic(keyword));
	    	return T;
	    }
	}

	public Question createQuestion(String question, Topic mainTopic) {
        Question Q = new Question (question, mainTopic);
        this.Questions.put(question, Q);
        return Q;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
        TheoryChapter T = new TheoryChapter();
        T.setNumPages(numPages);
        T.setText(text);
        T.setTitle(title);
        this.TheoryChapters.put(title, T);
        return T;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
        ExerciseChapter E = new ExerciseChapter();
        E.setTitle(title);
        E.setNumPages(numPages);
        this.ExerciseChapters.put(title, E);
        return E;
	}

	public List<Topic> getAllTopics() {
		List<Topic> Ret1 = this.TheoryChapters.values().stream().map(T -> T.getTopics()).flatMap(T -> T.stream()).collect(Collectors.toList());
		List<Topic> Ret2 = this.ExerciseChapters.values().stream().map(T -> T.getTopics()).flatMap(T -> T.stream()).collect(Collectors.toList());
		Ret2.addAll(Ret1);
		return Ret2.stream().distinct().sorted(new Comparator<Topic>() {
        	@Override
        	public int compare(Topic T1, Topic T2) {
        		return T1.getKeyword().compareTo(T2.getKeyword());
        	}
		}).collect(Collectors.toList());
	}

	public boolean checkTopics() {
        boolean contain = false;
        List<Topic> Ret = new ArrayList<>();
        for (String S : this.ExerciseChapters.keySet()) {
        	Ret.addAll(this.ExerciseChapters.get(S).getTopics());
        }
        for(String S : this.TheoryChapters.keySet()) {
        	if(this.TheoryChapters.get(S).getTopics().containsAll(Ret)) {
        		contain = true;
        		break;
        	}
        }
        return contain;
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
        Assignment A = new Assignment(ID, chapter);
        this.Assignments.put(ID, A);
        return A;
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
        return this.ExerciseChapters.values().stream().map(E -> E.getQuestion())
        		.flatMap(x -> x.stream()).collect(Collectors.groupingBy(
        				Q -> Q.numAnswers(), HashMap::new, Collector.of(
        						ArrayList<Question>::new, 
        						(List<Question> a, Question Q) -> a.add(Q), 
        						(a,b) -> {a.addAll(b); return a;}, a -> {return a;})));
    }
}
