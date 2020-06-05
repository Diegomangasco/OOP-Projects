package Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class TheoryChapter {

	private String text;
	private String title;
	private int numPages;
	private ArrayList<Topic> Topics = new ArrayList<>();
    public String getText() {
		return this.text;
	}

    public void setText(String newText) {
    	this.text = newText;
    }

	public List<Topic> getTopics() {
		List<Topic> Ret = new ArrayList<>();
        for(Topic T: this.Topics) {
        	recursive(Ret, T);
        }
        Ret.addAll(this.Topics);
        return Ret.stream().distinct().sorted(new Comparator<Topic>() {
        	@Override
        	public int compare(Topic T1, Topic T2) {
        		return T1.getKeyword().compareTo(T2.getKeyword());
        	}
        }).collect(Collectors.toList());
	}
	private void recursive(List<Topic> Ret, Topic T) {
		if(T.getSubTopics()==null)
			return;
		Ret.addAll(T.getSubTopics());
		for(Topic To : T.getSubTopics()) {
			recursive(Ret, To);
		}
		return;
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
    
    public void addTopic(Topic topic) {
    	this.Topics.add(topic);
    	this.Topics.addAll(topic.getSubTopics());
    }
    
}
