package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utility.Protocol;


/*
 * Joseph Militello
 * Logan Erexson
 * 3/31/17
 * Main class for client
 */

public class Client {
	String ServerInfo;
	int Port;
	Socket mySocket;
	OutputStream out;
	InputStream in;
	
	public Client(String Sever,int Port){
		this.ServerInfo= Sever;
		this.Port=Port;
		
		try{
			mySocket = new Socket(this.ServerInfo,Port);
			in = mySocket.getInputStream();
			out =mySocket.getOutputStream();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void execute(){
		System.out.println("Connected to Socket: "+ mySocket.getLocalPort());
		System.out.println("Type in command (\"quit\" to terminate):");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		while(true){
			try {
				System.out.print("> ");
				line = input.readLine();
				String[] subparts=line.split(" ");
				line = line + "\n";
				byte[] buffer = line.getBytes("UTF-8");
				
				
			
				out.write(buffer);
				if(line.equalsIgnoreCase("quit\n")){
					System.out.println("Client Shutting Down");
					try{
						mySocket.close();
					}catch(Exception e){
						e.printStackTrace();
					}
					return;
				}
				

				byte [] result = Protocol.recieve(in);

				String resultString= new String(result);
				System.out.println(resultString);


			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	}
	

}
