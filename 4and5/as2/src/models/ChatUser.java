package models;

import interfaces.Mediator;
import interfaces.User;

public class ChatUser extends User {

    public ChatUser(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    public void send(String message) {
        System.out.println("\t*" + name + " sent a message");
        mediator.send(message, this);
    }

    @Override
    public void notify(String message) {
        System.out.println(name + " got a message: \"" + message + "\"");
    }
}
