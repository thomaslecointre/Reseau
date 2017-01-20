package fr.ensisa.hassenforder.chatrooms.client;

import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

import fr.ensisa.hassenforder.chatrooms.client.model.Channel;
import fr.ensisa.hassenforder.chatrooms.client.model.Model;
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
    private String errorMessage = "";
	private List<Channel> channels = new ArrayList<Channel>();

    public CommandReader(InputStream inputStream) {
    	super(inputStream);
    }

    public void receive() {
		type = readInt();
		
		switch (type) {
			case Protocol.CONNECT_OK:
			    // TODO
			    break;
			case Protocol.CREATE_OK:
			    // TODO
			    break;
			case Protocol.LOAD_OK:
			    int size = readInt();
			    channels.clear();
			    for(int i = 0; i < size; i++) {
			    	String channelName = readString();
			    	int mode = readInt();
			    	String modoName = readString();
			    	boolean subscriptionBool = readBoolean();
			    	ChannelType modeCT;
			    	if (mode == 100) {
			    		modeCT = ChannelType.FREE;
			    		Channel channel = new Channel(channelName, modeCT,modoName, subscriptionBool);
			    		channels.add(channel);
			    	} else if (mode==101) {
			    		modeCT = ChannelType.MODERATED;
			    		Channel channel = new Channel(channelName, modeCT, modoName, subscriptionBool);
			    		channels.add(channel);
			    	}
			    }
			    break;
			case Protocol.CONNECT_KO:
			    errorMessage = readString(); 
				break;
			case Protocol.CREATE_KO:
				errorMessage = readString();
				break;
			case Protocol.LOAD_KO:
				errorMessage = readString();
				break;
			case Protocol.SUBSCRIBE_KO:
				errorMessage = readString();
				break;
			case Protocol.UNSUBSCRIBE_KO:
				errorMessage = readString();
				break;
			case Protocol.NEW_MESSAGE_KO:
				errorMessage = readString();
				break;
			case Protocol.VALIDATE_KO:
				errorMessage = readString();
				break;
			case Protocol.INVALIDATE_KO:
				errorMessage = readString();
				break;
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
    
    public String getErrorMessage() {
    	return this.errorMessage;
    }

    public ChannelType getChannelType() {
		if (channelType == Protocol.FREE)
		    return ChannelType.FREE;
		else if (channelType == Protocol.MODERATED)
		    return ChannelType.MODERATED;
		return null;
    }

    public List<Channel> getAllChannels() {
    	/*
		List<Channel> channels = new ArrayList<Channel>();
		for (int i = 0; i < readInt(); i++) {
		    channels.add(new Channel(readString(), getChannelType(), readString(), readBoolean()));
		}
		*/
		return channels;
    }

}
