package Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Assignment {

	private String ID;
	private ExerciseChapter EC;
	private HashMap<Question, List<String>> RisposteDate = new HashMap<>();
	private ArrayList<Double> Points = new ArrayList<>();
	public Assignment(String ID, ExerciseChapter EC) {
		this.EC = EC;
		this.ID = ID;
	}
    public String getID() {
        return this.ID;
    }

    public ExerciseChapter getChapter() {
        return this.EC;
    }

    public double addResponse(Question q, List<String> answers) {
        this.RisposteDate.put(q, answers);
        double N = q.numAnswers();
        double FP = 0.0;
        double FN = 0.0;
        for(String S: q.getIncorrectAnswers()) {
        	if(answers.contains(S)) {
        		FP++;
        	}
        }
        for(String S: q.getCorrectAnswers()) {
        	if(!answers.contains(S)) {
        		FN++;
        	}
        }
        double Res = (N-FP-FN)/N;
        this.Points.add(Double.valueOf(Res));
        return Res;
    }
    
    public double totalScore() {
    	double Sum = 0.0;
        for(Double D : this.Points) {
        	Sum+=D.doubleValue();
        }
        return Sum;
    }

}