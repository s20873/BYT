package models;

import interfaces.Mediator;
import interfaces.User;

public class ChatMediator extends Mediator {

    @Override
    public void send(String message, User sender) {
        for (User user: users) {
            if(!user.equals(sender)){
                user.notify(message);
            }
        }
    }

    public void addUser(User user){
        users.add(user);
    }
}
