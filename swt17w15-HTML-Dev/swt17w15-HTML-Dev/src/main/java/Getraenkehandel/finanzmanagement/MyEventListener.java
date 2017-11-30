package Getraenkehandel.finanzmanagement;

import org.salespointframework.order.Order;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Created by An on 14.11.2017.
 */

//@Component
public class MyEventListener {

    //@EventListener
    public void handleEventAfterTransaction(Order.OrderCompleted event) {
        System.out.println("Halloooooo");
    }
}
