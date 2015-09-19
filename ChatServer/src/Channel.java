import java.util.ArrayList;
import java.util.List;

import chatservice.Message;

public class Channel {
	private String name;
	private List<User> members;
	private List<Message> messages;
	
	public Channel() {
		members = new ArrayList<User>();
		messages = new ArrayList<Message>();
		name = "channelname";		//generate random name for channel
	}
	
	public Channel(String name) {
		members = new ArrayList<User>();
		messages = new ArrayList<Message>();
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
		this.messages.add(m);
		for (User u :  members) {
			u.addMessage(m);
		}
	}
}
