import java.util.List;
import java.util.Scanner;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import chatservice.ChatService;
import chatservice.Message;

public class ChatClient {
	public static boolean exit = false;
	public static String clientKey;
	
	public static void main(String [] args) {
		try {
			TTransport transport;
			transport = new TSocket("localhost", 9090);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(
					new TFramedTransport(transport));
			final ChatService.Client client = new
					ChatService.Client(protocol);
			clientKey = client.getKey();
			System.out.println(clientKey);
//			Thread receiver = new Thread(){
//				@Override
//				public void run() {
//					while (!exit) {
//						try {
//							List<Message> messages = client.get(clientKey);
//							for (Message m : messages){
//								System.out.println(m.getMessage());
//							}
//						} catch (TException e) {
//							exit = true;
//							e.printStackTrace();
//						}
//					}
//				}
//			};

			Thread sender = new Thread(){
				@Override
				public void run() {
					while (!exit) {
						try {
							perform(client);
						} catch (TException e) {
							e.printStackTrace();
						}
					}
				}
			};
			sender.start();
//			receiver.start();
			try {
				sender.join();
//				receiver.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				transport.close();
			}
		} catch (TException x) {
			x.printStackTrace();
		}
	}
	
	private static void perform(ChatService.Client client)
		throws TException {
		String ret = "";
		Scanner input = new Scanner(System.in);
		String cmdString = input.nextLine();
		if (cmdString.startsWith("/")){
			String[] cmd = cmdString.split("\\s+");
			switch (cmd[0]){
				case "/NICK":	
					if (cmd.length > 1)
						ret = client.nick(cmd[1], clientKey);
					else
						ret = client.nick("", clientKey);
					break;
				case "/JOIN":	
					if (cmd.length > 1)
						ret = client.join(cmd[1], clientKey); 
					else
						ret = client.join("", clientKey);
					break;
				case "/LEAVE": 	
					ret = client.leave(cmd[1], clientKey); 
					break;
				case "/EXIT": 	
					client.exit(clientKey);
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
			client.send(new Message(chName, msg, clientKey));
		} else {
			client.send(new Message("", cmdString, clientKey));
		}
	}
}
