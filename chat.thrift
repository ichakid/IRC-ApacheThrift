namespace java chatservice

struct Message {
	1:string channel, 2:string message, 3:string nick
}

service ChatService
{
	oneway void nick(1:string nickname),
	oneway void join(1:string channelname, 2:string nick),
	oneway void leave(1:string channel, 2:string nick),
	oneway void exit(1:string nick),
}

service MessageService
{	
	oneway void send(1:Message message),
}