namespace java chatservice


struct Message {
	1:string channel,
	2:string message,
	3:string clientKey,
}

service ChatService {
	string getKey(),
	string nick(1:string nickname, 2:string clientKey),
	string join(1:string channel, 2:string clientKey),
	string leave(1:string channel, 2:string clientKey),
	string exit(1:string clientKey),
	Message get(1:string clientKey),
	string send(1:Message message),
}