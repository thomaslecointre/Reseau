package fr.ensisa.hassenforder.chatrooms.client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.hassenforder.chatrooms.client.model.Channel;
import fr.ensisa.hassenforder.chatrooms.client.model.ChannelType;
import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.network.Protocol;

public class CommandReader extends BasicAbstractReader {

	private String name;
	private String channel;
	private int channelType;
	private boolean subscription;
	private int messageId;
	private boolean approved;

	public CommandReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {case Protocol.CONNECT:
		    this.name = readString();
		case Protocol.CREATE:
		    this.channel = readString();
		    this.channelType = readInt();
		case Protocol.SUBSCRIBE:
		    this.name = readString();
		    this.channel = readString();
		    this.subscription = true;
		case Protocol.UNSUBSCRIBE:
		    this.name = readString();
		    this.channel = readString();
		    this.subscription = false;
		case Protocol.VALIDATE:
		    this.channel = readString();
		    this.name = readString();
		    this.messageId = readInt();
		    this.approved = true;
		case Protocol.INVALIDATE:
		    this.channel = readString();
		    this.name = readString();
		    this.messageId = readInt();
		    this.approved = false;
		
		}
	}
	
    public String getName() {
	return this.name;
    }
    
    public String getChannel() {
	return this.channel;
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
    
    public ChannelType getChannelType() {
    	if(channelType == 100)
    	    return ChannelType.FREE;
    	else if(channelType == 101)
    	    return ChannelType.MODERATED;
    	return null;
    }

    public List<Channel> getAllChannels() {
		List<Channel> channelsList= new ArrayList<Channel>();
		for(int i=0;i<readInt();i++){
			channelsList.add(new Channel(readString(),getChannelType(),readString(),readBoolean()));
		}
		return channelsList;
	}

}
