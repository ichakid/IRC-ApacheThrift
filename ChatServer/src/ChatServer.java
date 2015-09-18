import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import chatservice.ChatService;

public class ChatServer {
	public static ChatHandler handler;
	public static ChatService.Processor processor;
	public static void main(String [] args) {
		try {
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
}
