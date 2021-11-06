package models;

import java.util.Locale;
import java.util.Random;

public class Server {
    private int id;
    private static int counter = 1;

    public Server(){
        id = counter++;
    }

    @Override
    public String toString() {
        return "Server_" + id;
    }
}
