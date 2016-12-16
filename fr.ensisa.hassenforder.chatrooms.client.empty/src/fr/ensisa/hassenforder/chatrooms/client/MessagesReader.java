package fr.ensisa.hassenforder.chatrooms.client;

import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;

public class MessagesReader extends BasicAbstractReader {

	public MessagesReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {
		case 0: break;
		}
	}

}
