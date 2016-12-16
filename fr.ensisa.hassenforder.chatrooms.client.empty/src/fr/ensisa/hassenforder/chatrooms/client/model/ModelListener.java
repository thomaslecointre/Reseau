/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensisa.hassenforder.chatrooms.client.model;

/**
 *
 * @author Hassenforder
 */
public interface ModelListener {
    
    public void updateStatus (String status);
    
    public void updateConnection (Model model, String status);
    
    public void updateChannelCreated (Model model, String status);

    public void updateChannelList (Model model, String status);

    public void updateSubscribtionChange (Model model, String status);

    public void updateModerationList (Model model, String status);

    public void updateModerationState(Model model, String subscribtion_changed);

    public void updateMessageSent (Model model, String status);

    public void updateMessageList (Model model, String status);

}
