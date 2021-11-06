package models;

import interfaces.ObjectPool;

public class ServerPool extends ObjectPool<Server> {
    @Override
    protected Server newObject() {
        return new Server();
    }
}
