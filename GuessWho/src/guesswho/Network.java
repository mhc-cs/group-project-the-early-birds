package guesswho;

import java.net.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import Messages.*;
import Messages.Error;
//import java.net.InetAddress;

/*
 * Provides support methods to connect and communicate with the 
 * game server
 */
public class Network {
//	static InetAddress SERVER_ADDRESS;
	static Socket sock;
	static ArrayList<String> outbuf;
	static String inbuf;
	//Fix types later
	static ArrayList<Message> msgs;
	static byte[] buf;
	static OutputStream outputStream;
	static DataOutputStream dataOutputStream;
	static InputStream inputStream;
	static BufferedReader in;
	
	
	
	/*
	 * Network Constructor
	 */
	public Network() {
//		this.SERVER_ADDRESS = new InetAddress();
		outbuf = new ArrayList<String>();
		inbuf = "";
		msgs = new ArrayList<Message>();
	}
	
	
	
	/*
	 * Connects to the game server
	 */
	public static boolean connect() {
		close();

		//add ability to connect to a different game server?
		   System.out.println("Attempting to connect...");
		try {
			sock = new Socket("Sockette.net", 9876);
			 // get the output stream from the socket.
	        OutputStream outputStream = sock.getOutputStream();
	        // create a data output stream from the output stream so we can send data through it
	       dataOutputStream = new DataOutputStream(outputStream);
	     //get the input stream from the connected socket
	        inputStream = sock.getInputStream();
//	        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			System.out.println("Connected");
			return true;
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldn't connect.");
			close();
			return true;
			//TODO change this to false once network is connecting
		}
	

		
	}
	
	
	/**
	 * Adds data to be sent to the out buffer
	 */
	public void send(Message data) {
		System.out.println("Sending: " + data);
		Gson gson = new Gson();
		outbuf.add( gson.toJson(data) + "\n");
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
			System.out.println("Couldn't close connection");
		}
	}
	
	/**
	 * Sends data from the outbuffer
	 * and inputs data from the server to inbuf,
	 * then decodes inbuf to messages
	 */
	public ArrayList<Message> do_communication() {		
		//only need this is not all of each message is sent in each?
//		ArrayList<JsonObject> oldMsgs = msgs;
		msgs = new ArrayList<Message>();
		System.out.println("Do Communication is running");
		if (sock == null) {
			return msgs;
		}

		byte[] encodedSend = new byte[300];
		//send message to server
		if (!outbuf.isEmpty()) {
			for(int h =0; h < outbuf.size();h++) {
				String send = outbuf.get(h);
				try {
					encodedSend = send.getBytes("UTF8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				System.out.println("Trying to send to server");
	//			System.out.println((String) newOutbuf);
				try {
					sock.setSoTimeout(1);
					 //remember how much is sent, then make the outbuffer the remainder
					dataOutputStream.write(encodedSend);
			        dataOutputStream.flush();
		       
		       
				} 
				catch (SocketException e) {
					System.out.print(e);
					
				} catch (IOException e) {
					System.out.print(e);
				}
			}
			
			 outbuf = new ArrayList<String>();
		     System.out.println("Sent to server");
		}
		//recieve message from server
		try {
			sock.setSoTimeout(2);
			System.out.println("Trying to receive from server");
	        // create a DataInputStream so we can read data from it.
	        DataInputStream dataInputStream = new DataInputStream(inputStream);
	        byte[] bytes = new byte[1000];
	        int i= 0;
	        while (dataInputStream.available() > 0) {
	        	
	        	bytes[i] = dataInputStream.readByte();
	        	
	        	i++;
	        }
	        String temp = new String(bytes);
        	inbuf = temp;
        	if (inbuf.trim().length() > 0) {
        		System.out.println("*******************MESSAGE RECEIVED*********************");
//        		System.out.println(inbuf);
        		String[] msgArray;
        		msgArray = inbuf.split("\n");
    			Gson gson = new Gson();
//    			JsonReader reader = new JsonReader(new StringReader(inbuf));
//    			reader.setLenient(true);
    			
    			//Anna do this (make the classes work)
    			//to take in multiple messages at once: split inbuf by /n and put into array
    			// to parse to specific class, probably going to need to use JsonParser with
    			//begin and end object to get type
    			JsonParser parser = new JsonParser();
    			
    			for (int j = 0; j  < msgArray.length;j++) {
//    				System.out.println("Checking: " + msgArray[j].trim());
    				if (!msgArray[j].trim().equals("")) {
	    				JsonElement jsonTree = parser.parse(msgArray[j].trim());
	    				String TYPE = jsonTree.getAsJsonObject().get("TYPE").toString();
//	    				System.out.println("Trying to decode: " + msgArray[j].trim() + "of type " + TYPE);
	    				if (TYPE.equals("\"JOIN\"")) {
	    					System.out.println("JOIN: " + gson.fromJson(msgArray[j].trim(), Join.class));
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Join.class));
	    				}
	    				else if (TYPE.equals("\"LEAVE\"")) {
	    					System.out.println("LEAVE: " + gson.fromJson(msgArray[j].trim(), Leave.class));
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Leave.class));
	    				}
	    				else if (TYPE.equals("\"ROOM_STATUS\"")) {
	    					System.out.println("ROOM_STATUS:" + gson.fromJson(msgArray[j].trim(), Room_Status.class));
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Room_Status.class));
	    				}
	    				else if (TYPE.equals("\"ERROR\"")) {
	    					System.out.println("ERROR:" + gson.fromJson(msgArray[j].trim(), Room_Status.class));
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Error.class));
	    				}
	    				else if (TYPE.equals("\"DATA\"")) {
	    					//need to specify data type
	    					System.out.println(msgArray[j].trim().toString());
		    				String Sender = jsonTree.getAsJsonObject().get("SENDER").toString();
		    				String name = "\""+Controller.player.getName()+"\"";
		    				if (!Sender.equals(name)) {
		    					JsonElement msg2 = jsonTree.getAsJsonObject().get("msg");
		    					String type = msg2.getAsJsonObject().get("TYPE").toString();
		    					System.out.println("#################################### "+ type);
		    					if (type.equals("\"cards\"")) {
		    						System.out.println("DATA: cards:" + gson.fromJson(msg2, Cards.class));
			    					msgs.add(gson.fromJson(msg2, Cards.class));
		    					}
		    					else if (type.equals("\"redraw")) {
		    						System.out.println("DATA: redraw:" + gson.fromJson(msg2, Cards.class));
			    					msgs.add(gson.fromJson(msg2, Message.class));
		    					}
		    					else if (type.equals("\"turnUpdate")) {
		    						System.out.println("DATA: turnUpdate:" + gson.fromJson(msg2, Cards.class));
			    					msgs.add(gson.fromJson(msg2, TurnUpdate.class));
		    					}
		    					else if (type.equals("\"guess")) {
		    						System.out.println("DATA: guess:" + gson.fromJson(msg2, Cards.class));
			    					msgs.add(gson.fromJson(msg2, Guess.class));
		    					}
		    					else if (type.equals("\"chat")) {
		    						System.out.println("DATA: chat:" + gson.fromJson(msg2, Cards.class));
			    					msgs.add(gson.fromJson(msg2, Chat.class));
		    					}
		    				}
	    					
	    				}
	    				else {
	    					System.out.println("OTHER: " + gson.fromJson(msgArray[j].trim(), Message.class));
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Message.class));
	    				}
    				}
//    				System.out.println("Decoded: " + gson.fromJson(msgArray[j].trim(), Message.class));
    			}
    			
    		

    				
//    			Message test = gson.fromJson(inbuf.trim(), Message.class);
//    			System.out.println("Message: " + test);
    			
    			
//    			msgs.add(test);
    			
//    			System.out.print("Inbuf: " + inbuf);
    			System.out.println("Received from server");


        	}
			
//	        inbuf = new String(IOUtilities.streamToBytes(inputStream));
			//need to add processing for string to become JSONObjects
//			System.out.println("Data stream set up");
//			JsonParser jsonParser = new JsonParser();
//			JsonElement jsonTree = jsonParser.parse(inbuf);
//			if (jsonTree.isJsonObject()) {
//				JsonObject jsonObject = jsonTree.getAsJsonObject();
//				System.out.print(jsonObject);
//				msgs.add(jsonObject);
//			}
			
			// ask for help on this one
			
//			 JsonReader reader = new JsonReader(new StringReader(inbuf));
//			 JsonObject array = reader.readObject();
//			 reader.close();
	    
			
			
		}
		catch (Exception e) {
			System.out.println("Tried to receive from server");
			e.printStackTrace();
		}
		
		inbuf = "";
		for (int i =0; i < msgs.size(); i++ ) {
			System.out.println(msgs.get(i));
		}
		
		return msgs;
		
		
	}
	

}
