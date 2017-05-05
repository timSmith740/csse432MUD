package client;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	String ServerInfo;
	int Port;
	Socket mySocket;
	OutputStream out;
	InputStream in;
	
	//Where I got my sources
	//http://www.java2s.com/Tutorial/Java/0240__Swing/UseGridBagLayouttolayoutRadioButtons.htm
	
	
	//Create Fields for the GUI
	  JTextField console = new JTextField(30);

	  JRadioButton small = new JRadioButton("Small"), medium = new JRadioButton("Medium"),
	      large = new JRadioButton("Large"), thick = new JRadioButton("Thick"),
	      thin = new JRadioButton("Thin");

	  JCheckBox pepperoni = new JCheckBox("Pepperoni"), mushrooms = new JCheckBox("Mushrooms"),
	      anchovies = new JCheckBox("Anchovies");

	  JButton enterButton = new JButton("Enter");// closeButton = new JButton("Close");
	 
	  JTextArea messageArea = new JTextArea(
			    "You are attacked by a stick. You take damage"	);
	  
	
	  JTextArea inventory = new JTextArea( "Stuff\nstuff\nstuff\nstuff\nstuff\nstuff\nstuff\nstuff\nstuff\nstuff\nmore Stuff"	);
	  JTextArea equiped = new JTextArea( "I got a ring!"	);
	  
	  JLabel playerName = new JLabel("Player Name: Tim Smith");
	  JLabel health = new JLabel("Health: 5/5");
	  JLabel playerLevel = new JLabel("Level: 1");
	  JLabel strength = new JLabel("Strength: 5");
	  JLabel dexterity = new JLabel("Dexterity: 5");
	  JLabel intelligence = new JLabel("Intelligence: 5");
	  JLabel constitution = new JLabel("Constitution: 5");
	  JLabel AC = new JLabel("AC: 5");
	  JLabel armor = new JLabel("Armor: 5");
	  
	  
	  
	//Constructor
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
		
		//On Close, send quit so that the error does not occur
		this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
//                System.out.println("Quiting");
                execute("quit");
            	System.exit(0);
            }
        });
	}
	
	
	//Method that sets up the GUI
	  public void GUI() {
		  //Make this area not editable
		  messageArea.setEditable(false);
		  inventory.setEditable(false);
		  equiped.setEditable(false);
		  
		  //Close on exit
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    JPanel panel1 = new JPanel();
		    panel1.setLayout(new GridBagLayout()); 
		    //Name
		   
		    addItem(panel1, playerName, 1, 0, 1, 1, GridBagConstraints.CENTER);
		    addItem(panel1, playerLevel, 1, 1, 1, 1, GridBagConstraints.CENTER);
		   

		    //Stats
		    Box sizeBox = Box.createVerticalBox();
		    sizeBox.add(health);
		    sizeBox.add(AC);
		    sizeBox.add(armor);
		    sizeBox.add(strength);
		    sizeBox.add(constitution);
		    sizeBox.add(intelligence);
		    sizeBox.add(dexterity);
		    sizeBox.setBorder(BorderFactory.createTitledBorder("Stats"));
		    addItem(panel1, sizeBox, 0, 3, 1, 1, GridBagConstraints.CENTER);
		    
		    
		    //Inventory and Equiped
		    addItem(panel1, new JLabel("Inventory"),1,2,1,1,GridBagConstraints.WEST);
		    JScrollPane sp = new JScrollPane(inventory); 
		    sp.setPreferredSize(new Dimension(200, 100));
		    addItem(panel1, sp,1,3,1,1,GridBagConstraints.WEST);
		   
		    addItem(panel1, new JLabel("Equiped"),2,2,1,1,GridBagConstraints.WEST);
		    JScrollPane sp1 = new JScrollPane(equiped); 
		    sp1.setPreferredSize(new Dimension(200,100));
		    addItem(panel1, sp1,2,3,1,1,GridBagConstraints.WEST);

		    
		    //Enter Button Function
		    enterButton.addActionListener(new ActionListener() { 
		  	  public void actionPerformed(ActionEvent e) { 

		  		  
		  		 //Send message to Server
		  	    String result = execute(console.getText());
		  	    
		  	    //Print result in messageArea
		  	    messageArea.setText(result);
		  	    
		  	    //Example to show I can change parameters
		  	    health.setText("health 10/5");
		  	  } 
		  	} );
		 
		    
		    //Message Area
		    JScrollPane sp2 = new JScrollPane(messageArea); 
		    sp2.setPreferredSize(new Dimension(600,100));
		    addItem(panel1, sp2,0,5,3,1,GridBagConstraints.WEST);
		    
		    
		    //Console and Enter Button
		    addItem(panel1, console, 0, 6, 2, 1, GridBagConstraints.WEST);
		    Box buttonBox = Box.createHorizontalBox();
		    buttonBox.add(enterButton);
		    addItem(panel1, buttonBox, 2, 6, 1, 1, GridBagConstraints.WEST);
		    
		    
		    
		    this.add(panel1);
		    this.pack();
		    this.setVisible(true);
		    //enter key
		    this.getRootPane().setDefaultButton(enterButton);
		    this.setResizable(false); //Make this unable to resize
		  }
	  
//	  public void keyPressed(KeyEvent e){
//		  if(e.getKeyCode()==KeyEvent.VK_ENTER){
//			  System.out.println("Hello");
//		  }
//	  }
	  

	//Method for adding things to GUI
	  private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
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
				out.write(buffer);
				
				//In case of quit
				if(line.equalsIgnoreCase("quit\n")){
					System.out.println("Client Shutting Down");
					try{
						mySocket.close();
					}catch(Exception e){
						e.printStackTrace();
					}
					return "";
				}
				

				byte [] result = ClientProtocol.recieve(in);

				String resultString= new String(result);
				System.out.println(resultString);
				return resultString;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";

	}
	

}
