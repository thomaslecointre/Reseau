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
		} else {
		    writer.writeInt(Protocol.CONNECT_OK);
		}
		writer.send();
		return true;
	    case Protocol.CREATE:
		os = listener.createChannel(reader.getName(), reader.getChannel(),
			reader.getChannelType());
		if (!os.equals(OperationStatus.CHANNEL_CREATED)) {
		    writer.writeInt(Protocol.CREATE_KO);
		} else {
		    writer.writeInt(Protocol.CREATE_OK);
		}
		writer.send();
		return true;
	    case Protocol.LOAD:
		// TODO
		return true;
	    case Protocol.SUBSCRIBE:
		os = listener.ChangeChannelSubscription(reader.getName(), reader.getChannel(), reader.getSubscription());
		if(!os.equals(OperationStatus.SUBSCRIPTION_CHANGED)) {
		    writer.writeInt(Protocol.SUBSCRIBE_KO);
		} else {
		    writer.writeInt(Protocol.SUBSCRIBE_OK);
		}
		writer.send();
		return true;
	    case Protocol.UNSUBSCRIBE:
		os = listener.ChangeChannelSubscription(reader.getName(), reader.getChannel(), reader.getSubscription());
		if(!os.equals(OperationStatus.SUBSCRIPTION_CHANGED)) {
		    writer.writeInt(Protocol.UNSUBSCRIBE_KO);
		} else {
		    writer.writeInt(Protocol.UNSUBSCRIBE_OK);
		}
		writer.send();
		return true;
	    case Protocol.NEW_MESSAGE:
		os = listener.sendMessage(reader.getName(), reader.getChannel(), reader.getText());
		if(!os.equals(OperationStatus.MESSAGE_SENT)) {
		    writer.writeInt(Protocol.NEW_MESSAGE_KO);
		} else {
		    // TODO
		}
		writer.send();
		return true;
	    case Protocol.VALIDATE:
		os = listener.SetApprobation(reader.getName(), reader.getMessageId(), reader.getApproved());
		if(!os.equals(OperationStatus.APPROBATION_CHANGED)) {
		    writer.writeInt(Protocol.VALIDATE_KO);
		} else {
		    // TODO
		}
		writer.send();
		return true;
	    case Protocol.INVALIDATE:
		os = listener.SetApprobation(reader.getName(), reader.getMessageId(), reader.getApproved());
		if(!os.equals(OperationStatus.APPROBATION_CHANGED)) {
		    writer.writeInt(Protocol.INVALIDATE_KO);
		} else {
		    // TODO
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
