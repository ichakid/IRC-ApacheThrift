import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import org.apache.thrift.TException;

import chatservice.ChatService;
import chatservice.Message;

public class ChatHandler implements ChatService.Iface{

	@Override
	public String getKey() throws TException {
		//generate key for client
		SecureRandom random = new SecureRandom();
		String key = new BigInteger(35, random).toString(32);
		User user = new User(key);
		
		int temp = ChatServer.users.getListUsers().size();
		user.setNick("user" + String.valueOf(temp));
		ChatServer.users.addUser(user);
		return key;
	}

	@Override
	public String nick(String nickname, String clientKey) throws TException {
		User user = ChatServer.users.getUser(clientKey);
		if (!nickname.isEmpty()) {
			user.setNick(nickname);
		}
		return user.getNick();
	}

	@Override
	public String join(String channel, String clientKey) throws TException {
		User user = ChatServer.users.getUser(clientKey);
		Channel c;
		if (channel.isEmpty()) {
			int temp = ChatServer.channels.getListChannels().size();
			c = new Channel("channel" + String.valueOf(temp));
			ChatServer.channels.addChannel(c);
		} else if (!ChatServer.channels.isExist(channel)) {
				c = new Channel(channel);
				ChatServer.channels.addChannel(c);
		} else {
			c = ChatServer.channels.getChannel(channel);
		}
		c.addMember(user);
		user.addChannel(c);
		return c.getName();
	}

	@Override
	public String leave(String channel, String clientKey) throws TException {
		User user = ChatServer.users.getUser(clientKey);
		Channel c;
		if (channel.isEmpty()) {
			return "channel name is empty";
		} else if (!ChatServer.channels.isExist(channel)) {
			return "channel not found";
		}
		
		c = ChatServer.channels.getChannel(channel);
		if (!user.isJoinChannel(c.getName())){
			return "You're not registered as member of " + c.getName();
		}
		c.removeMember(user);
		user.removeChannel(c);
		return ("leaving channel " + c.getName());
	}

	@Override
	public String exit(String clientKey) throws TException {
		User user = ChatServer.users.getUser(clientKey);
		ChatServer.channels.removeUser(user);
		ChatServer.users.removeUser(user);
		return "exit";
	}

	@Override
	public List<Message> get(String clientKey) throws TException {
		User user = ChatServer.users.getUser(clientKey);
		List<Message> m = user.getMessage();
		return m;
	}

	@Override
	public String send(Message message) throws TException {
		User user = ChatServer.users.getUser(message.getClientKey());
		if (message.getChannel().isEmpty()) {	//send to all user's channels
			message.setClientKey(user.getNick());
			user.addMessageToAllChannels(message);
		} else {
			Channel c = ChatServer.channels.getChannel(
					message.getChannel());
			message.setClientKey(user.getNick());
			c.addMessage(message);
		}
		return "delivered";
	}

}
