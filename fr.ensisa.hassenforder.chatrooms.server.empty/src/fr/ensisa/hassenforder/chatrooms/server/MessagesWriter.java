package fr.ensisa.hassenforder.chatrooms.server;

import java.io.OutputStream;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;

public class MessagesWriter extends BasicAbstractWriter {

	public MessagesWriter(OutputStream outputStream) {
		super(outputStream);
	}

	@Override
	protected void writeInt(int v) {
		// TODO Auto-generated method stub
		super.writeInt(v);
	}

	@Override
	protected void writeString(String v) {
		// TODO Auto-generated method stub
		super.writeString(v);
	}

}
