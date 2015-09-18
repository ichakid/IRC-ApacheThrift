import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.thrift.TException;

import chatservice.ChatService;
import chatservice.MessageService;
import chatservice.Message;

public class ChatHandler implements ChatService.Iface, 
	MessageService.Iface, Runnable{
	private final BlockingQueue<Message> messageQueue;
	private final List<Client> clients;
	
	public ChatHandler() {
	    this.messageQueue = new LinkedBlockingQueue<Message>();
	    this.clients = new ArrayList<Client>();
	}
	  
	public void addClient(Client client) {
	    clients.add(client);
	}
	
	@Override
	public void nick(String nickname) throws TException {
		System.out.println("/NICK " + nickname);
		if (nickname.isEmpty()){
			nickname = "nickname";
		}
		if (!ChatServer.users.contains(nickname)){
			ChatServer.users.add(nickname);
			System.out.println("Users: " + ChatServer.users.toString());
		}
	}

	@Override
	public void join(String channelname, String nick) throws TException {
		System.out.println("/JOIN " + channelname);
		if (channelname.isEmpty()){
			channelname = "channelname";
		}
		if (!ChatServer.channelNames.contains(channelname)){
			ChatServer.channelNames.add(channelname);
			ChatServer.Channel channel = new ChatServer.Channel(nick);
			System.out.println("Channels: " + ChatServer.channelNames.toString());
			ChatServer.channels.add(channel);
		}
		int id = ChatServer.channelNames.indexOf(channelname);
		ChatServer.channels.get(id).addMember(nick);
		System.out.println("Users: " + ChatServer.channels.get(id).members.toString());
	}

	@Override
	public void leave(String channel, String nick) throws TException {
		System.out.println("/LEAVE " + channel);
		if (!channel.isEmpty() && !ChatServer.channelNames.contains(channel)){
			int id = ChatServer.channelNames.indexOf(channel);
			ChatServer.channels.get(id).deleteMember(nick);
			System.out.println("Channel " + ChatServer.channelNames.get(id) + 
					": " + ChatServer.channels.get(id).members.toString());
		}
	}

	@Override
	public void exit(String nick) throws TException {
		ChatServer.users.remove(nick);
		System.out.println(ChatServer.users.toString());
	}

	@Override
	public void send(Message message) throws TException {
		System.out.println(message.getNick() + ": " + message.getMessage());
		messageQueue.add(message);
	}

	@Override
	public void run() {
		while(true) {
			try {
				Message msg = messageQueue.take();
				Iterator<Client> clientItr = clients.iterator();
				while (clientItr.hasNext()) {
					Client client = clientItr.next();
					client.send(msg);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
			}
		}
	}

}
