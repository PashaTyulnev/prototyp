package Getraenkehandel.controller;

import Getraenkehandel.Personenmanagement.*;
import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.order.ChargeLine;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.payment.Cash;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static org.salespointframework.core.Currencies.EURO;

/**
 * Created by Leon Röscher on 14.11.2017.
 */
@Component
public class SetupInitializer implements DataInitializer {

    private final UserAccountManager userAccountManager;
    private final PersonenRepository personenRepository;
    private final AdressenRepository adressenRepository;
    private final PersonenManager personenManager;
    private final OrderManager<Order> orderManager;

    SetupInitializer(UserAccountManager userAccountManager, PersonenRepository personenRepository, AdressenRepository adressenRepository, PersonenManager personenManager, OrderManager<Order> orderManager) {

        Assert.notNull(personenRepository, "PersonenRepository darf nicht null sein!");
        Assert.notNull(userAccountManager, "UserAccountManager darf nicht null sein!");
        Assert.notNull(personenManager, "PersonenManager darf nicht null sein!");
        Assert.notNull(adressenRepository, "AdressenRepository darf nicht null sein!");

        this.userAccountManager = userAccountManager;
        this.personenRepository = personenRepository;
        this.adressenRepository = adressenRepository;
        this.personenManager = personenManager;
        this.orderManager = orderManager;
    }

    /*
     * (non-Javadoc)
     * @see org.salespointframework.core.DataInitializer#initialize()
     */
    @Override
    public void initialize() {
        // (｡◕‿◕｡)
        // UserAccounts bestehen aus einem Identifier und eine Password, diese werden auch für ein Login gebraucht
        // Zusätzlich kann ein UserAccount noch Rollen bekommen, diese können in den Controllern und im View dazu genutzt
        // werden
        // um bestimmte Bereiche nicht zugänglich zu machen, das "ROLE_"-Prefix ist eine Konvention welche für Spring
        // Security nötig ist.

        // Skip creation if database was already populated
        if (userAccountManager.findByUsername(PersonenUtils.usernameKonvention("Hugo", "Boss")).isPresent()) {
            System.out.println("Datenbank besteht bereits. Überspringe Initialisierung...");
            return;
        }

        //try {
            // Lege Testpersonen an
            String result = personenManager.anlegen("Niklas", "Reldeif", "ROLE_Kunde", "niklas@Email.de", "Dresden", "012345", "Hauptstraße", "4", "erster Stock", "0123456789");
            if (result != "")
                System.out.println(result);

            result = personenManager.anlegen("Max", "Mustermann", "ROLE_Kunde", "dasIstMeine1@Email.de", "Dresden", "012345", "Hauptstraße", "8c", "Mülltonne davor", "0123456789");
            if (result != "")
                System.out.println(result);

            result = personenManager.anlegen("Robert", "Holzkopf", "123", "ROLE_Lieferant", "dasIstMeine2@Email.de", "Dresden", "012345", "Hauptstraße", "8c", "Mülltonne davor", "0123456789", "10", "40");
            if (result != "")
                System.out.println(result);

            result = personenManager.anlegen("Hugo", "Boss", "123", "ROLE_Chef", "dasIst3Meine@Email.de", "Dresden", "012345", "Hauptstraße", "8c", "Mülltonne davor", "0123456789", "150", "30");
            if (result != "")
                System.out.println(result);

        //}
        //catch (Exception e) {

        //}

        //System.out.println(personenManager.zeigeAlleMitarbeiter().toString());
        //System.out.println(personenManager.zeigeAlleKunden().toString());

        Role customerRole = Role.of("ROLE_CUSTOMER");

        UserAccount ua1 = userAccountManager.create("hans", "123", customerRole);
        UserAccount ua2 = userAccountManager.create("dextermorgan", "123", customerRole);
        UserAccount ua3 = userAccountManager.create("earlhickey", "123", customerRole);
        UserAccount ua4 = userAccountManager.create("mclovinfogell", "123", customerRole);

        Order order1 = new Order(ua1, Cash.CASH);
        Order order2 = new Order(ua2,Cash.CASH);
        Order order3 = new Order(ua3,Cash.CASH);
        Order order4 = new Order(ua4,Cash.CASH);

        order1.add(new ChargeLine(Money.of(11000,EURO),"bla"));
        order2.add(new ChargeLine(Money.of(2000,EURO),"bla"));
        order3.add(new ChargeLine(Money.of(3000,EURO),"bla"));
        order4.add(new ChargeLine(Money.of(4000,EURO),"bla"));

        System.out.println(order1.getTotalPrice());


        orderManager.save(order1);
        orderManager.save(order2);
        orderManager.save(order3);
        orderManager.save(order4);

        orderManager.payOrder(order1);
        orderManager.payOrder(order2);
        orderManager.payOrder(order3);
        orderManager.payOrder(order4);
    }
}
