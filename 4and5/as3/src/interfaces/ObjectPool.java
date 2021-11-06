package interfaces;

import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public abstract class ObjectPool<T> {
    private Set<T> available = new HashSet<>();
    private Set<T> inUse = new HashSet<>();

    protected abstract T newObject();

    public synchronized T use(String request){
        int random = (int) ((new Random().nextDouble())*4000);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(available.isEmpty()){
            available.add(newObject());
        }

        T object = available.iterator().next();
        available.remove(object);
        inUse.add(object);

        System.out.println("\t" + object.toString() + " responded to " + request.toUpperCase(Locale.ROOT));
        System.out.println(toString());
        return object;
    }

    public synchronized void release(T object){
        inUse.remove(object);
        available.add(object);

        System.out.println("\t" + object.toString() + " was released");
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "available=" + available.size() +
                ", inUse=" + inUse.size();
    }
}
