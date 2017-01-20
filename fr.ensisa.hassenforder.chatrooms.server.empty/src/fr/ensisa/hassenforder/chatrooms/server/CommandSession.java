package fr.ensisa.hassenforder.chatrooms.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.hassenforder.chatrooms.server.model.Channel;
import fr.ensisa.hassenforder.network.Protocol;

public class CommandSession extends Thread {

	private Socket connection;
	private NetworkListener listener;

	public CommandSession(Socket connection, NetworkListener listener) {
		this.connection = connection;
		this.listener = listener;
		if (listener == null)
			throw new RuntimeException("listener cannot be null");
	}

	public void close() {
		this.interrupt();
		try {
			if (connection != null)
				connection.close();
		} catch (IOException e) {
		}
		connection = null;
	}

	public boolean operate() {
		CommandWriter writer;
		try {
			writer = new CommandWriter(connection.getOutputStream());
			CommandReader reader = new CommandReader(connection.getInputStream());
			reader.receive();
			OperationStatus os;
			switch (reader.getType()) {
			case -1:
				return false; // socket closed
			case Protocol.CONNECT:
				os = listener.connectCommandUser(reader.getName(), this);
				if (!os.equals(OperationStatus.NOW_CONNECTED)) {
					writer.writeInt(Protocol.CONNECT_KO);
					if(os.equals(OperationStatus.UNKNOWN_USER))
						writer.writeString("Server does not recognise user.");
					if(os.equals(OperationStatus.ALLREADY_CONNECTED))
						writer.writeString("User is already connected.");
				} else {
					writer.writeInt(Protocol.CONNECT_OK);
				}
				writer.send();
				return true;
			case Protocol.DISCONNECT:
				os = listener.disconnectUser(reader.getName());
				return true;
			case Protocol.CREATE:
				os = listener.createChannel(reader.getName(), reader.getChannel(), reader.getChannelType());
				if (!os.equals(OperationStatus.CHANNEL_CREATED)) {
					writer.writeInt(Protocol.CREATE_KO);
					if(os.equals(OperationStatus.UNKNOWN_USER))
						writer.writeString("Server does not recognise user.");
					if(os.equals(OperationStatus.NOT_CONNECTED))
						writer.writeString("User is not connected.");
					if(os.equals(OperationStatus.CHANNEL_CREATION_FAILED))
						writer.writeString("Server failed to create channel.");
					if(os.equals(OperationStatus.CHANNEL_EXISTS))
						writer.writeString("Channel already exists on the server.");
				} else {
					writer.writeInt(Protocol.CREATE_OK);
				}
				writer.send();
				return true;
			case Protocol.LOAD:
				List<Channel> channels = listener.loadChannels(reader.getName());
				if (channels != null) {
					writer.writeInt(Protocol.LOAD_OK);
					writer.writeInt(channels.size());
					for (Channel channel : channels) {
						writer.writeString(channel.getName());
						writer.writeInt(channel.getChannelTypeInt());
						if (channel.getModerator() == null) {
							writer.writeString("No Moderator");
						} else {
							writer.writeString(channel.getModerator().getName());
						}
						writer.writeBoolean(channel.isSubscriptor(reader.getName()));
					}
				} else {
					writer.writeInt(Protocol.LOAD_KO);
				}
				writer.send();
				return true;
			case Protocol.SUBSCRIBE:
				os = listener.ChangeChannelSubscription(reader.getName(), reader.getChannel(),
						reader.getSubscription());
				if (!os.equals(OperationStatus.SUBSCRIPTION_CHANGED)) {
					writer.writeInt(Protocol.SUBSCRIBE_KO);
					if(os.equals(OperationStatus.UNKNOWN_USER))
						writer.writeString("Server does not recognise user.");
					if(os.equals(OperationStatus.NOT_CONNECTED))
						writer.writeString("User is not connected.");
					if(os.equals(OperationStatus.UNKNOWN_CHANNEL))
						writer.writeString("Server does not recognise the channel.");
				} else {
					writer.writeInt(Protocol.SUBSCRIBE_OK);
				}
				writer.send();
				return true;
			case Protocol.UNSUBSCRIBE:
				os = listener.ChangeChannelSubscription(reader.getName(), reader.getChannel(),
						reader.getSubscription());
				if (!os.equals(OperationStatus.SUBSCRIPTION_CHANGED)) {
					writer.writeInt(Protocol.UNSUBSCRIBE_KO);
					if(os.equals(OperationStatus.UNKNOWN_USER))
						writer.writeString("Server does not recognise user.");
					if(os.equals(OperationStatus.NOT_CONNECTED))
						writer.writeString("User is not connected.");
					if(os.equals(OperationStatus.UNKNOWN_CHANNEL))
						writer.writeString("Server does not recognise the channel.");
				} else {
					writer.writeInt(Protocol.UNSUBSCRIBE_OK);
				}
				writer.send();
				return true;
			case Protocol.NEW_MESSAGE:
				os = listener.sendMessage(reader.getName(), reader.getChannel(), reader.getText());
				if (!os.equals(OperationStatus.MESSAGE_SENT)) {
					writer.writeInt(Protocol.NEW_MESSAGE_KO);
					if(os.equals(OperationStatus.UNKNOWN_USER))
						writer.writeString("Server does not recognise user.");
					if(os.equals(OperationStatus.NOT_CONNECTED))
						writer.writeString("User is not connected.");
					if(os.equals(OperationStatus.UNKNOWN_CHANNEL))
						writer.writeString("Server does not recognise the channel.");
				} else {
					writer.writeInt(Protocol.NEW_MESSAGE);
				}
				writer.send();
				return true;
			case Protocol.VALIDATE:
				os = listener.SetApprobation(reader.getName(), reader.getMessageId(), reader.getApproved());
				if (!os.equals(OperationStatus.APPROBATION_CHANGED)) {
					writer.writeInt(Protocol.VALIDATE_KO);
					if(os.equals(OperationStatus.UNKNOWN_USER))
						writer.writeString("Server does not recognise user.");
					if(os.equals(OperationStatus.NOT_CONNECTED))
						writer.writeString("User is not connected.");
					if(os.equals(OperationStatus.UNKNOWN_MESSAGE))
						writer.writeString("Server does not recognise the message.");
				} else {
					writer.writeInt(Protocol.VALIDATE);
				}
				writer.send();
				return true;
			case Protocol.INVALIDATE:
				os = listener.SetApprobation(reader.getName(), reader.getMessageId(), reader.getApproved());
				if (!os.equals(OperationStatus.APPROBATION_CHANGED)) {
					writer.writeInt(Protocol.INVALIDATE_KO);
					if(os.equals(OperationStatus.UNKNOWN_USER))
						writer.writeString("Server does not recognise user.");
					if(os.equals(OperationStatus.NOT_CONNECTED))
						writer.writeString("User is not connected.");
					if(os.equals(OperationStatus.UNKNOWN_MESSAGE))
						writer.writeString("Server does not recognise the message.");
				} else {
					writer.writeInt(Protocol.INVALIDATE);
				}
				writer.send();
				return true;
			default:
				return false; // connection jammed
			}

		} catch (IOException e) {
			return false;
		}
	}

	public void run() {
		while (true) {
			if (!operate())
				break;
		}
		try {
			if (connection != null)
				connection.close();
		} catch (IOException e) {
		}
	}

}
