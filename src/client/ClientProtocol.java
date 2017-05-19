package client;

import java.io.IOException;
import java.io.InputStream;


/*
 * Joseph Militello
 * Logan Erexson
 * Tim Smith
 * Static Methods across all client files
 */
public class ClientProtocol {


	public static int DEFAULT_PORT = 5555;
	public static int CHAT_PORT = 5556;
	public static String SERVER_INFO = "localhost";


	public static final String INVALID_SYNTAX="Incorrect Syntax";
	
	//To recieve a file
	public static byte[] recieve(InputStream in) throws IOException{
		int nRead=-1;
		byte [] result = new byte[0];
		byte [] tempBuff = new byte[1024];
		int y = 0;
		do{
			y = (nRead = in.read(tempBuff,0, tempBuff.length));
			byte [] newBuff = new byte[result.length+nRead];
			System.arraycopy(result, 0, newBuff, 0, result.length);
			System.arraycopy(tempBuff, 0, newBuff, result.length, nRead);
			result = newBuff;
		}while(y>=1024);
		return result;
	}
	
	//parse commands
	public static String CommandHandler(String command){
		
		String[] subparts=command.split(" ");
		switch(subparts[0].toLowerCase()){
		case "quit":
			return "quit";
					
		case "attack":
			if(subparts.length==2){
			String sendback = "attack:"+subparts[1];
			return(sendback);
			}
			return(ClientProtocol.INVALID_SYNTAX);
			
		case "run":
			if(subparts.length==2){
			String sendback = "run:"+subparts[1];
			return(sendback);
			}
			return(ClientProtocol.INVALID_SYNTAX);
			
		default:
			return(ClientProtocol.INVALID_SYNTAX);
		}
		
	}
	
	

}
