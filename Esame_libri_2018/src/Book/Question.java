package Book;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Question {
	private String question;
	private Topic topic;
	private HashMap<String, Boolean> Answers = new HashMap<>();
	public Question(String question, Topic T) {
		this.question = question;
		this.topic = T;
	}
	public String getQuestion() {
		return this.question;
	}
	
	public Topic getMainTopic() {
		return this.topic;
	}

	public void addAnswer(String answer, boolean correct) {
		this.Answers.put(answer, correct);
	}
	
    @Override
    public String toString() {
        return this.question + " " + "(" + this.topic.getKeyword() + ")";
    }

	public long numAnswers() {
	    return Long.valueOf(this.Answers.size());
	}

	public Set<String> getCorrectAnswers() {
		Set<String> Ret = new TreeSet<>();
		for(String S : this.Answers.keySet()) {
			if(this.Answers.get(S).booleanValue()==true) {
				Ret.add(S);
			}
		}
		return Ret;
	}

	public Set<String> getIncorrectAnswers() {
		Set<String> Ret = new TreeSet<>();
		for(String S : this.Answers.keySet()) {
			if(this.Answers.get(S).booleanValue()==false) {
				Ret.add(S);
			}
		}
		return Ret;
	}
}
