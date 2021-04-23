package guesswho;
import java.net.*;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
//import java.net.InetAddress;

/*
 * Provides support methods to connect and communicate with the 
 * game server
 */
public class Network {
//	static InetAddress SERVER_ADDRESS;
	static Socket sock;
	static ByteArrayOutputStream outbuf;
	static ByteArrayInputStream inbuf;
	//Fix types later
	static HashMap<String,String> msgs;
	static byte[] buf;
	
	
	/*
	 * Network Constructor
	 */
	public Network() {
//		this.SERVER_ADDRESS = new InetAddress();
		outbuf = new ByteArrayOutputStream();
		inbuf = new ByteArrayInputStream(buf);
		msgs = new HashMap<String,String>();
	}
	
	/**
	 * Adds data to be sent to the out buffer
	 */
	public static void send(HashMap<String, String> data) {
		
	}
	
	/*
	 * Connects to the game server
	 */
	public static void connnect() {
		close();
		//add ability to connect to a different game server?
		try {
			sock = new Socket("127.0.0.1", 9876);
			//add a message of type CONNECT
		} 
		catch (IOException e) {
			add_hist("Couldn't connect.");
			close();
		}
		
	}
	
	/*
	 * Closes the connection with the server
	 */
	public static void close() {
		
	}
	
	/**
	 * Sends data from the outbuffer
	 * and handles confirmation messages from the 
	 * server when data is recieved
	 */
	public static void do_communication() {
		
	}
	
	/*
	 * Handles text from the chat, 
	 * we may not actually need this
	 */
	public static void send_chat() {
		
	}
	
	/**
	 * Adds messages to the chat log/history
	 * @param msg
	 */
	public static void add_hist(String msg) {
		
	}

}
