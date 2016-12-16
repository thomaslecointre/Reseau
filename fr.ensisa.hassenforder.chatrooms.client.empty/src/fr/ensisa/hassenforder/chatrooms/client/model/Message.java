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
public class Message {
    final private String channel;
    final private int id;
    final private String author;
    final private String text;

    public Message(String channel, int id, String author, String text) {
        this.channel = channel;
        this.id = id;
        this.author = author;
        this.text = text;
    }

    public String getChannel() {
        return channel;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Message{" + "channel=" + channel + ", id=" + id + ", author=" + author + ", text=" + text + '}';
    }
    
    
}
