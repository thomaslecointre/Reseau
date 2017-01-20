package fr.ensisa.hassenforder.chatrooms.client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fr.ensisa.hassenforder.chatrooms.client.model.Message;
import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.network.Protocol;

public class MessagesReader extends BasicAbstractReader {

	private int size;
	private String name;
	private String moderatorName;
	private String channelName;
	private String text;
	private int messageId;
	private List<Message> messages = new ArrayList<Message>();
	private HashMap<Message, String> moderations = new HashMap<Message, String>();

	private String errorMessage;

	public MessagesReader(InputStream inputStream) {
		super(inputStream);
	}

	public void receive() {
		type = readInt();
		switch (type) {
		case 0:
			break;
		case Protocol.CONNECT_KO:
			errorMessage = readString();
		case Protocol.NEW_MESSAGE:
			messages.clear();
			size = readInt();
			for (int i = 0; i < size; i++) {
				name = readString();
				channelName = readString();
				text = readString();
				messageId = readInt();
				Message message = new Message(channelName, messageId, text, name);
				messages.add(message);
			}
			break;
		case Protocol.PENDING_MESSAGE:
			System.out.println("Receiving moderations...");
			moderations.clear();
			size = readInt();
			System.out.println("Moderations size " + size);
			for (int i = 0; i < size; i++) {
				moderatorName = readString();
				name = readString();
				channelName = readString();
				text = readString();
				messageId = readInt();
				Message message = new Message(channelName, messageId, text, name);
				moderations.put(message, moderatorName);
			}
			break;
		}
	}

	public List<Message> getMessages() {
		return messages;
	}

	public List<Message> getModerations(String name) {
		List<Message> messages = new ArrayList<Message>();
		for (Iterator<Message> it = moderations.keySet().iterator(); it.hasNext();) {
			Message message = it.next();
			if (name.equals(moderations.get(message))) {
				messages.add(message);
			}
		}
		System.out.println("Filtered messages size = " + messages.size());
		return messages;
	}

}
