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
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import Messages.*;
import Messages.Error;

/*
 * Manages the connection to the server including handling for
 * messages received and sent and JSON parsing for messages
 */
public class Network {
	static Socket sock;
	static ArrayList<String> outbuf;
	static String inbuf;
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
			sock = new Socket("0.0.0.0", 9876);
//			sock = new Socket("Sockette.net", 9878);
			// get the output stream from the socket.
	        OutputStream outputStream = sock.getOutputStream();
	        // create a data output stream from the output stream so we can send data through it
	        dataOutputStream = new DataOutputStream(outputStream);
	        //get the input stream from the connected socket
	        inputStream = sock.getInputStream();
			
			System.out.println("Connected");
			return true;
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Couldn't connect.");
			close();
			return false;
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
		msgs = new ArrayList<Message>();
		System.out.println("Do Communication is running");
		if (sock == null) {
			return msgs;
		}
		if (sock.isClosed()) {
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
					e1.printStackTrace();
				}
			
				System.out.println("Trying to send to server");
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
		//receive message from server
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
        		String[] msgArray;
        		msgArray = inbuf.split("\n");
    			Gson gson = new Gson();
    			JsonParser parser = new JsonParser();			
    			for (int j = 0; j  < msgArray.length;j++) {
    				if (!msgArray[j].trim().equals("")) {
    					System.out.println(msgArray[j].trim().toString());
	    				JsonElement jsonTree = parser.parse(msgArray[j].trim());
	    				String TYPE = jsonTree.getAsJsonObject().get("TYPE").toString();
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
	    					System.out.println("ERROR:" + gson.fromJson(msgArray[j].trim(), Error.class));
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Error.class));
	    				}
	    				else if (TYPE.equals("\"DATA\"")) {
	    					//get message from inside DATA and return if not from self
		    				String Sender = jsonTree.getAsJsonObject().get("SENDER").toString();
		    				String name = "\""+Controller.player.getName()+"\"";
		    				JsonElement msg2 = jsonTree.getAsJsonObject().get("msg");
	    					String type = msg2.getAsJsonObject().get("TYPE").toString();
		    				if (!Sender.equals(name)) {
		    					System.out.println("#################################### Recieved message from other player of type "+ type);
		    					if (type.equals("\"cards\"")) {
		    						System.out.println("DATA: cards:" + gson.fromJson(msg2, Cards.class));
			    					msgs.add(gson.fromJson(msg2, Cards.class));
		    					}
		    					else if (type.equals("\"redraw\"")) {
		    						System.out.println("DATA: redraw:" + gson.fromJson(msg2, Message.class));
			    					msgs.add(gson.fromJson(msg2, Message.class));
		    					}
		    					else if (type.equals("\"continue\"")) {
		    						System.out.println("DATA: continue:" + gson.fromJson(msg2, Message.class));
			    					msgs.add(gson.fromJson(msg2, Message.class));
		    					}
		    					else if (type.equals("\"turnUpdate\"")) {
		    						System.out.println("DATA: turnUpdate:" + gson.fromJson(msg2, TurnUpdate.class));
			    					msgs.add(gson.fromJson(msg2, TurnUpdate.class));
		    					}
		    					else if (type.equals("\"guess\"")) {
		    						System.out.println("DATA: guess:" + gson.fromJson(msg2, Guess.class));
			    					msgs.add(gson.fromJson(msg2, Guess.class));
		    					}
		    					else if (type.equals("\"chat\"")) {
		    						System.out.println("DATA: chat:" + gson.fromJson(msg2, Chat.class));
			    					msgs.add(gson.fromJson(msg2, Chat.class));
		    					}
		    				}
		    				else {
		    					System.out.println("Ignored message from self of type "+ type);
		    				}
		    				
	    				}
	    				else if (TYPE.equals("\"GUESS\"")) {
	    					System.out.println("GUESS: " + gson.fromJson(msgArray[j].trim(), Guess.class));
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Guess.class));
	    				}
	    				else {
	    					System.out.println("OTHER: " + gson.fromJson(msgArray[j].trim(), Message.class));
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Message.class));
	    				}
    				}
    			}
    			System.out.println("Received from server");
        	}
		}
		catch (Exception e) {
			System.out.println("Tried to receive from server");
			e.printStackTrace();
		}
		inbuf = "";
		return msgs;
	}
	

}
