package interfaces;

import java.util.ArrayList;

public abstract class Mediator {
    protected ArrayList<User> users;

    public Mediator(){
        users = new ArrayList<>();
    }
    public abstract void send(String message, User user);
}
