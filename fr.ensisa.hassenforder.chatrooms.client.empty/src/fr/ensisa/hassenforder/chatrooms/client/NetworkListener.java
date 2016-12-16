package fr.ensisa.hassenforder.chatrooms.client;

import java.util.List;

import fr.ensisa.hassenforder.chatrooms.client.model.Message;

public interface NetworkListener {

	public void notifyIncomingModerations (List<Message> messages);

	public void notifyIncomingMessages (List<Message> messages);
}
