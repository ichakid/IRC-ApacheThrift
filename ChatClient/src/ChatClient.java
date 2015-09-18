import java.util.Scanner;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import chatservice.ChatService;

public class ChatClient {
	static boolean exit = false;
	
	public static void main(String [] args) {
		try {
			TTransport transport;
			transport = new TSocket("localhost", 9090);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(new TFramedTransport(transport));
			ChatService.Client client = new
			ChatService.Client(protocol);
			while (!exit){
				perform(client);
			}
			transport.close();
		} catch (TException x) {
		x.printStackTrace();
		}
	}
	
	private static void perform(ChatService.Client client)
		throws TException {
		int ret = 5;
		Scanner input = new Scanner(System.in);
		String cmdString = input.nextLine();
		if (cmdString.startsWith("/")){
			String[] cmd = cmdString.split("\\s+");
			switch (cmd[0]){
				case "/NICK":	ret = client.nick(cmd[1]); 
								break;
				case "/JOIN":	ret = client.join(cmd[1]); 
								break;
				case "/LEAVE": 	ret = client.leave(cmd[1]); 
								break;
				case "/EXIT": 	ret = client.exit();
								exit = true;
								break;
				default: 		break;
			}
		}
		System.out.println(ret);
		if (cmdString.startsWith("@")){
//			client.message(channelname, message);
		}
		client.message("", cmdString);
	}
}
