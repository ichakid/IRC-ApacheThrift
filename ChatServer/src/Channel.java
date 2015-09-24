import java.util.ArrayList;
import java.util.List;

import chatservice.Message;

public class Channel {
	private String name;
	private List<User> members;
	
	public Channel() {
		name = "";
		members = new ArrayList<User>();
	}
	
	public Channel(String name) {
		members = new ArrayList<User>();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addMember(User u) {
		this.members.add(u);
	}
	
	public void removeMember(User u) {
		this.members.remove(u);
	}
	
	public void addMessage(Message m) {
		for (User u :  members) {
			Message msg = m.deepCopy();
			u.addMessage(msg);
		}
	}
}
