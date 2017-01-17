package fr.ensisa.hassenforder.chatrooms.client;

import java.util.List;

import fr.ensisa.hassenforder.chatrooms.client.model.Channel;
import fr.ensisa.hassenforder.chatrooms.client.model.ChannelType;
import fr.ensisa.hassenforder.chatrooms.client.model.Message;
import fr.ensisa.hassenforder.chatrooms.client.model.Model;
import fr.ensisa.hassenforder.chatrooms.client.model.ModelListener;
import fr.ensisa.hassenforder.chatrooms.client.ui.GUI;
import fr.ensisa.hassenforder.chatrooms.client.ui.GUIListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hassenforder
 */
public class Application implements GUIListener, NetworkListener {

    private CommandSession command;
    private MessagesSession messages;
    private Model model;
    private ModelListener gui;

    @Override
    public void notifyConnection(String name) {
	if (model.isConnected()) {
	    String n = model.getName() == null ? name : model.getName();
	    command.doDisconnect(n);
	    command.close();
	    command = null;
	    messages.close();
	    messages = null;
	    model.setConnected(false);
	    gui.updateConnection(model, model.getName() + " is disconnected");
	} else {
	    command = new CommandSession();
	    command.open();
	    if (command.doConnect(name)) {
		messages = new MessagesSession(name, this);
		messages.open();
		model.setName(name);
		model.setConnected(true);
		gui.updateConnection(model, model.getName() + " is connected");
	    } else {
		command = null;
		gui.updateConnection(model, name + " is not allowed");
	    }
	}
    }

    @Override
    public void notifyChannelCreation(String channelName, ChannelType type) {
	if (model.isConnected()) {
	    if (command.doChannelCreation(model.getName(), channelName, type)) {
		model.getChannels().add(new Channel(channelName, type, model.getName(), true));
		gui.updateChannelCreated(model, "Channel created");
	    } else {
		gui.updateChannelCreated(model, "Channel cannot be created");
	    }
	} else {
	    gui.updateStatus("cannot do this I am not connected");
	}
    }

    @Override
    public void notifyChannelLoadAll() {
	if (model.isConnected()) {
	    List<Channel> descriptions = command.doLoadAllChannels(model.getName());
	    if (descriptions != null) {
		model.getChannels().clear();
		for (Channel d : descriptions) {
		    model.getChannels().add(d);
		}
		gui.updateChannelList(model, "Channels loaded");
	    } else {
		gui.updateChannelList(model, "Channel cannot be loaded");
	    }
	} else {
	    gui.updateStatus("cannot do this I am not connected");
	}
    }

    @Override
    public void notifyChannelSubscriptionChange(Channel description, boolean selected) {
	if (model.isConnected()) {
	    if (command.doChannelSubscriptionChange(model.getName(), description, selected)) {
		description.setSubscribed(selected);
		gui.updateSubscribtionChange(model, "Subscription changed");
	    } else {
		gui.updateSubscribtionChange(model, "Subscription cannot be changed");
	    }
	} else {
	    gui.updateStatus("cannot do this I am not connected");
	}
    }

    @Override
    public void notifyModerationsLoad() {
	/*
	 * if (model.isConnected()) { gui.updateModerationList(model,
	 * "Moderation list loaded"); } else { gui.updateStatus
	 * ("cannot do this I am not connected"); }
	 */
    }

    @Override
    public void notifyModerationState(Message message, boolean approved) {
	if (model.isConnected()) {
	    if (command.createModerationState(model.getName(), message, approved)) {
		model.getPendingMessages().remove(message);
		gui.updateModerationState(model, "Moderation changed");
	    } else {
		gui.updateModerationState(model, "Moderation cannot be changed");
	    }
	} else {
	    gui.updateStatus("cannot do this I am not connected");
	}
    }

    @Override
    public void notifyClearMessages() {
	model.getIncomingMessages().clear();
	gui.updateMessageList(model, "Message list cleared");
    }

    @Override
    public void notifyMessagesLoad() {
	/*
	 * if (model.isConnected()) { gui.updateMessageList(model,
	 * "Message list loaded"); } else { gui.updateStatus
	 * ("cannot do this I am not connected"); }
	 */
    }

    @Override
    public void notifySendMessage(String channelName, String text) {
	if (model.isConnected()) {
	    if (command.doMessage(model.getName(), channelName, text)) {
		gui.updateMessageSent(model, "Message sent");
	    } else {
		gui.updateStatus("Message cannot be sent");
	    }
	} else {
	    gui.updateStatus("cannot do this I am not connected");
	}
    }

    @Override
    public void notifyIncomingModerations(List<Message> messages) {
	if (model.isConnected()) {
	    model.getPendingMessages().clear();
	    model.getPendingMessages().addAll(messages);
	    gui.updateModerationList(model, "Incoming moderations");
	} else {
	    gui.updateStatus("cannot do this I am not connected");
	}
    }

    @Override
    public void notifyIncomingMessages(List<Message> messages) {
	if (model.isConnected()) {
	    model.getIncomingMessages().clear();
	    model.getIncomingMessages().addAll(messages);
	    gui.updateMessageList(model, "Incoming messages");
	} else {
	    gui.updateStatus("cannot do this I am not connected");
	}
    }

    public void start() {
	model = new Model();
	java.awt.EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		GUI g = new GUI(Application.this);
		g.setVisible(true);
		gui = g;
	    }
	});
    }

    public static void main(String[] args) {
	new Application().start();
    }

}
