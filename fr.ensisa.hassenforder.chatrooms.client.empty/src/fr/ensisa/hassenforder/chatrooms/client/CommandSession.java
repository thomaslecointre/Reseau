package fr.ensisa.hassenforder.chatrooms.client;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import fr.ensisa.hassenforder.chatrooms.client.model.Channel;
import fr.ensisa.hassenforder.chatrooms.client.model.ChannelType;
import fr.ensisa.hassenforder.chatrooms.client.model.Message;
import fr.ensisa.hassenforder.chatrooms.client.model.Model;
import fr.ensisa.hassenforder.network.Protocol;

public class CommandSession {

	private Socket connection;
	
	public CommandSession () {
	}

	public boolean close () {
		try {
			if (connection != null) connection.close();
			connection = null;
		} catch (IOException e) {
		}
		return true;
	}
	
	public boolean open () {
		this.close();
		try {
			connection = new Socket("localhost", Protocol.CHATROOMS_CMD_PORT_ID);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean doDisconnect(String name) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			CommandReader r = new CommandReader(connection.getInputStream());
			w.disconnect(name);
			w.send();
			r.receive();
			return true;
			//return false;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean doConnect (String name) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			CommandReader r = new CommandReader(connection.getInputStream());

			w.createConnect(name);
			w.send();
			r.receive();
			return (r.getType() == Protocol.CONNECT_OK) ? true : false;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean doChannelCreation(String name, String channel, ChannelType type) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			CommandReader r = new CommandReader(connection.getInputStream());
			w.channelCreation(name, channel, type);
			w.send();
			r.receive();
			return (r.getType() == Protocol.CREATE_OK) ? true : false;
			
		} catch (IOException e) {
			return false;
		}
	}

	public List<Channel> doLoadAllChannels(String name) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			CommandReader r = new CommandReader(connection.getInputStream());
			w.loadAllChannels(name);
			w.send();
			r.receive();
			return (r.getType() == Protocol.LOAD_OK) ? r.getAllChannels() : null;
		} catch (IOException e) {
			return null;
		}
	}

	public boolean doChannelSubscriptionChange(String name, Channel description, boolean selected) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			CommandReader r = new CommandReader(connection.getInputStream());
			w.channelSubsctiptionChange(name, description, selected);
			w.send();
			r.receive();
			return (r.getType() == Protocol.SUBSCRIBE_OK) ? true : false;
			
		} catch (IOException e) {
			return false;
		}
	}

	public boolean createModerationState(String name, Message message, boolean approved) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			CommandReader r = new CommandReader(connection.getInputStream());
			w.createModerationState(name, message, approved);
			w.send();
			r.receive();
			return (r.getType() == Protocol.FREE) ? true : false;
			
		} catch (IOException e) {
			return false;
		}
	}

	public boolean doMessage(String name, String channelName, String text) {
		try {
			CommandWriter w = new CommandWriter(connection.getOutputStream());
			CommandReader r = new CommandReader(connection.getInputStream());
			w.createMessage(name, channelName, text);
			w.send();
			r.receive();
			return (r.getType() == Protocol.NEW_MESSAGE) ? true : false;
			
		} catch (IOException e) {
			return false;
		}
	}

}
