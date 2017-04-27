package client;

import utility.Protocol;

/*
 * Joseph Militello
 * Logan Erexson
 * Tim Smith
 * Application File for Client
 */

public class ClientApp {
	

	public static void main(String[] args) {
		
		Client myClient = new Client(Protocol.SERVER_INFO,Protocol.DEFAULT_PORT);
		
		myClient.execute();


	}

}
