package storeProject;

public interface QueueBehavoir {
    void takeInQueue(Actor actor);
    void takeOrders(String txt);
    void giveOrders();
    void releaseFromQueue(Actor actor);
}
