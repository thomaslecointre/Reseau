package fr.ensisa.hassenforder.chatrooms.client;

import java.io.OutputStream;

import fr.ensisa.hassenforder.chatrooms.client.model.Channel;
import fr.ensisa.hassenforder.chatrooms.client.model.ChannelType;
import fr.ensisa.hassenforder.chatrooms.client.model.Message;
import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.network.Protocol;

public class CommandWriter extends BasicAbstractWriter {

	public CommandWriter(OutputStream outputStream) {
		super (outputStream);
	}

	public void createConnect(String name)
	{
		writeInt(Protocol.CONNECT);
		writeString(name);
		
	}
	
	public void disconnect(String name)
	{
		writeInt(Protocol.DISCONNECT);
		writeString(name);
	}

	public void channelCreation(String name, String channel, ChannelType type)
	{
		writeInt(Protocol.CREATE);
		writeString(channel);
		writeInt(type.ordinal());
		writeString(name);		
	}

	public void loadAllChannels(String name)
	{
		writeInt(Protocol.LOAD);
		writeString(name);
	}

	public void channelSubsctiptionChange(String name, Channel description, boolean selected)
	{
		writeInt(Protocol.CREATE);
		writeString(name);
		writeString(description.getName());
		writeBoolean(selected);	
	}

	public void createModerationState(String name, Message message, boolean approved)
	{
		writeInt(Protocol.CREATE);
		writeString(name);
		writeString(message.getText());
		writeBoolean(approved);
	}

	public void createMessage(String name, String channelName, String text)
	{
		writeInt(Protocol.NEW_MESSAGE);
		writeString(name);
		writeString(channelName);
		writeString(text);		
	}

}
