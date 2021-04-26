package guesswho;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
	static ArrayList<HashMap<String,String>> msgs;
	static byte[] buf;
	
	
	/*
	 * Network Constructor
	 */
	public Network() {
//		this.SERVER_ADDRESS = new InetAddress();
		outbuf = new ByteArrayOutputStream();
		inbuf = new ByteArrayInputStream(buf);
		msgs = new ArrayList<HashMap<String,String>>();
	}
	
	
	
	/*
	 * Connects to the game server
	 */
	public static void connect() {
		close();
		//add ability to connect to a different game server?
		   System.out.println("Attempting to connect...");
		try {
			sock = new Socket("127.0.0.1", 9876);
			//add a message of type CONNECT
		} 
		catch (IOException e) {
			System.out.println("Couldn't connect.");
			close();
		}
		
	}
	
	
	/**
	 * Adds data to be sent to the out buffer
	 */
	public static void send(HashMap<String, String> data) {
//		ByteArrayOutputStream encodedData = 
				
//		outbuf += encodedData;
	}
	
	/*
	 * Closes the connection with the server
	 */
	public static void close() {
		
	}
	
	/**
	 * Sends data from the outbuffer
	 * and inputs data from the server to inbuf,
	 * then decodes inbuf to messages
	 */
	public static ArrayList<HashMap<String,String>> do_communication() {
		//needs to send data from out buf to server
//		sock.send("ehll");
		// needs to decode inbuf to messages
		if (sock == null) {
			return new ArrayList<HashMap<String,String>>();
		}
		
		return new ArrayList<HashMap<String,String>>();
		
		
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
