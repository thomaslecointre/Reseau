package fr.ensisa.hassenforder.chatrooms.client;

import java.io.OutputStream;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;

public class CommandWriter extends BasicAbstractWriter {

	public CommandWriter(OutputStream outputStream) {
		super (outputStream);
	}

}
