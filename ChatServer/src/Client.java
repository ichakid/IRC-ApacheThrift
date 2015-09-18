import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import chatservice.MessageService;
import chatservice.Message;

public class Client implements MessageService.Iface{
	protected final TTransport transport;
	protected final String addy;
	protected final int port;
	protected final MessageService.Client client;
	
	public Client(TTransport transport) {
		TSocket tsocket = (TSocket) transport;
		this.transport = transport;
		this.client = new MessageService.Client(new TBinaryProtocol (transport));
		this.addy = tsocket.getSocket().getInetAddress().getHostAddress();
		this.port = tsocket.getSocket().getPort();
	}
	
	@Override
	public void send(Message message) throws TException {
		this.client.send(message);
	}
	
}
