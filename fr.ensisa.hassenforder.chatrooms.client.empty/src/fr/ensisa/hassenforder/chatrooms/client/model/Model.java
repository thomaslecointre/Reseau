/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensisa.hassenforder.chatrooms.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hassenforder
 */
public class Model {

    private String name;
    private boolean connected;
    private List<Channel> channels;
    private List<Message> pendingMessages;
    private List<Message> incomingmessages;

    public String getName() {
        return name == null ? "" : name;
    }

    public boolean isConnected() {
        return connected;
    }

    public List<Channel> getChannels() {
        if (channels == null) channels = new ArrayList<>();
        return channels;
    }

    public List<Message> getPendingMessages() {
        if (pendingMessages == null) pendingMessages = new ArrayList<>();
        return pendingMessages;
    }

    public List<Message> getIncomingMessages() {
        if (incomingmessages == null) incomingmessages = new ArrayList<>();
        return incomingmessages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public void setPendingMessages(List<Message> pendingMessages) {
        this.pendingMessages = pendingMessages;
    }

    public void setIncomingmessages(List<Message> incomingmessages) {
        this.incomingmessages = incomingmessages;
    }
    
}
