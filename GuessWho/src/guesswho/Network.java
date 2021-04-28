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
import com.google.gson.stream.JsonReader;

import Messages.Message;
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
	static InputStream inputStream;
	
	
	
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
	     //get the input stream from the connected socket
	        inputStream = sock.getInputStream();
			
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
	public static void send(Message data) {
		Gson gson = new Gson();
		outbuf += gson.toJson(data);
	}
	
	/*
	 * Closes the connection with the server
	 */
	public static void close() {
		if (sock == null) {
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
	public ArrayList<JsonObject> do_communication() {		
		//only need this is not all of each message is sent in each?
//		ArrayList<JsonObject> oldMsgs = msgs;
		msgs = null;
		System.out.println("Do Communication is running");
		if (sock == null) {
			return msgs;
		}

		//send message to server
		if (!outbuf.isEmpty()) {
			System.out.println("Trying to send to server");
			try {
				sock.setSoTimeout(1);
				 //remember how much is sent, then make the outbuffer the remainder
		        dataOutputStream.writeBytes(outbuf);
		        dataOutputStream.flush();
		        System.out.println("Sent to server");
			} 
			catch (SocketException e) {
				System.out.print(e);
				
			} catch (IOException e) {
				System.out.print(e);
			}
			
			
		}
		//recieve message from server
		try {
			sock.setSoTimeout(1);
			System.out.println("Trying to receive from server");
	        // create a DataInputStream so we can read data from it.
	        DataInputStream dataInputStream = new DataInputStream(inputStream);
			inbuf = dataInputStream.readUTF();
//			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
//			//need to add processing for string to become JSONObjects
//			try (JsonReader jsonReader = new JsonReader(streamReader)) {
//	            jsonReader.beginObject();
//	            while (jsonReader.hasNext()) {
//	            	
////	                String name = jsonReader.nextName();
////	                if (name.equals("filter_level")) {
////	                    System.out.println(jsonReader.nextString());
////	                } else if (name.equals("text")) {
////	                    System.out.println("text: " + jsonReader.nextString());
////	                } else {
////	                    jsonReader.skipValue();
////	                }
//	            }
//	            jsonReader.endObject();
//	            jsonReader.close();
//	        }
			
			System.out.print("Inbuf: " + inbuf);
			System.out.println("Received from server");
		}
		catch (Exception e) {
			System.out.println("Tried to receive from server");
		}
		
		
		return new ArrayList<JsonObject>();
		
		
	}
	

}
