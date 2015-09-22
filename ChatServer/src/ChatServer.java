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
			TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(8000);
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
		
		public void removeChannel(Channel channel) {
			channels.remove(channel);
		}
		
		//Check whether channel with certain name is exist
		public boolean isExist(String channelName) {
			boolean ret = false;
			int i = 0;
			while (!ret && i < channels.size()) {
				if (channels.get(i).getName().equals(channelName))
					ret = true;
				i++;
			}
			return ret;
		}
		
		//Get channel instance by channelName
		public Channel getChannel(String channelName) {
			Channel chInstance = new Channel();
			boolean ret = false;
			int i = 0;
			while (!ret && i < channels.size()) {
				if (channels.get(i).getName().equals(channelName)) {
					ret = true;
					chInstance = channels.get(i);
				}
				i++;
			}
			return chInstance;
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
		
		public List<Channel> getListChannels() {
			return this.channels;
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
			User usr = new User();
			boolean ret = false;
			int i = 0;
			while (!ret && i < users.size()) {
				if (users.get(i).getClientKey().equals(clientKey)) {
					ret = true;
					usr = users.get(i);
				}
				i++;
			}
			return usr;
		}
		
		public List<User> getListUsers() {
			return this.users;
		}
	}
}
