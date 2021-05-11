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
 * Network
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
	
	
	/**
	 * Network Constructor
	 */
	public Network() {
		outbuf = new ArrayList<String>();
		inbuf = "";
		msgs = new ArrayList<Message>();
	}
	
	
	/**
	 * Connects to the game server
	 */
	public static boolean connect() {
		close();

		//add ability to connect to a different game server?
		   System.out.println("Attempting to connect...");
		try {
//			sock = new Socket("0.0.0.0", 9876);
			sock = new Socket("Sockette.net", 9878);
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
		Gson gson = new Gson();
		outbuf.add( gson.toJson(data) + "\n");
	}
	
	/**
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
		}
		//receive message from server
		try {
			sock.setSoTimeout(2);
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
        		String[] msgArray;
        		msgArray = inbuf.split("\n");
    			Gson gson = new Gson();
    			JsonParser parser = new JsonParser();			
    			for (int j = 0; j  < msgArray.length;j++) {
    				if (!msgArray[j].trim().equals("")) {
	    				JsonElement jsonTree = parser.parse(msgArray[j].trim());
	    				String TYPE = jsonTree.getAsJsonObject().get("TYPE").toString();
	    				if (TYPE.equals("\"JOIN\"")) {
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Join.class));
	    				}
	    				else if (TYPE.equals("\"LEAVE\"")) {
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Leave.class));
	    				}
	    				else if (TYPE.equals("\"ROOM_STATUS\"")) {
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Room_Status.class));
	    				}
	    				else if (TYPE.equals("\"ERROR\"")) {
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Error.class));
	    				}
	    				else if (TYPE.equals("\"DATA\"")) {
	    					//get message from inside DATA and return if not from self
		    				String Sender = jsonTree.getAsJsonObject().get("SENDER").toString();
		    				String name = "\""+Controller.player.getName()+"\"";
		    				JsonElement msg2 = jsonTree.getAsJsonObject().get("msg");
	    					String type = msg2.getAsJsonObject().get("TYPE").toString();
		    				if (!Sender.equals(name)) {
		    					if (type.equals("\"cards\"")) {
			    					msgs.add(gson.fromJson(msg2, Cards.class));
		    					}
		    					else if (type.equals("\"redraw\"")) {
			    					msgs.add(gson.fromJson(msg2, Message.class));
		    					}
		    					else if (type.equals("\"continue\"")) {
			    					msgs.add(gson.fromJson(msg2, Message.class));
		    					}
		    					else if (type.equals("\"turnUpdate\"")) {
			    					msgs.add(gson.fromJson(msg2, TurnUpdate.class));
		    					}
		    					else if (type.equals("\"guess\"")) {
			    					msgs.add(gson.fromJson(msg2, Guess.class));
		    					}
		    					else if (type.equals("\"chat\"")) {
			    					msgs.add(gson.fromJson(msg2, Chat.class));
		    					}
		    				}
		    				
	    				}
	    				else {
	    					msgs.add(gson.fromJson(msgArray[j].trim(), Message.class));
	    				}
    				}
    			}
        	}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		inbuf = "";
		return msgs;
	}
	

}
