import org.apache.thrift.TException;

import chatservice.ChatService;

public class ChatHandler implements ChatService.Iface{

	@Override
	public int nick(String nickname) throws TException {
		// TODO Auto-generated method stub
		System.out.println("/NICK " + nickname);
		return 0;
	}

	@Override
	public int join(String channelname) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int leave(String channel) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int exit() throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int message(String channelname, String message) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

}
