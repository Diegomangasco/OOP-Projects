package Book;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Topic {

	private String key;
	private HashMap<String, Topic> SubTopics = new HashMap<>();
	public Topic(String Key) {
		this.key = Key;
	}
	public String getKeyword() {
        return this.key;
	}
	
	@Override
	public String toString() {
	    return this.key;
	}

	public boolean addSubTopic(Topic topic) {
		if(this.SubTopics.containsValue(topic))
			return false;
		else {
			this.SubTopics.put(topic.getKeyword(), topic);
			return true;
		}
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        return this.SubTopics.values().stream().sorted(new Comparator<Topic>() {
        	@Override
        	public int compare(Topic T1, Topic T2) {
        		return T1.getKeyword().compareTo(T2.getKeyword());
        	}
        }).collect(Collectors.toList());
	}
}
