package fr.ensisa.hassenforder.chatrooms.server;

import java.io.OutputStream;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;

public class CommandWriter extends BasicAbstractWriter {

	public CommandWriter(OutputStream outputStream) {
		super (outputStream);
	}

	@Override
	protected void writeInt(int v) {
	    super.writeInt(v);
	}
	@Override
	protected void writeString(String v) {
		super.writeString(v);
	}

	@Override
	public void send() {
	    super.send();
	}

}
