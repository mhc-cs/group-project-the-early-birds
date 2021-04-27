package guesswho;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

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
	static String outbuf;
	static String inbuf;
	//Fix types later
	static ArrayList<JsonObject> msgs;
	static byte[] buf;
	static OutputStream outputStream;
	static DataOutputStream dataOutputStream;
	
	
	
	/*
	 * Network Constructor
	 */
	public Network() {
//		this.SERVER_ADDRESS = new InetAddress();
		outbuf = "";
		inbuf = "";
		msgs = new ArrayList<JsonObject>();
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
			 // get the output stream from the socket.
	        OutputStream outputStream = sock.getOutputStream();
	        // create a data output stream from the output stream so we can send data through it
	       dataOutputStream = new DataOutputStream(outputStream);
			
			System.out.println("Connected");
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
		if (sock != null) {
			return;
		}
		try {
			System.out.println("Connection closed.");
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends data from the outbuffer
	 * and inputs data from the server to inbuf,
	 * then decodes inbuf to messages
	 */
	public static ArrayList<JsonObject> do_communication() {
		//needs to send data from out buf to server
//		sock.send("ehll");
		// needs to decode inbuf to messages
		
		//only need this is not all of each message is sent in each
//		ArrayList<JsonObject> oldMsgs = msgs;
//		msgs = null;
		
		

		
		//send message to server
		if (!outbuf.isEmpty()) {
			try {
				sock.setSoTimeout(1);
				 //remember how much is sent, then make the outbuffer the remainder
		        dataOutputStream.writeBytes(outbuf);
		        dataOutputStream.flush();
			} 
			catch (SocketException e) {
				System.out.print(e);
				
			} catch (IOException e) {
				System.out.print(e);
			}
			
			
		}
		
		
		//recieve message from server
		try {
			// get the input stream from the connected socket
	        InputStream inputStream = sock.getInputStream();
	        // create a DataInputStream so we can read data from it.
	        DataInputStream dataInputStream = new DataInputStream(inputStream);
			inbuf = dataInputStream.readUTF();
			System.out.print("Inbuf: " + inbuf);
		}
		catch (Exception e) {
			
		}
		
		if (sock == null) {
			return msgs;
		}
		
		return new ArrayList<JsonObject>();
		
		
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
