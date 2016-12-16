package fr.ensisa.hassenforder.chatrooms.server;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.network.Protocol;

public class CommandSession extends Thread {

	private Socket connection;
	private NetworkListener listener;
	
	public CommandSession(Socket connection, NetworkListener listener) {
		this.connection = connection;
		this.listener = listener;
		if( listener == null) throw new RuntimeException("listener cannot be null");
	}

	public void close () {
		this.interrupt();
		try {
			if (connection != null)
				connection.close();
		} catch (IOException e) {
		}
		connection = null;
	}

	public boolean operate() {
		try {
			CommandWriter writer = new CommandWriter (connection.getOutputStream());
			CommandReader reader = new CommandReader (connection.getInputStream());
			reader.receive ();
			switch (reader.getType ()) {
			case -1 : return false; // socket closed
			case Protocol.CONNECT:
				OperationStatus os = listener.connectCommandUser(reader.getName(), this);
				if(os.equals(OperationStatus.UNKNOWN_USER))
				{
					writer.
					return false;
				}
					
				else if(os.equals(OperationStatus.NOT_CONNECTED)) return false;
				else return true;
			default: return false; // connection jammed
			}
			
			writer.send();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void run() {
		while (true) {
			if (! operate())
				break;
		}
		try {
			if (connection != null) connection.close();
		} catch (IOException e) {
		}
	}

}
