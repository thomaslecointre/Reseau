package fr.ensisa.hassenforder.chatrooms.client;

import java.io.OutputStream;

import fr.ensisa.hassenforder.chatrooms.client.model.Channel;
import fr.ensisa.hassenforder.chatrooms.client.model.ChannelType;
import fr.ensisa.hassenforder.chatrooms.client.model.Message;
import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.network.Protocol;

public class CommandWriter extends BasicAbstractWriter {

    public CommandWriter(OutputStream outputStream) {
	super(outputStream);
    }

    public void createConnect(String name) {
	writeInt(Protocol.CONNECT);
	writeString(name);

    }

    public void disconnect(String name) {
	writeInt(Protocol.DISCONNECT);
	writeString(name);
    }

    public void channelCreation(String name, String channel, ChannelType type) {
	writeInt(Protocol.CREATE);
	writeString(name);
	writeString(channel);
	if (type.equals(ChannelType.FREE))
	    writeInt(Protocol.FREE);
	if (type.equals(ChannelType.MODERATED))
	    writeInt(Protocol.MODERATED);
    }

    public void loadAllChannels(String name) {
	writeInt(Protocol.LOAD);
	writeString(name);
    }

    public void channelSubsctiptionChange(String name, Channel description, boolean selected) {
	writeInt(Protocol.SUBSCRIBE);
	writeString(name);
	writeString(description.getName());
	writeBoolean(selected);
    }

    public void createModerationState(String name, Message message, boolean approved) {
	if(approved)
	    writeInt(Protocol.VALIDATE);
	else
	    writeInt(Protocol.INVALIDATE);
	writeString(name);
	writeInt(message.getId());
    }

    public void createMessage(String name, String channelName, String text) {
	writeInt(Protocol.NEW_MESSAGE);
	writeString(name);
	writeString(channelName);
	writeString(text);
    }

}
