package fr.ensisa.hassenforder.chatrooms.server;

import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.network.Protocol;

public class MessagesReader extends BasicAbstractReader {

    private String userName;

    public MessagesReader(InputStream inputStream) {
	super(inputStream);
    }

    public void receive() {
	type = readInt();
	switch (type) {
	case Protocol.CONNECT:
	    userName = readString();
	}
    }

    public String getUserName() {
	return userName;
    }

}
