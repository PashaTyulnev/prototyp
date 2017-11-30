package Getraenkehandel.Bestellungsmanagement;
import Getraenkehandel.Application;

import java.util.Date;

import javax.money.MonetaryAmount;

import org.salespointframework.order.Order;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.useraccount.UserAccount;

public class Bestellung extends Order {
	
	private String liefertermin;
	private boolean event;
	private String lieferadresse;
	private Warenkorb warenkorb;
	private OrderStatus status;
	private String kunde;
	private MonetaryAmount price;
	
	public Bestellung(Warenkorb warenkorb){
		this.warenkorb = warenkorb;
	}
	
	public void setLieferadresse(String a1){
		this.lieferadresse = a1;
	}
	
	public OrderStatus getOrderStatus(){
		return this.status;
	}
	
	public boolean inAuftragGeben(){
		this.status = OrderStatus.OPEN;
		return true;
	}

	public String getLiefertermin() {
		return liefertermin;
	}

	public void setLiefertermin(String liefertermin) {
		this.liefertermin = liefertermin;
	}

	public boolean isEvent() {
		return event;
	}

	public void setEvent(boolean event) {
		this.event = event;
	}

	public Warenkorb getWarenkorb() {
		return warenkorb;
	}

	public void setWarenkorb(Warenkorb warenkorb) {
		this.warenkorb = warenkorb;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getKunde() {
		return kunde;
	}

	public void setKunde(String kunde) {
		this.kunde = kunde;
	}

	public String getLieferadresse() {
		return lieferadresse;
	}

	public MonetaryAmount getPrice() {
		return price;
	}

	public void setPrice(Warenkorb warenkorb) {
		this.price = warenkorb.getKV();
	}

}
