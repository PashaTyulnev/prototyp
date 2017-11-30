package Getraenkehandel.Getränkemanagement;


import Getraenkehandel.Application;
import org.salespointframework.catalog.Catalog;
import org.salespointframework.catalog.ProductIdentifier;
import org.springframework.stereotype.Component;


import javax.persistence.Tuple;
import java.util.*;

/**
 * Created by Leon Röscher on 12.11.2017.
 */
@Component
//public interface Getränkekatalog extends Catalog<Artikel>{
public class Getränkekatalog implements Catalog<Artikel> {
    private List<Artikel> artikelliste = new ArrayList<>();

    public void delete(ProductIdentifier id) {
        Iterator<Artikel> ite = artikelliste.iterator();

        while (ite.hasNext()) {
            Artikel nextartikel = ite.next();
            if (nextartikel.getId() == id) {
                artikelliste.remove(nextartikel);
            }
        }
    }

    public Iterable<Artikel> findByName(String name) {
        List<Artikel> returnliste = new LinkedList<>();
        Iterator<Artikel> ite = artikelliste.iterator();

        while (ite.hasNext()) {
            Artikel nextartikel = ite.next();
            if (nextartikel.getName() == name) {
                returnliste.add(nextartikel);
            }
        }
        return returnliste;
    }

    public void delete(Artikel artikel) {
        artikelliste.remove(artikel);
    }

    public Optional<Artikel> findOne(ProductIdentifier id) {
        Optional<Artikel> returnartikel = null;
        Iterator<Artikel> ite = artikelliste.iterator();

        while (ite.hasNext()) {
            Artikel nextartikel = ite.next();
            if (nextartikel.getId() == id) {
                return returnartikel.of(nextartikel);
            }
        }
        return null;
    }

    public Iterable<Artikel> findAll(Iterable<ProductIdentifier> ids) {
        List<Artikel> returnliste = new LinkedList<>();
        Iterator<Artikel> ite = artikelliste.iterator();
        Iterator<ProductIdentifier> iteID = ids.iterator();
        while (ite.hasNext()) {
            Artikel nextartikel = ite.next();
            while (iteID.hasNext()) {
                ProductIdentifier id = iteID.next();
                if (nextartikel.getId() == id) {
                    returnliste.add(nextartikel);
                }
            }
        }
        return returnliste;
    }

    public boolean exists(ProductIdentifier id) {
        if (findOne(id) != null) {
            return true;
        }
        return false;
    }

    public void delete(Iterable<? extends Artikel> artikellist) {
        Iterator<? extends Artikel> ite = artikellist.iterator();

        while (ite.hasNext()) {
            artikelliste.remove(ite.next());
        }
    }

    public Iterable<Artikel> findByCategory(String categorie) {
        List<Artikel> returnliste = new LinkedList<>();
        Iterator<Artikel> ite = artikelliste.iterator();
        while (ite.hasNext()){
            Artikel nextartikel = ite.next();

            if (nextartikel.hasCategorie(categorie)){
                returnliste.add(nextartikel);
            }
        }
        return returnliste;
    }

    public <S extends Artikel>Iterable<S> save(Iterable<S> entities){
        Iterator<S> ite = entities.iterator();
       while (ite.hasNext()) {
           Artikel nextartikel = ite.next();
           if (!artikelliste.contains(nextartikel)) {
               artikelliste.add(ite.next());
           }
       }
    return entities; //Bitte Nur entities die hinzugefügt wurden returnen
    }

    public void deleteAll(){
        artikelliste.clear();
    }

    public Iterable<Artikel> findAll(){
        return artikelliste;
    }

    public <S extends Artikel>S save(S entity){
        artikelliste.add(entity);
        return entity; //Bitte Nur entity die hinzugefügt wurden returnen
    }

    public long count(){
        return artikelliste.toArray().length;
    }

    //*************************************************** Unser Katalog*****************************************************

    private List<Artikel> zugangsliste = new ArrayList<>();

    private Boolean zugang(ProductIdentifier id, Integer anzahl){   //Später zu Quantity
        if (findOne(id).isPresent()) {
            findOne(id).get().setAnzahl(findOne(id).get().getAnzahl() + anzahl);
            return true;
        }else {
            return false;
        }
    }

    private Boolean abgang(ProductIdentifier id, Integer anzahl){  //Später zu Quantity
        if (findOne(id).isPresent()) {
            if (findOne(id).get().getAnzahl() - anzahl >= Application.MINDESTANZAHL) {
                findOne(id).get().setAnzahl(findOne(id).get().getAnzahl() - anzahl);
                return true;
            } //Möglichkeit für Bestandswarnung
        }
    return false;
    }

    public Artikel getByIDString(ProductIdentifier id){
        Iterator<Artikel> ite = findAll().iterator();
        while (ite.hasNext()){
            Artikel nextartikel = ite.next();
            if (nextartikel.getId().getIdentifier().equals(id.getIdentifier())){
               return nextartikel;
            }
        }
    return null;
    }
}