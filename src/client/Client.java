package client;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//import com.oracle.webservices.internal.api.EnvelopeStyle.Style;
//import com.sun.glass.events.WindowEvent;


/*
 * Joseph Militello
 * Logan Erexson
 * Smith Tim
 * 5/2/17
 * Main class for client
 */

public class Client extends JFrame{

	private static final long serialVersionUID = 1L;
	private String serverInfo;
	private int port;
	private Socket mySocket;
	private OutputStream out;
	private InputStream in;
	ChatHandler chat;
	
	//Where I got my sources
	//http://www.java2s.com/Tutorial/Java/0240__Swing/UseGridBagLayouttolayoutRadioButtons.htm
	
	
	//Create Fields for the GUI
	  JTextField console = new JTextField(30);


	  JButton enterButton = new JButton("Enter");
	  JTextArea messageArea = new JTextArea();
	  
	
	  JTextArea inventory = new JTextArea( ""	);
	  JTextArea equiped = new JTextArea( ""	);
	  
	  JLabel playerName = new JLabel("Player Name: ---");
	  JLabel health = new JLabel("Health: -/-");
	  JLabel strength = new JLabel("Strength: -");
	  JLabel dexterity = new JLabel("Dexterity: -");
	  JLabel intelligence = new JLabel("Intelligence: -");
	  JLabel constitution = new JLabel("Constitution: -");
	  JLabel armor = new JLabel("Armor: -");
	  
	//Constructor
	public Client(String server,int port, int chatPort){
		this.serverInfo= server;
		this.port=port;
		
		try{
			this.mySocket = new Socket(this.serverInfo,port);
			this.in = this.mySocket.getInputStream();
			this.out =this.mySocket.getOutputStream();
			Socket chatSock= new Socket(this.serverInfo, chatPort);
			this.chat = new ChatHandler(chatSock, this.messageArea);
			Thread chatThread = new Thread(this.chat);
			chatThread.start();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//On Close, send quit so that the error does not occur
		this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
			public void windowClosing(java.awt.event.WindowEvent e) {
            	Client.this.chat.setRunning(false);
                execute("quit");
            	System.exit(0);
            }
        });
	}
	
	
	//Method that sets up the GUI
	  public void GUI() {
		  //Make this area not editable
		  this.messageArea.setEditable(false);
		  this.inventory.setEditable(false);
		  this.equiped.setEditable(false);
		  
		  //Close on exit
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    JPanel panel1 = new JPanel();
		    panel1.setLayout(new GridBagLayout()); 
		    //Name
		   
		    addItem(panel1, this.playerName, 1, 0, 1, 1, GridBagConstraints.CENTER);
		   // addItem(panel1, playerLevel, 1, 1, 1, 1, GridBagConstraints.CENTER);
		   

		    //Stats
		    Box sizeBox = Box.createVerticalBox();
		    sizeBox.add(this.health);
//		    sizeBox.add(AC);
		    sizeBox.add(this.armor);
		    sizeBox.add(this.strength);
		    sizeBox.add(this.constitution);
		    sizeBox.add(this.intelligence);
		    sizeBox.add(this.dexterity);
		    sizeBox.setBorder(BorderFactory.createTitledBorder("Stats"));
		    addItem(panel1, sizeBox, 0, 3, 1, 1, GridBagConstraints.CENTER);
		    
		    
		    //Inventory and Equiped
		    addItem(panel1, new JLabel("Inventory"),1,2,1,1,GridBagConstraints.WEST);
		    JScrollPane sp = new JScrollPane(this.inventory); 
		    sp.setPreferredSize(new Dimension(200, 100));
		    addItem(panel1, sp,1,3,1,1,GridBagConstraints.WEST);
		   
		    addItem(panel1, new JLabel("Equipped"),2,2,1,1,GridBagConstraints.WEST);
		    JScrollPane sp1 = new JScrollPane(this.equiped); 
		    sp1.setPreferredSize(new Dimension(200,100));
		    addItem(panel1, sp1,2,3,1,1,GridBagConstraints.WEST);

		    
		    //Enter Button Function
		    this.enterButton.addActionListener(new ActionListener() { 
		  	  @Override
			public void actionPerformed(ActionEvent e) { 

		  		  
		  		 //Send message to Server
		  	    String result = execute(Client.this.console.getText());
		  	    Client.this.console.setText("");
		  	    //Print result in messageArea
		  	    if(result.charAt(0)=='&'){
		  	    	if(result.charAt(1)=='S'){
		  	    		//Update characters stats
		  	    		String[] data = result.split("@");
		  	    		String[] stats = data[0].split(":");
		  	    		//UpdateStats
		  	    		Client.this.messageArea.append(data[3]+"\n");
		  	    		Client.this.playerName.setText("Player Name: "+stats[1]);
		  	    		Client.this.health.setText("Health: "+stats[2]+"/"+stats[3]);
//		  	    		AC.setText("AC: "+stats[4]);
		  	    		Client.this.armor.setText("Armor: "+stats[4]);
		  	    		Client.this.strength.setText("Strength: "+stats[5]);
		  	    		Client.this.constitution.setText("Constitution: "+stats[6]);
		  	    		Client.this.intelligence.setText("Intelligence: "+stats[7]);
		  	    		Client.this.dexterity.setText("Dexterity: "+stats[8]);
		  	    		
		  	    		//Update Inventory
		  	    		Client.this.inventory.setText("");
		  	    		Client.this.inventory.append(data[1]);
		  	    		
		  	    		//Upate Equipped 
		  	    		Client.this.equiped.setText("");
		  	    		Client.this.equiped.append(data[2]);
		  	
		  	    	}else if(result.charAt(1)=='H'){
		  	    		String[] data = result.split("@");
		  	    		String[] stats = data[0].split(":");
		  	    		Client.this.messageArea.append(data[1]);
		  	    		Client.this.health.setText("Health: "+stats[1]+"/"+stats[2]);
		  	    		
		  	    	}else{
		  	    		
		  	    		Client.this.messageArea.setText("ERROR");
		  	    	}
		  	    }else{
		  	    	Client.this.messageArea.append(result+"\n");
		  	    }
		  	    
		  	    
		  	    //Example to show I can change parameters
		  	   // health.setText("health 10/5");
		  	  } 
		  	} );
		 
		    
		    //Message Area
		    JScrollPane sp2 = new JScrollPane(this.messageArea); 
		    sp2.setPreferredSize(new Dimension(600,100));
		    addItem(panel1, sp2,0,5,3,1,GridBagConstraints.WEST);
		    
		    
		    //Console and Enter Button
		    addItem(panel1, this.console, 0, 6, 2, 1, GridBagConstraints.WEST);
		    Box buttonBox = Box.createHorizontalBox();
		    buttonBox.add(this.enterButton);
		    addItem(panel1, buttonBox, 2, 6, 1, 1, GridBagConstraints.WEST);
		    
		    
		    
		    this.add(panel1);
		    this.pack();
		    this.setVisible(true);
		    //enter key
		    this.getRootPane().setDefaultButton(this.enterButton);
		    this.setResizable(false); //Make this unable to resize
		  }
	  

	  

	//Method for adding things to GUI
	  private static void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
		    GridBagConstraints gc = new GridBagConstraints();
		    gc.gridx = x;
		    gc.gridy = y;
		    gc.gridwidth = width;
		    gc.gridheight = height;
		    gc.weightx = 500.0;
		    gc.weighty = 500.0;
		    gc.insets = new Insets(5, 5, 5, 5);
		    gc.anchor = align;
		    gc.fill = GridBagConstraints.NONE;
		    p.add(c, gc);
		    
		  }
	
	//Send message to Server
	//Instead of getting message from keyboard, get it from the GUI
	public String execute(String line){
			try {
				String[] subparts=line.split(" ");
				line = line + "\n";
				byte[] buffer = line.getBytes("UTF-8");
				this.out.write(buffer);
				
				//In case of quit
				if(line.equalsIgnoreCase("quit\n")){
					System.out.println("Client Shutting Down");
					try{
						this.mySocket.close();
					}catch(Exception e){
						e.printStackTrace();
					}
					return "";
				}
				

				byte [] result = ClientProtocol.recieve(this.in);

				String resultString= new String(result);
				System.out.println(resultString);
				return resultString;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";

	}
	

}
