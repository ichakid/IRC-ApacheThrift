namespace java chatservice

typedef i32 int
service ChatService
{
	int nick(1:string nickname),
	int join(1:string channelname, 2:string nick),
	int leave(1:string channel, 2:string nick),
	int exit(1:string nick),
	int message(1:string channelname, 2:string message, 3:string nick),
}