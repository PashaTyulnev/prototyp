package Getraenkehandel.Personenmanagement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Adresse {

    private @Id @GeneratedValue long id; // DB Primärschlüssel

    private String stadt;
    private String plz;
    private String nr;
    private String zusatz;
    private String strasse;
    private String zone;

    private Adresse() {}

    public Adresse(String stadt, String plz, String strasse, String nr, String zusatz) {
        this.stadt = stadt;
        this.plz = plz;
        this.strasse = strasse;
        this.nr = nr;
        this.zusatz = zusatz;

        ermittleZone();
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getZusatz() {
        return zusatz;
    }

    public void setZusatz(String zusatz) {
        this.zusatz = zusatz;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getZone() {
        return zone;
    }

    private void ermittleZone() {
        //TODO: Funktionalität
    }

    @Override
    public String toString() {
        return plz + " " + stadt + " " + strasse + " " + nr + " " + zusatz;
    }

    public boolean equals(Adresse adresse) {
        return (    adresse.getStadt() == this.getStadt() &&
                    adresse.getStrasse() == this.getStrasse() &&
                    adresse.getPlz() == this.getPlz() &&
                    adresse.getNr() == this.getNr() );
    }
}
