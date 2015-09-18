import java.util.ArrayList;

import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import chatservice.ChatService;

public class ChatServer {
	public static ArrayList<Channel> channels;
	public static ArrayList<String> channelNames;
	public static ArrayList<String> users;
	public static ChatHandler handler;
	public static ChatService.Processor processor;
	public static String message = null;
	
	public static void main(String [] args) {
		try {
			channels = new ArrayList();
			channelNames = new ArrayList();
			users = new ArrayList();
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
	
	public static class Channel {
		ArrayList<String> members;
		
		public Channel() {
			members = new ArrayList();
		}

		public Channel(String u) {
			members = new ArrayList();
			members.add(u);
		}
		
		public void addMember(String u) {
			if (!members.contains(u)) {
				members.add(u);
			}
		}
		
		public void deleteMember(String u) {
			members.remove(u);
		}
	}
}
