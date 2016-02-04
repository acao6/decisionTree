package decisionTreeHomework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data<Type extends Event> implements Serializable {
	
	private static final long serialVersionUID = -1924470211233573788L;
	
	private List<Type> events = new ArrayList<>();
	
	public List<Type> getEvents() {
		return events;
	}
	
	public void addEvent(Type event) {
		events.add(event);
	}
}
