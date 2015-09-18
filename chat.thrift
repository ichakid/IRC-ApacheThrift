namespace java chatservice

typedef i32 int
service ChatService
{
	int nick(1:string nickname),
	int join(1:string channelname),
	int leave(1:string channel),
	int exit(),
	int message(1:string channelname, 2:string message),
}