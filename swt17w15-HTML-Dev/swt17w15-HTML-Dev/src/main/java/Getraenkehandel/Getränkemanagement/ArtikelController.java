package Getraenkehandel.Getränkemanagement;

import org.javamoney.moneta.Money;
import org.salespointframework.catalog.ProductIdentifier;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.salespointframework.core.Currencies.*;

/**
 * Created by Leon Röscher on 12.11.2017.
 */
@Controller
public class ArtikelController {

    private final Getränkekatalog catalog;

    private final MessageSourceAccessor messageSourceAccessor;

    public ArtikelController(Getränkekatalog catalog, MessageSource messageSource){
        this.catalog = catalog;
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }


    @GetMapping("/katalog")
    String katalog(Model model) {

        model.addAttribute("katalog", catalog.findAll());
        model.addAttribute("title", messageSourceAccessor.getMessage("catalog.title"));
        return "katalog";
    }

    @GetMapping("/lager")
    @PreAuthorize("hasRole('ROLE_Chef')")
    String lager(Model model) {

        model.addAttribute("lager", catalog.findAll());

        return "lager";
    }


    @GetMapping("/neuerArtikel")
    @PreAuthorize("hasRole('ROLE_Chef')")
    String neuerArtikel( Model model){



        return "neuerArtikel";
    }

    @PostMapping("/artikelErstellen")
    @PreAuthorize("hasRole('ROLE_Chef')")
    String artikelErstellen(@RequestParam("name") String name, @RequestParam("ek") Double einkaufspreis,@RequestParam("beschreibung")String beschreibung,@RequestParam("anfangsbestand") Integer bestand,@RequestParam("kategorie")String kat, Model model){
        ProductIdentifier id;

        id = catalog.save(new Artikel(name, Money.of(einkaufspreis,EURO))).getId();

        if (catalog.findOne(id).isPresent()) {
            if (!beschreibung.isEmpty()) {
                catalog.findOne(id).get().setBeschreibung(beschreibung);
            }

            if (bestand != null){
                catalog.findOne(id).get().setAnzahl(bestand);
            }

            if (!kat.isEmpty()){
                catalog.findOne(id).get().addCategory(kat);
            }


        }
        return "neuerArtikel";
    }

    @PostMapping("/artikelSuchen")
    String artikelSuchen(@RequestParam("suche")String kat,Model model){
        model.addAttribute("katalog", catalog.findByCategory(kat));
        return "katalog";
    }

    @GetMapping("/artikel/{id}")
    String ansicht(@PathVariable("id") ProductIdentifier id,Model model){

        Iterator<Artikel> ite = catalog.findAll().iterator();
        while (ite.hasNext()){
           Artikel nextelement = ite.next();
            if (nextelement.getId().getIdentifier().equals(id.getIdentifier())){
                model.addAttribute("artikel",nextelement);
                break;
            }
        }
        return "ansicht";
    }

    @PostMapping("/aufstocken")
    String aufstocken(@RequestParam("artikel")ProductIdentifier id,@RequestParam("aufstocken") Integer number,Model model) {

        Iterator<Artikel> ite = catalog.findAll().iterator();
        while (ite.hasNext()){
            Artikel nextartikel = ite.next();
            if (nextartikel.getId().getIdentifier().equals(id.getIdentifier())){
                nextartikel.setAnzahl(nextartikel.getAnzahl()+number);
                break;
            }
        }

    return "redirect:/artikel/"+id;
    }
}
