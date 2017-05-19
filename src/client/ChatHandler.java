package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JTextArea;

public class ChatHandler implements Runnable {
	
	private Socket socket;
	private InputStream in;
	private boolean running = false;
	private JTextArea messageArea;
	public ChatHandler(Socket socket, JTextArea messageArea) throws IOException{
		this.socket = socket;
		this.in = socket.getInputStream();
		this.messageArea = messageArea;
	}
	
	public void setRunning(boolean isRunning){
		this.running = isRunning;
	}

	@Override
	public void run() {
		System.out.println("Chat starting");
		this.running = true;
		while(this.running){
			try{
				byte[] messageBytes = ClientProtocol.recieve(this.in);
				String message = new String(messageBytes);
				if(message.equals("quit")){
					this.running=false;
				}
				System.out.println(message);
				this.messageArea.append(message+"\n");
				
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		try {
			this.socket.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

}
