import models.ChatMediator;
import models.ChatUser;

//mediator pattern
public class Main {
    public static void main(String[] args) {
        ChatMediator mediator = new ChatMediator();

        ChatUser user1 = new ChatUser(mediator, "Alisa");
        ChatUser user2 = new ChatUser(mediator, "Max");
        ChatUser user3 = new ChatUser(mediator, "Vladik");

        mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);

        user1.send("hi everyone!");

        ChatUser user4 = new ChatUser(mediator, "Sonia");
        mediator.addUser(user4);
        user4.send("hello, I'm new one");


    }
}
