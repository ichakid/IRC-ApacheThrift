import java.util.ArrayList;
import java.util.List;

import chatservice.Message;

public class User {
	private String clientKey;
	private String nick;
	private List<Message> messages;
	private List<Channel> channels;
	
	public User() {
		this.clientKey = "";
		this.nick = "";
		this.messages = new ArrayList<Message>();
		this.channels = new ArrayList<Channel>();
	}
	
	public User(String key) {
		String clientKey = key;
//		this.nick = "nickname";			//generate random nickname for user
		this.messages = new ArrayList<Message>();
		this.channels = new ArrayList<Channel>();
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getNick() {
		return nick;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	
	public void addMessage(Message m) {
		messages.add(m);
	}
	
	public void addChannel(Channel channel) {
		channels.add(channel);
	}
	
	public void removeChannel(Channel channel) {
		channels.remove(channel);
	}
	
	public String getClientKey() {
		return this.clientKey;
	}
}
