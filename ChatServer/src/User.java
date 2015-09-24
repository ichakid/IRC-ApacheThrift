import java.util.ArrayList;
import java.util.List;

import chatservice.Message;

public class User {
	private String clientKey;
	private String nick;
	private List<Message> message;
	private ChatServer.Channels channels;
	
	public User() {
		this.clientKey = "";
		this.nick = "";
		this.message = new ArrayList<Message>();
		this.channels = new ChatServer.Channels();
	}
	
	public User(String key) {
		this.clientKey = key;
		this.message = new ArrayList<Message>();
		this.channels = new ChatServer.Channels();
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getNick() {
		return nick;
	}
	
	public List<Message> getMessage() {
		List<Message> m = new ArrayList<Message>();
		m.addAll(this.message);
		this.message.clear();
		return m;
	}
	
	public void addMessage(Message m) {
		message.add(m);
	}
	
	public void addMessageToAllChannels(Message m) {
		List<Channel> chList = channels.getListChannels();
		for (Channel c : chList) {
			m.setChannel(c.getName());
			c.addMessage(m);
		}
	}
	
	public boolean isJoinChannel(String channelName) {
		return this.channels.isExist(channelName);
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
	
	public ChatServer.Channels getChannels() {
		return this.channels;
	}
}
