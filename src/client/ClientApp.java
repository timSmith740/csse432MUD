package client;

/*
 * Joseph Militello
 * Logan Erexson
 * Tim Smith
 * Application File for Client
 */

public class ClientApp {
	

	public static void main(String[] args) {
		
		Client myClient = new Client(ClientProtocol.SERVER_INFO,ClientProtocol.DEFAULT_PORT);
		
	
		//myClient.execute();
		myClient.GUI();

	}

}
