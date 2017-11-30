package Getraenkehandel.Getränkemanagement;

import Getraenkehandel.Application;
import org.salespointframework.catalog.Product;
import org.salespointframework.quantity.Metric;
import org.salespointframework.quantity.Quantity;
import org.springframework.stereotype.Component;

import javax.money.MonetaryAmount;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Iterator;

/**
 * Created by Leon Röscher on 12.11.2017.
 */
@Entity
public class Artikel extends Product {

    //Dies ist um Git zu testen...

    private Integer anzahl; //Später zu Quantity
    private String beschreibung;
    private Integer einkaufspreis; //Später zu MonetaryAmount

    public Artikel(String name,MonetaryAmount einkaufspreis,String beschreibung){
        super(name,einkaufspreis.add(einkaufspreis.multiply(Application.GEWINNPAUSCHALE)), Metric.UNIT);
        this.anzahl = 0;
        this.beschreibung = beschreibung;
    }

    public Artikel(String name,MonetaryAmount einkaufspreis){
        super(name,einkaufspreis.add(einkaufspreis.multiply(Application.GEWINNPAUSCHALE)), Metric.UNIT);
        this.anzahl = 0;
        this.beschreibung = "Keine Beschreibung vorhanden!";
    }

    public Artikel(String name){
        super(name,null);
        this.anzahl = 0;
        this.beschreibung = "Keine Beschreibung vorhanden!";
    }

    public Integer getAnzahl(){
        return this.anzahl;
    }

    public Boolean setAnzahl(Integer anzahl){
        this.anzahl = anzahl;
        return true; //Änderbar
    }

    public String getBeschreibung(){
        return beschreibung;
    }

    public Boolean setBeschreibung(String beschreibung){
        this.beschreibung = beschreibung;
        return true; //Änderbar
    }

    public Boolean setEinkaufspreis(Integer ep){
        this.einkaufspreis = ep;
        return true; //Änderbar
    }

    public Integer getEinkaufspreis(){
        return einkaufspreis;
    }

    public Boolean hasCategorie(String kat){
        Iterator<String> ite = getCategories().iterator();

        while (ite.hasNext()){
            if (ite.next().equals(kat)){
                return true;
            }
        }

        return false;
    }

    public String categoriesToString(){
        Iterator<String> ite = getCategories().iterator();
        String returnstring = "";
        while (ite.hasNext()){
            returnstring = returnstring + ", "+ite.next();
        }
        return returnstring;
    }

}
