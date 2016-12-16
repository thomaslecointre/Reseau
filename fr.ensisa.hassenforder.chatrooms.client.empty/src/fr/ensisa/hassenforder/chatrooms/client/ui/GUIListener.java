/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensisa.hassenforder.chatrooms.client.ui;

import fr.ensisa.hassenforder.chatrooms.client.model.Channel;
import fr.ensisa.hassenforder.chatrooms.client.model.ChannelType;
import fr.ensisa.hassenforder.chatrooms.client.model.Message;

/**
 *
 * @author Hassenforder
 */
public interface GUIListener {

    public void notifyChannelSubscriptionChange(Channel channel, boolean selected);

    public void notifyModerationState(Message message, boolean approved);

    public void notifyConnection(String name);

    public void notifyChannelCreation(String name, ChannelType type);

    public void notifyChannelLoadAll();

    public void notifyClearMessages();

    public void notifySendMessage(String channelName, String text);

    public void notifyMessagesLoad();

    public void notifyModerationsLoad();
    
}
