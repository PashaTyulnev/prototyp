package Getraenkehandel.finanzmanagement;

import Getraenkehandel.Personenmanagement.Mitarbeiter;
import Getraenkehandel.Personenmanagement.Person;
import Getraenkehandel.Personenmanagement.PersonenManagement;
import org.javamoney.moneta.Money;
import org.salespointframework.accountancy.Accountancy;
import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.accountancy.ProductPaymentEntry;
import org.salespointframework.core.Streamable;
import org.salespointframework.order.ChargeLine;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.payment.Cash;
import org.salespointframework.time.BusinessTime;
import org.salespointframework.time.Interval;
import org.salespointframework.time.Intervals;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.stereotype.Service;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.*;


import static org.salespointframework.core.Currencies.EURO;


/**
 * Created by An on 13.11.2017.
 */
@Service
@Transactional
public class Finanzen {

    private final Accountancy buchhaltung;
    private final BusinessTime businessTime;
    private final OrderManager<Order> orderManager;
    private final UserAccountManager userAccountManager;
    private final List<MonetaryAmount> mitarbeiter; // vom Typ MitarbeiterRepository // PersonenRepository
    private final Map<LocalDateTime, MonetaryAmount> ausgabenliste;
    private final Map<LocalDateTime, MonetaryAmount> gesAusgabenliste;


    Finanzen(Accountancy buchhaltung, BusinessTime businessTime, OrderManager orderManager, UserAccountManager userAccountManager){
        this.userAccountManager = userAccountManager;
        this.orderManager = orderManager;
        this.businessTime = businessTime;
        this.buchhaltung = buchhaltung;


        mitarbeiter = new LinkedList<>();
        mitarbeiter.add(Money.of(1600,EURO));
        mitarbeiter.add(Money.of(5000,EURO));
        mitarbeiter.add(Money.of(2000,EURO));
        mitarbeiter.add(Money.of(1400,EURO));

        System.out.println(mitarbeiter);

        ausgabenliste = new TreeMap<>();
        gesAusgabenliste = new TreeMap<>();

    }


    public Map<LocalDateTime,MonetaryAmount> monatlicheEinnahmenBerechnen() {

        LocalDateTime startDatum = LocalDateTime.of(businessTime.getTime().getYear(),businessTime.getTime().getMonth(),1,0,0,0,0);
        startDatum = startDatum.minusYears(1);

        Interval.IntervalBuilder intervalB = Interval.from(startDatum);
        Interval interval = intervalB.to(businessTime.getTime());

        Map<Interval,MonetaryAmount> monatliEinnahmen = buchhaltung.salesVolume(interval, Duration.ofDays(30));

        Map revMonatliEinnahmen = new TreeMap(Collections.reverseOrder());

        for(Interval inv : monatliEinnahmen.keySet()){
            revMonatliEinnahmen.put(inv.getEnd().withHour(0).withMinute(0).withSecond(0).withNano(0),monatliEinnahmen.get(inv));
        }

        System.out.println(revMonatliEinnahmen);

        for (AccountancyEntry entry : buchhaltung.findAll()){
            System.out.println(entry.getDate());
        }

        return revMonatliEinnahmen;
    }

    public Map<LocalDateTime,MonetaryAmount> jährlicheEinnahmenBerechnen() {

        LocalDateTime loc = LocalDateTime.of(businessTime.getTime().getYear(),1,1,0,0,0,0);
        loc = loc.minusYears(10);
        Interval.IntervalBuilder bla = Interval.from(loc);
        Interval bl2 = bla.to(businessTime.getTime());
        Map<Interval,MonetaryAmount> bl3 = buchhaltung.salesVolume(bl2, Duration.ofDays(365));

        Map treeMap = new TreeMap(Collections.reverseOrder());



        for(Interval inv : bl3.keySet()){
            treeMap.put(inv.getEnd().withHour(0).withMinute(0).withSecond(0).withNano(0),bl3.get(inv));
        }

        return treeMap;
    }

    public void addTime(int time) {
        businessTime.forward(Duration.ofDays(time));
    }


        /*Role customerRole = Role.of("ROLE_CUSTOMER");

        UserAccount ua1 = userAccountManager.create("hans2", "123", customerRole);
        UserAccount ua2 = userAccountManager.create("dextermorgan2", "123", customerRole);
        UserAccount ua3 = userAccountManager.create("earlhickey2", "123", customerRole);
        UserAccount ua4 = userAccountManager.create("mclovinfogell2", "123", customerRole);

        Order order1 = new Order(ua1, Cash.CASH);
        Order order2 = new Order(ua2,Cash.CASH);
        Order order3 = new Order(ua3,Cash.CASH);
        Order order4 = new Order(ua4,Cash.CASH);

        order1.add(new ChargeLine(Money.of(100,EURO),"bla"));
        order2.add(new ChargeLine(Money.of(40,EURO),"bla"));
        order3.add(new ChargeLine(Money.of(500,EURO),"bla"));
        order4.add(new ChargeLine(Money.of(600,EURO),"bla"));

        System.out.println(order1.getTotalPrice());


        orderManager.save(order1);
        orderManager.save(order2);
        orderManager.save(order3);
        orderManager.save(order4);

        orderManager.payOrder(order1);
        orderManager.payOrder(order2);
        orderManager.payOrder(order3);
        orderManager.payOrder(order4);*/

    //}

    public MonetaryAmount gesamtGehaltberechnen() {
        MonetaryAmount gesGehalt = Money.of(0, EURO) ;

        for (MonetaryAmount money : mitarbeiter){
            gesGehalt = gesGehalt.add(money);
        }

        return gesGehalt;
    }


    public Map<LocalDateTime,MonetaryAmount> ausgabenBerechnen(){                      //noch in Monaten einteilen
        MonetaryAmount ausgaben = Money.of(0,EURO);

        Map ausMap = new TreeMap(Collections.reverseOrder());
        for (Interval interv : Intervals.divide(monatInvtervale(),Duration.ofDays(30))){
            ausgaben = Money.of(0,EURO);
            for (LocalDateTime l: ausgabenliste.keySet()) {
                if (interv.contains(l)) {
                    ausgaben = ausgaben.add(ausgabenliste.get(l));
                }
            }
            ausMap.put(interv.getEnd().withHour(0).withMinute(0).withSecond(0).withNano(0),ausgaben);
        }
        return ausMap;
    }

    public boolean ausgabenHinzufügen(float i){
            ausgabenliste.put(businessTime.getTime(),Money.of(i,EURO));
            return true;
    }


    public Map<LocalDateTime,MonetaryAmount> gesamtAusgabenBerechnen(){                      //noch in Monaten einteilen

        Map gesAusMap = new TreeMap(Collections.reverseOrder());

        for (LocalDateTime loc : ausgabenBerechnen().keySet()){
            gesAusMap.put(loc, ausgabenBerechnen().get(loc).add(gesamtGehaltberechnen()));
        }
        return gesAusMap;
    }

    public Map monatlichenUmsatzBerechnen() {

        Map<LocalDateTime, MonetaryAmount> umsMap = new TreeMap(Collections.reverseOrder());

        MonetaryAmount umsatz = Money.of(0, EURO);

        for (Interval interv: Intervals.divide(monatInvtervale(), Duration.ofDays(30))) {
            umsatz = Money.of(0, EURO);
            for (LocalDateTime einLoc : monatlicheEinnahmenBerechnen().keySet()) {
                if (interv.contains(einLoc.minusHours(1))) {
                    umsatz = umsatz.add(monatlicheEinnahmenBerechnen().get(einLoc));
                    System.out.println("einnahmen" + umsatz);
                }
            }

            for (LocalDateTime ausLoc : gesamtAusgabenBerechnen().keySet()){
                if(interv.contains(ausLoc.minusHours(1))) {
                    umsatz = umsatz.add(gesamtAusgabenBerechnen().get(ausLoc).negate());
                    System.out.println("ausgaben" + umsatz);
                        }
                    }
                    umsMap.put(interv.getEnd(), umsatz);
                }

        return umsMap;
    }

    public Interval monatInvtervale() {

        LocalDateTime startDatum = LocalDateTime.of(businessTime.getTime().getYear(),businessTime.getTime().getMonth(),1,0,0,0,0);
        startDatum = startDatum.minusYears(1);

        Interval.IntervalBuilder intervalB = Interval.from(startDatum);
        Interval interval = intervalB.to(businessTime.getTime());

        return interval;
    }

}
