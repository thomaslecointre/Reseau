package fr.ensisa.hassenforder.chatrooms.client;

import java.io.InputStream;

import fr.ensisa.hassenforder.network.BasicAbstractReader;

public class CommandReader extends BasicAbstractReader {

	public CommandReader(InputStream inputStream) {
		super (inputStream);
	}

	public void receive() {
		type = readInt ();
		switch (type) {
		}
	}

}
