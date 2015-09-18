import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;

import chatservice.ChatService;

public class ChatServer {
	public static List<Channel> channels;
	public static List<String> channelNames;
	public static List<String> users;
	public static ChatHandler handler;
	public static ChatService.Processor processor;
	public static String message = null;
	
	public static void main(String [] args) {
		try {
			channels = new ArrayList<Channel>();
			channelNames = new ArrayList<String>();
			users = new ArrayList<String>();
			
		    final ChatHandler chatHandler = new ChatHandler();
		    new Thread(chatHandler).start();
		    
		    TProcessorFactory processorFactory = new TProcessorFactory(null) {
		      @Override
		      public TProcessor getProcessor(TTransport trans) {
		        chatHandler.addClient(new Client(trans));
		        return new ChatService.Processor(chatHandler);
		      }
		    };
			
		    TServerTransport serverTransport = new TServerSocket(9090);
		    TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverTransport);
		    serverArgs.processorFactory(processorFactory);
		    TServer server = new TThreadPoolServer(serverArgs);
			System.out.println("Starting the multi client server...");
		    server.serve();
		    
//			TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(9090);
//			THsHaServer.Args arg = new THsHaServer.Args(serverTransport);
//			arg.processorFactory(processorFactory);
//			arg.transportFactory(new TFramedTransport.Factory());
//			TServer server = new THsHaServer(arg);
//			System.out.println("Starting the multi client server...");
//			server.serve();
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
