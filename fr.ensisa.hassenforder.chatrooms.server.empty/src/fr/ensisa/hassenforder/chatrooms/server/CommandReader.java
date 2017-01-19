package fr.ensisa.hassenforder.chatrooms.server;

import java.io.InputStream;

import fr.ensisa.hassenforder.chatrooms.server.model.ChannelType;
import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.network.Protocol;

public class CommandReader extends BasicAbstractReader {

    private String name;
    private String channel;
    private int channelType;
    private boolean subscription;
    private int messageId;
    private boolean approved;
    private String text;

    public CommandReader(InputStream inputStream) {
	super(inputStream);
    }

    public void receive() {
	type = readInt();
	switch (type) {
	case Protocol.CONNECT:
	    this.name = readString();
	    break;
	case Protocol.DISCONNECT:
		this.name = readString();
		break;
	case Protocol.CREATE:
	    this.name = readString();
	    this.channel = readString();
	    this.channelType = readInt();
	    break;
	case Protocol.SUBSCRIBE:
	    this.name = readString();
	    this.channel = readString();
	    this.subscription = readBoolean();
	    break;
	case Protocol.UNSUBSCRIBE:
	    this.name = readString();
	    this.channel = readString();
	    this.subscription = readBoolean();
	    break;
	case Protocol.VALIDATE:
	    this.name = readString();
	    this.messageId = readInt();
	    this.approved = true;
	    break;
	case Protocol.INVALIDATE:
	    this.name = readString();
	    this.messageId = readInt();
	    this.approved = false;
	    break;
	case Protocol.LOAD:
	    this.name = readString();
	    break;
	case Protocol.NEW_MESSAGE:
	    this.name = readString();
	    this.channel = readString();
	    this.text = readString();
	    break;
	// case Protocal.DISCONNECT:
	// case Protocol.MODERATED: name; text; approved;
	// ???? case Protocol.FREE: ????
	}
    }

    public String getName() {
	return this.name;
    }

    public String getChannel() {
	return this.channel;
    }

    public ChannelType getChannelType() {
	return channelType == Protocol.FREE ? ChannelType.FREE : ChannelType.MODERATED;
    }

    public boolean getSubscription() {
	return this.subscription;
    }

    public int getMessageId() {
	return this.messageId;
    }

    public boolean getApproved() {
	return this.approved;
    }

    public String getText() {
	return this.text;
    }
}
