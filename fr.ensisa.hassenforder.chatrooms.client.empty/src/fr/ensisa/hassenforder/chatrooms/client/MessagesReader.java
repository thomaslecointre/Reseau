package fr.ensisa.hassenforder.chatrooms.client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.hassenforder.chatrooms.client.model.Message;
import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.network.Protocol;

public class MessagesReader extends BasicAbstractReader {

    private int size;
    private String name;
    private String channelName;
    private String text;
    private int messageId;
    private List<Message> messages = new ArrayList<Message>();

    public MessagesReader(InputStream inputStream) {
	super(inputStream);
    }

    public void receive() {
	type = readInt();
	switch (type) {
	case 0:
	    break;
	case Protocol.NEW_MESSAGE:
	    messages.clear();
	    size = readInt();
	    System.out.println("Incoming message count = " + size);
	    for(int i = 0; i < size; i++) {
		name = readString();
		channelName = readString();
		text = readString();
		messageId = readInt();
		Message message = new Message(channelName, messageId, text, name);
		messages.add(message);
	    }
	    break;
	}
    }

    public List<Message> getMessages() {
	return messages;
    }

}
