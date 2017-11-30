package Getraenkehandel.Bestellungsmanagement;

import java.util.Iterator;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.payment.Cash;
import org.salespointframework.useraccount.UserAccount;

public class Warenkorb extends Cart {
	
	private MonetaryAmount kostenvoranschlag = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0).create();

	public Warenkorb(){
		
	}
	
	public MonetaryAmount getKV(){
		MonetaryAmount no = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0).create();
		UserAccount account = new UserAccount();
		if(this.isEmpty() == true){
			return no;
		}
		Order order = new Order(account, Cash.CASH);
		this.addItemsTo(order);
		return order.getTotalPrice();
	}
	
	
}
