package storeProject;

import java.util.ArrayList;
import java.util.List;

public class Market implements MarketBehavior, QueueBehavoir {

    private int countFinishBuyers = 0;
    private int countLoose = 0;
    private List<Actor> actors = new ArrayList<>();
    private List<Actor> inQueue = new ArrayList<>();
    @Override
    public void acceptToMarket(Actor actor) {
        actors.add(actor);
        System.out.println(">>> В магазин зашел покупатель " + actor.getName());
        if (actors.size() > 7) {
            System.out.println("<<< Покупателя " + actor.getName() + " испугала загруженность рынка и он ушел.");
            countLoose++;
            actors.remove(actor);
        }
    }

    @Override
    public void releaseFromMarket(Actor actor) {
        actors.remove(actor);
        System.out.println("<<< Покупалель " + actor.getName() + " радостный выходит с рынка.");
        countFinishBuyers++;
    }

    @Override
    public void update() {
        for (int i = 0; i < actors.size(); i++) {
            if (actors.get(i).isMakeOrder && !actors.get(i).isTakeOrder){
                takeInQueue(actors.get(i));
            } else if (actors.get(i).isMakeOrder && actors.get(i).isTakeOrder){
                releaseFromQueue(actors.get(i));
            }
        }

    }

    @Override
    public void takeInQueue(Actor actor) {
        if (!inQueue.contains(actor))
            inQueue.add(actor);
    }

    @Override
    public void takeOrders(String txt) {
        System.out.println(txt);
    }

    @Override
    public void giveOrders() {
        Actor buyer = inQueue.get(0);
        buyer.setTakeOrder(true);
        System.out.println("Покупатель " + buyer.getName() + " забирает свой заказ.");
        releaseFromQueue(buyer);
        releaseFromMarket(buyer);
    }

    @Override
    public void releaseFromQueue(Actor actor) {
        inQueue.remove(actor);
    }

    public int getNumberCustomers() {
        return actors.size();
    }
    public Actor getCustomers(int position){
        return actors.get(position);
    }
    public int getNumberQueue() { return inQueue.size(); }
    public void finishDay(){
        System.out.println("\n\nРабочий день подошел к концу." +
                "\nЗа смену было обслужено " + countFinishBuyers + " покупателей." +
                "\nПотеряно из-за больших очередей " + countLoose +
                "\nНе успели обслужить и пришлось выгнать " + actors.size());
        inQueue.clear();
        actors.clear();
    }
}
