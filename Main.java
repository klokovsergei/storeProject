package storeProject;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Market market = new Market();

        System.out.println("Сколько минут вы хотите наблюдать за Рынком: ");
        Scanner console = new Scanner(System.in);
        int diffMinutes;

        while (!console.hasNextInt()){
            System.out.println("Введите целое число (минут): ");
        }
        diffMinutes = console.nextInt();


        Date firstTime = new Date(); //старое время в миллисекундах
        Date secondTime = new Date(); //текущее время

        Random random = new Random();
        int i = 1;
        while ((secondTime.getTime() - firstTime.getTime())/1000 < diffMinutes * 60){
            switch (random.nextInt(4)){
                case 1 -> { //в магазин заходит новый покупатель
                    market.acceptToMarket(new Human("Customer" + i++));
                }
                case 2 -> { //случайный покупатель делает заказ
                    if (market.getNumberCustomers() > 0) {
                        int position = random.nextInt(market.getNumberCustomers());
                        if (!market.getCustomers(position).isMakeOrder) {
                            market.getCustomers(position).setMakeOrder(true);
                            market.takeOrders("Покупатель " + market.getCustomers(position).getName() + " делает заказ.");
                        }
                    } else
                        System.out.println("В магазине пока нет покупателей. Никто ничего не заказывает.");
                }
                case 3 -> { //получение заказа
                    if (market.getNumberQueue() > 0) {
                        market.giveOrders();
                    } else
                        System.out.println("В магазине пока нет заказов. Нечего выдавать.");
                }
            }
            market.update();

            Thread.sleep(1_000);
            secondTime = new Date();
        }
        market.finishDay();
    }

}
