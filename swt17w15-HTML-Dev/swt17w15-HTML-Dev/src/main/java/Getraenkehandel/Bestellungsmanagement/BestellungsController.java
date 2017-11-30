package Getraenkehandel.Bestellungsmanagement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Stack;

import javax.money.Monetary;

import org.salespointframework.catalog.Product;
import org.salespointframework.catalog.ProductIdentifier;
import org.salespointframework.core.Streamable;
import org.salespointframework.order.Cart;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import Getraenkehandel.Getränkemanagement.Artikel;
import Getraenkehandel.Getränkemanagement.Getränkekatalog;
import Getraenkehandel.Personenmanagement.Person;
import Getraenkehandel.Personenmanagement.PersonenManager;

@Controller
public class BestellungsController {
	
	private Warenkorb warenkorb;
	private Streamable<Person> kundenliste;
	private String kunde = "dummy";
	private boolean failed = false;
	private final Bestellungsmanagement bestellungsmanagement;
	private final PersonenManager personenManager;
	private Getränkekatalog katalog;
	String event;
	
	public BestellungsController(Bestellungsmanagement bm, PersonenManager personenManager, Getränkekatalog katalog){
		this.bestellungsmanagement = bm;
		this.katalog = katalog;
		Assert.notNull(personenManager, "CustomerManagement must not be null!");
		this.personenManager = personenManager;
		this.kundenliste = personenManager.zeigeAlleKunden();
		System.out.println(kundenliste);
		warenkorb = new Warenkorb();
	}
	
	@GetMapping("/warenkorb")
	String warenkorb(@RequestParam Optional<String> name, Model model){
		model.addAttribute("kostenvoranschlag", this.warenkorb.getKV());
		return "warenkorb";
	}
	
	@GetMapping("/form")
	String formular(@RequestParam Optional<String> name, Model model){
		if(failed == true){
			model.addAttribute("failed","Bei der Erstellung der Bestellung ist ein Fehler aufgetreten. Bitte versuchen Sie es erneut.");
			this.failed = false;
		}
		model.addAttribute("list", this.personenManager.zeigeAlleKunden());
		System.out.println("HUHU" + this.personenManager.zeigeAlleKunden());
		return "form";
	}
	
	@RequestMapping(value="/foo", method=RequestMethod.POST)
	public String recoverData(@RequestParam("adresse") String adresse,@RequestParam("termin") String termin, @RequestParam("event") boolean check, @RequestParam("kunde") String kunde) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
		DateFormat format2 = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
		Date date = format.parse(termin);
		String termin2 = format2.format(date);
		System.out.println(date);
		if(adresse != ""){
			System.out.println("Neue Bestellung an " + adresse + " bis zum " + termin.toString() + " Event? " + check + " Kunde:" + kunde);
			Bestellung bestellung = new Bestellung(this.warenkorb);
			bestellung.setLieferadresse(adresse);
			bestellung.setLiefertermin(termin2);
			bestellung.setEvent(check);
			bestellung.setStatus(OrderStatus.OPEN);
			bestellung.setKunde(kunde);
			bestellung.setPaymentMethod(Cash.CASH);
			bestellung.setPrice(this.warenkorb);
			bestellungsmanagement.getOffeneBestellungen().add(bestellung);
			this.warenkorb.clear();
			return "redirect:/warenkorb";
		}
		this.failed=true;
		return "redirect:/form";
	}
	
	@GetMapping("/bestellungen")
	String orders(@RequestParam Optional<String> name, Model model){
		return "bestellungen";
	}
	
	@ModelAttribute("bestellungen")
	List<Bestellung> initializeBestellungen() {
		return this.bestellungsmanagement.getOffeneBestellungen();
	}
	
	@PostMapping("/hinzufuegenWarenkorb")
	String add(@RequestParam("artikel")ProductIdentifier id, @RequestParam("hinzu") Integer number, Model model){
		Artikel artikel = katalog.getByIDString(id); //gibt dir den Artikel für den Warenkorb
		Quantity anzahl = Quantity.of(number); //gibt dir die Anzahl
		
		this.warenkorb.addOrUpdateItem(artikel, anzahl);
		System.out.println(artikel.getName()+"  "+number); //Zum Test
		return "redirect:/katalog"; //geht wieder auf ansicht des Artikels
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String deleteAuswahl(@RequestParam("kunde") List<String> auswahl) {
		for(int i = 0; i<auswahl.size(); i++){
			if(auswahl.get(i)!="false") {
				this.warenkorb.removeItem(auswahl.get(i));
			}
		}
		return "redirect:/warenkorb";
	}
	
	@RequestMapping(value="/bar", method=RequestMethod.POST)
	public String recoverPerson(@RequestParam("item") String person) {
		this.kunde = person;
	    return "redirect:form";
	}
	
	@ModelAttribute("cart")
	Cart initializeCart() {
		return warenkorb;
	}
	
	@ModelAttribute("date")
	String getTodaysDate() {
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
		Date date = new Date();
		String x = dateFormat.format(date);
		System.out.println("Todays date" + dateFormat.format(date));
		return x;
	}
	
}
