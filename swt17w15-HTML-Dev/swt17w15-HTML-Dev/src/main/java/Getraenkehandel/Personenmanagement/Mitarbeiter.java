package Getraenkehandel.Personenmanagement;

import org.javamoney.moneta.Money;
import org.salespointframework.core.Currencies;
import org.salespointframework.useraccount.UserAccount;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.persistence.Entity;
import java.util.Currency;

@Entity
public class Mitarbeiter extends Person {

    private float stundenlohn;
    private int wochenstunden;

    @SuppressWarnings("unused")
    private Mitarbeiter() {}

    public Mitarbeiter(UserAccount userAccount, Adresse adresse, float stundenlohn, int wochenstunden) {
        super(userAccount, adresse);
        this.stundenlohn = stundenlohn;
        this.wochenstunden = wochenstunden;
    }

    public float getStundenlohn() {
        return this.stundenlohn;
    }

    public void setStundenlohn(float stundenlohn) {
        this.stundenlohn = stundenlohn;
    }

    public int getWochenstunden() {
        return this.wochenstunden;
    }

    public void setWochenstunden(int wochenstunden) {
        this.wochenstunden = wochenstunden;
    }

    @Override
    public String toString() {
        return "|" + super.getId() + "|" + super.getUserAccount().getFirstname() + "|" + super.getUserAccount().getLastname() + "|Rolle: " + getRolle().toString() + "|Adresse: " + super.getAdresse().toString() + "|" + "Stundenlohn: " + getStundenlohn() + "|" + "Wochenstunden: " + getWochenstunden();
    }
}
