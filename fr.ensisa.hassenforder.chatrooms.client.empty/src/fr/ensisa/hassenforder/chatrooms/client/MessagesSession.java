package fr.ensisa.hassenforder.chatrooms.client;

import java.io.IOException;
import java.net.Socket;

import fr.ensisa.hassenforder.network.Protocol;

public class MessagesSession extends Thread {

    private Socket connection;
    private String name;
    private NetworkListener listener;

    public MessagesSession(String name, NetworkListener listener) {
	this.name = name;
	if (listener == null)
	    throw new RuntimeException("listener cannot be null");
	this.listener = listener;
    }

    public boolean close() {
	this.interrupt();
	try {
	    if (connection != null)
		connection.close();
	    connection = null;
	} catch (IOException e) {
	}
	return true;
    }

    public boolean open() {
	this.close();
	try {
	    connection = new Socket("localhost", Protocol.CHATROOMS_MSG_PORT_ID);
	    start();
	    return true;
	} catch (IOException e) {
	    return false;
	}
    }

    public boolean operate() {
	try {
	    MessagesReader r = new MessagesReader(connection.getInputStream());
	    r.receive();
	    /*
	     * TODO : a giant switch case to manage the request/reply
	     */
	    
	    switch (r.getType()) {
	    case Protocol.NEW_MESSAGE:
		listener.notifyIncomingMessages(r.getMessages());
		return true;
	    case Protocol.PENDING_MESSAGE:
		listener.notifyIncomingModerations(r.getModerations(name));
		return true;
	    default:
		return false;
	    }
	} catch (IOException e) {
	    return false;
	}
    }

    public void run() {
	try {
	    MessagesWriter w = new MessagesWriter(connection.getOutputStream());
	    w.createConnect(name);
	    w.send();
	} catch (IOException e) {
	}
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
