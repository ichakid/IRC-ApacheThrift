import java.util.ArrayList;
import java.util.List;

import chatservice.Message;

public class Channel {
	private String name;
	private List<User> members;
	private Message message;
	
	public Channel() {
		name = "";
		members = new ArrayList<User>();
		message = new Message();
	}
	
	public Channel(String name) {
		members = new ArrayList<User>();
		message = new Message();
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
		this.message = m;
		for (User u :  members) {
			u.addMessage(m);
		}
	}
}
