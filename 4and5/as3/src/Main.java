import models.Server;
import models.ServerPool;

//object pool pattern
public class Main {
    public static void main(String[] args) {
        ServerPool pool = new ServerPool();

        Server server1 = pool.use("req1");
        Server server2 = pool.use("req2");
        pool.release(server2);
        pool.release(server1);
        Server server3 = pool.use("req3");
        Server server4 = pool.use("req4");
        Server server5 = pool.use("req5");
        pool.release(server3);
        pool.release(server4);

    }
}
