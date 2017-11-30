package Getraenkehandel.Bestellungsmanagement;

import java.text.Normalizer.Form;
import java.util.LinkedList;
import java.util.List;

import org.salespointframework.order.OrderManager;
import org.springframework.stereotype.Component;

@Component
public class Bestellungsmanagement {
	
	private List<Bestellung> offeneBestellungen;
	private OrderManager ordermanager;
	
	public Bestellungsmanagement(OrderManager ordermanager){
		this.ordermanager = ordermanager;
		this.offeneBestellungen = new LinkedList<Bestellung>();
	}
	
	public OrderManager getOrdermanager(){
		return this.ordermanager;
	}

	public List<Bestellung> getOffeneBestellungen() {
		return offeneBestellungen;
	}

	public void setOffeneBestellungen(List<Bestellung> offeneBestellungen) {
		this.offeneBestellungen = offeneBestellungen;
	}

	public void setOrdermanager(OrderManager ordermanager) {
		this.ordermanager = ordermanager;
	}

	
}
