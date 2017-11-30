package Getraenkehandel.Getränkemanagement;

import static org.salespointframework.core.Currencies.*;

import org.javamoney.moneta.Money;
import org.salespointframework.core.DataInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by Leon Röscher on 13.11.2017.
 */
@Component
class GetränkeInitializer implements DataInitializer{

    private final Getränkekatalog getränkekatalog;

    GetränkeInitializer(Getränkekatalog getränkekatalog){
        Assert.notNull(getränkekatalog,"Getränkekatalog darf nicht NULL sein");

        this.getränkekatalog = getränkekatalog;
    }

    @Override
    public void initialize(){
        Artikel a1 = new Artikel("Cola",Money.of(1.50,EURO));
        a1.setBeschreibung("Der Klassiker Coca Cola als 250ml Dose.");
        a1.addCategory("Alkoholfrei");

        getränkekatalog.save(a1);
        getränkekatalog.save(new Artikel("Fanta",Money.of(1.20,EURO)));

    }
}
