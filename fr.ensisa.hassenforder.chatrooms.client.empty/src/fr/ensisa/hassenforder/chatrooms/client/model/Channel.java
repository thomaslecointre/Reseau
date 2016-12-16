package fr.ensisa.hassenforder.chatrooms.client.model;

/**
 *
 * @author Hassenforder
 */
public class Channel {
    private String name;
    private ChannelType type;
    private String moderator;
    private boolean subscribed;

    public Channel() {
    }

    public Channel(String name, ChannelType type, String moderator, boolean subscribed) {
        this.name = name;
        this.type = type;
        this.moderator = moderator;
        this.subscribed = subscribed;
    }

    public String getName() {
        return name;
    }

    public ChannelType getType() {
        return type;
    }

    public String getModerator() {
        return moderator;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public String toString() {
        return "Channel{" + "name=" + name + ", type=" + type + ", subscribed=" + subscribed + '}';
    }

}
