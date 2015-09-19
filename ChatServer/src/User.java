import java.util.ArrayList;
import java.util.List;

import chatservice.Message;

public class User {
	private String clientKey;
	private String nick;
	private Message message;
	private ChatServer.Channels channels;
	
	public User() {
		this.clientKey = "";
		this.nick = "";
		this.message = new Message();
		this.channels = new ChatServer.Channels();
	}
	
	public User(String key) {
		this.clientKey = key;
		this.message = new Message();
		this.channels = new ChatServer.Channels();
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getNick() {
		return nick;
	}
	
	public Message getMessage() {
		Message m = message;
		message = new Message();
		return m;
	}
	
	public void addMessage(Message m) {
		message = m;
	}
	
	public void addMessageToAllChannels(Message m) {
		channels.addMessage(m);
	}
	
	public void addChannel(Channel channel) {
		channels.addChannel(channel);
	}
	
	public void removeChannel(Channel channel) {
		channels.removeChannel(channel);
	}
	
	public String getClientKey() {
		return this.clientKey;
	}
}
