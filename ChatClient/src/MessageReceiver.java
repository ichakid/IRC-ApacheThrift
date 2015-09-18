import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

import chatservice.Message;
import chatservice.MessageService;

public class MessageReceiver implements Runnable{
	private final MessageService.Processor processor;
	private final TProtocol protocol;
	
	public MessageReceiver(TProtocol protocol) {
		this.protocol = protocol;
		MessageService.Iface handler = new MessageService.Iface() {	
			@Override
			public void send(Message message) throws TException {
		        System.out.println("["+ message.getChannel() +"] " + 
		        		message.getNick() + ": " + message.getMessage());
			}
	    };
		this.processor = new MessageService.Processor(handler);
	}

	@Override
	public void run() {
		while(true) {
			try {
				while (processor.process(protocol, protocol) == true) { }
			} catch (TException e) {
				e.printStackTrace();
			}
		}
	}

}
