package client;

/*
 * Joseph Militello
 * Logan Erexson
 * Tim Smith
 * Application File for Client
 */

public class ClientApp {
	

	public static void main(String[] args) {
		String server = ClientProtocol.SERVER_INFO;
		int port =ClientProtocol.DEFAULT_PORT;
		int chatPort=ClientProtocol.CHAT_PORT;
		if(args.length==3){
			server = args[0];
			port= Integer.parseInt(args[1]);
			chatPort=Integer.parseInt(args[2]);
		}
		Client myClient = new Client(server,port, chatPort);

		myClient.GUI();

	}

}
