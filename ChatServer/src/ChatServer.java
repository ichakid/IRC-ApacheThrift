import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import chatservice.ChatService;
import chatservice.Message;

public class ChatServer {
	public static Channels channels;
	public static Users users;
	public static ChatHandler handler;
	public static ChatService.Processor processor;
	
	public static void main(String [] args) {
		try {
			channels = new Channels();
			users = new Users();
			handler = new ChatHandler();
			processor = new ChatService.Processor(handler);
			TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(9090);
			THsHaServer.Args arg = new THsHaServer.Args(serverTransport);
			arg.processor(processor);
			arg.transportFactory(new TFramedTransport.Factory());
			TServer server = new THsHaServer(arg);
			System.out.println("Starting the multi client server...");
			server.serve();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
	public static class Channels {
		private List<Channel> channels;
		
		public Channels() {
			channels = new ArrayList<Channel>();
		}
		
		public void addChannel(Channel channel) {
			channels.add(channel);
		}
		
		//Check whether channel with certain name is exist
		public boolean isExist(String channelName) {
			return false;
		}
		
		//Get channel instance by channelName
		public Channel getChannel(String channelName) {
			return new Channel();
		}
		
		public void removeUser(User user) {
			for (Channel c : channels) {
				c.removeMember(user);
			}
		}
		
		public void addMessage(Message m) {
			for (Channel c : channels) {
				c.addMessage(m);
			}
		}
	}
	
	public static class Users {
		private List<User> users;
		
		public Users() {
			users = new ArrayList<User>();
		}
		
		public void addUser(User user) {
			users.add(user);
		}
		
		public void removeUser(User user) {
			users.remove(user);
		}
		
		//Get user instance by clientKey
		public User getUser(String clientKey) {
			return new User(clientKey);
		}
	}
}
