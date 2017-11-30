package Getraenkehandel.Logistikmanagement;
import java.util.LinkedList;
import java.util.List;
import org.salespointframework.order.OrderManager;
public class Calendermanagement {
	
	private List<Calendar> verfugbareBestellungen;
	private OrderManager ordermanager;
	
	public Calendermanagement(OrderManager ordermanager){
		this.ordermanager = ordermanager;
		this.verfugbareBestellungen = new LinkedList<Calendar>();
	}
	
	public OrderManager getOrdermanager(){
		return this.ordermanager;
	}
	public List<Calendar> getverfugbareBestellungen() {
		return verfugbareBestellungen;
	}

	public void deleteevent(List<Calendar> kalender) {
		kalender.remove(kalender);
		
	}
}
