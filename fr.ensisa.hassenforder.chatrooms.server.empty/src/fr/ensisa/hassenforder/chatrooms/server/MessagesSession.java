package fr.ensisa.hassenforder.chatrooms.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.hassenforder.chatrooms.server.model.Channel;
import fr.ensisa.hassenforder.chatrooms.server.model.Message;
import fr.ensisa.hassenforder.network.Protocol;

public class MessagesSession {

	private Socket connection;
	private NetworkListener listener;

	public MessagesSession(Socket connection, NetworkListener listener) {
		this.connection = connection;
		this.listener = listener;
		if (listener == null)
			throw new RuntimeException("listener cannot be null");
	}

	public void close() {
		try {
			if (connection != null)
				connection.close();
		} catch (IOException e) {
		}
		connection = null;
	}

	public boolean processConnection() {
		try {
			MessagesReader reader = new MessagesReader(connection.getInputStream());
			MessagesWriter writer = new MessagesWriter(connection.getOutputStream());
			reader.receive();
			OperationStatus os;
			switch (reader.getType()) {
			case Protocol.CONNECT:
				os = listener.connectMessagesUser(reader.getUserName(), this);
				if (!os.equals(OperationStatus.NOW_CONNECTED)) {
					writer.writeInt(Protocol.CONNECT_KO);
					if (os.equals(OperationStatus.UNKNOWN_USER))
						writer.writeString("Server does not recognise user.");
					if (os.equals(OperationStatus.NOT_CONNECTED))
						writer.writeString("User is not connected.");
				} else {
					writer.writeInt(Protocol.CONNECT_OK);
				}
				writer.send();
				return true;
			default:
				return false;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public boolean dispatchIncomingMessages(List<Message> messages) {
		try {
			MessagesWriter writer = new MessagesWriter(connection.getOutputStream());
			writer.writeInt(Protocol.NEW_MESSAGE);
			writer.writeInt(messages.size());
			for (Message message : messages) {
				writer.writeString(message.getAuthor());
				writer.writeString(message.getChannel().getName());
				writer.writeString(message.getText());
				writer.writeInt(message.getId());
			}
			writer.send();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public boolean dispatchPendingMessages(List<Message> messages) {
		try {
			MessagesWriter writer = new MessagesWriter(connection.getOutputStream());
			writer.writeInt(Protocol.PENDING_MESSAGE);
			writer.writeInt(messages.size());
			for (Message message : messages) {
				writer.writeString(message.getChannel().getModerator().getName());
				writer.writeString(message.getAuthor());
				writer.writeString(message.getChannel().getName());
				writer.writeString(message.getText());
				writer.writeInt(message.getId());
			}
			writer.send();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

}
