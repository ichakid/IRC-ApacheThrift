import org.apache.thrift.TException;

import chatservice.ChatService;

public class ChatHandler implements ChatService.Iface{
	
	@Override
	public int nick(String nickname) throws TException {
		System.out.println("/NICK " + nickname);
		if (nickname.isEmpty()){
			nickname = "nickname";
		}
		if (!ChatServer.users.contains(nickname)){
			ChatServer.users.add(nickname);
			System.out.println("Users: " + ChatServer.users.toString());
			return 1;
		}
		return 	-1;
	}

	@Override
	public int join(String channelname, String nick) throws TException {
		System.out.println("/JOIN " + channelname);
		if (channelname.isEmpty()){
			channelname = "channelname";
		}
		if (!ChatServer.channelNames.contains(channelname)){
			ChatServer.channelNames.add(channelname);
			ChatServer.Channel channel = new ChatServer.Channel(nick);
			System.out.println("Channels: " + ChatServer.channelNames.toString());
			ChatServer.channels.add(channel);
			return 1;
		}
		int id = ChatServer.channelNames.indexOf(channelname);
		ChatServer.channels.get(id).addMember(nick);
		return 	1;
	}

	@Override
	public int leave(String channel, String nick) throws TException {
		System.out.println("/LEAVE " + channel);
		if (channel.isEmpty()){
			return 0;
		}
		if (!ChatServer.channelNames.contains(channel)){
			return 0;
		}
		int id = ChatServer.channelNames.indexOf(channel);
		ChatServer.channels.get(id).deleteMember(nick);
		System.out.println("Channel " + ChatServer.channelNames.get(id) + 
				": " + ChatServer.channels.get(id).members.toString());
		return 	1;
	}

	@Override
	public int exit(String nick) throws TException {
		ChatServer.users.remove(nick);
		return 0;
	}

	@Override
	public int message(String channelname, String message, String nick) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

}
