package handlers;

import models.Request;

abstract class Handler{
    private Handler successor;
    public void setSuccessor(Handler successor){
        this.successor = successor;
    }
    public abstract void handleRequest(Request request);

    public Handler getSuccessor() {
        return successor;
    }
}
