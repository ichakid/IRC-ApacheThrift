import java.util.Scanner;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import chatservice.ChatService;
import chatservice.Message;
import chatservice.MessageService;

public class ChatClient {
	public static boolean exit = false;
	public static String nick = "";
	
	public static void main(String [] args) {
		try {
			TTransport transport;
			transport = new TSocket("localhost", 9090);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			ChatService.Client client = new ChatService.Client(protocol);
			MessageService.Client msgClient = new MessageService.Client(protocol);
			MessageReceiver receiver = new MessageReceiver(protocol);
			new Thread(receiver).start();
			while (!exit){
				perform(client, msgClient);
			}
			transport.close();
		} catch (TException x) {
		x.printStackTrace();
		}
	}
	
	private static void perform(ChatService.Client client, 
		MessageService.Client msgClient) throws TException {
		int ret = 5;
		Scanner input = new Scanner(System.in);
		String cmdString = input.nextLine();
		if (cmdString.startsWith("/")){
			String[] cmd = cmdString.split("\\s+");
			switch (cmd[0]){
				case "/NICK":	
					client.nick(cmd[1]);
					nick = cmd[1];
					break;
				case "/JOIN":	
					client.join(cmd[1], nick); 
					break;
				case "/LEAVE": 	
					client.leave(cmd[1], nick); 
					break;
				case "/EXIT": 	
					client.exit(nick);
					exit = true;
					break;
				default:
					break;
			}
		} else if (cmdString.startsWith("@")){
			String chName = null;
			String msg = null;
			if(cmdString.contains(" ")){
				int idx = cmdString.indexOf(" ");
			    chName = cmdString.substring(1, idx);
			    msg = cmdString.substring(idx+1);
			}
			msgClient.send(new Message(chName, msg, nick));
		} else {
			System.out.println("kkkk");
			msgClient.send(new Message("", cmdString, nick));
		}
	}
}
