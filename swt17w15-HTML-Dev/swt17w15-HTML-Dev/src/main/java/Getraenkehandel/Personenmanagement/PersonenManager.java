/**
 * Der PersonenManager arbeitet mit dem UserAccountManager und dem PersonenRepository zusammen.
 * Dabei erkennt er anhand der Rolle einer Person, ob es sich um einen Mitarbeiter oder Kunden handelt und
 * speichert/ruft diesen je nach Rollenzugehörigkeit ab/auf.
 *
 * TODO: DAS ERSTELLEN VON USERACCOUNTS und PERSONEN soll über PersonenManager geschehen, damit rechtzeitig auf Fehler geprüft werden kann,
 */
package Getraenkehandel.Personenmanagement;

import org.salespointframework.core.Streamable;
import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class PersonenManager {

    private PersonenManagement personenManagement;

	/**
	 * @param personenRepo must not be {@literal null}.
	 * @param userAccountManager must not be {@literal null}.
	 */
	PersonenManager(PersonenRepository personenRepo, AdressenRepository adressenRepo, UserAccountManager userAccountManager) {
		this.personenManagement = new PersonenManagement(personenRepo, adressenRepo, userAccountManager);
	}

	// TODO: diese Methoden komplett ersetzen (nur noch mit form arbeiten, sinnvoll??)
	public String anlegen(MitarbeiterAnlegenForm form) {
	    return anlegen(form.getVorname(), form.getNachname(), form.getPasswort(), form.getRolle(), form.getEmail(), form.getStadt(), form.getPlz(), form.getStrasse(), form.getNr(), form.getZusatz(), form.getTel(), form.getStundenlohn(), form.getWochenstunden());
    }

    public String anlegen(KundenAnlegenForm form) {
	    return anlegen(form.getVorname(), form.getNachname(), PersonenUtils.systemfreundlicherRollenName("Kunde").toString(), form.getEmail(), form.getStadt(), form.getPlz(), form.getStadt(), form.getNr(), form.getZusatz(), form.getTel());
    }


	public String anlegen(String vorname, String nachname, String passwort, String rolle, String email, String stadt, String plz, String strasse, String nr, String zusatz, String telefon, String Stundenlohn, String Wochenstunden) {
	    if (!PersonenUtils.istGueltigeRolle(rolle))
	        return "FEHLER: Übergebene Rolle existiert nicht: " + rolle;

	    Adresse adresse = adresseAnlegen(stadt, plz, strasse, nr, zusatz);
	    if(adresse == null)
	        return "FEHLER: Konnte Adresse nicht anlegen!";
	    personenManagement.speichereAdresse(adresse);

	    UserAccount userAccount = speichereUserAccount(vorname, nachname, passwort, rolle, email);
        if(userAccount == null)
            return "FEHLER: Konnte userAccount nicht anlegen!";

        // Annahme, dass ROLLE=Mitarbeiter, da WochenStunden und Stundenlohn angegeben wurden
        Mitarbeiter mitarbeiter = new Mitarbeiter(userAccount, adresse, Float.valueOf(Stundenlohn), Integer.valueOf(Wochenstunden));
        mitarbeiter.setTelefonnummer(telefon);
        personenManagement.speichern(mitarbeiter);

        return "";
    }

    public String anlegen(String vorname, String nachname, String rolle, String email, String stadt, String plz, String strasse, String nr, String zusatz, String telefon) {
        if (!PersonenUtils.istGueltigeRolle(rolle))
            return "FEHLER: Übergebene Rolle existiert nicht: " + rolle;

        Adresse adresse = adresseAnlegen(stadt, plz, strasse, nr, zusatz);
        if(adresse == null)
            return "FEHLER: Konnte Adresse nicht anlegen!";
        personenManagement.speichereAdresse(adresse);

        UserAccount userAccount = speichereUserAccount(vorname, nachname, "123", rolle, email);
        if(userAccount == null)
            return "FEHLER: Konnte userAccount nicht anlegen!";

        // Annahme, dass ROLLE=ROLE_Kunde, da keine WochenStunden und Stundenlohn angegeben wurden
        Person person = new Person(userAccount, adresse);
        person.setTelefonnummer(telefon);
        personenManagement.speichern(person);

        return "";
    }

    public String bearbeiten(KundenBearbeitenForm form) {
        /*Person p = personenManagement.suchePerson(Long.valueOf(form.getPersonenid()));

        if (p == null)
            return "FEHLER: Person mit der angegebenen ID nicht gefunden!";*/

        return bearbeiten(form.getPersonenid(), form.getVorname(), form.getNachname(), form.getEmail(), form.getStadt(), form.getPlz(), form.getStrasse(), form.getNr(), form.getZusatz(), form.getTel());
    }

    public String bearbeiten(MitarbeiterBearbeitenForm form) {
        /*Mitarbeiter p = personenManagement.sucheMitarbeiter(Long.valueOf(form.getPersonenid()));

        if (p == null)
            return "FEHLER: Person mit der angegebenen ID nicht gefunden!";*/

        return bearbeiten(form.getPersonenid(), form.getVorname(), form.getNachname(), form.getPasswort(), form.getRolle(), form.getEmail(), form.getStadt(), form.getPlz(), form.getStrasse(), form.getNr(), form.getZusatz(), form.getTel(), form.getStundenlohn(), form.getWochenstunden());
    }

    private String bearbeiten(String id, String vorname, String nachname, String email, String stadt, String plz, String strasse, String nr, String zusatz, String telefon) {
        Person p = personenManagement.suchePerson(Long.valueOf(id));
        //TODO: Fehler abfangen, wenn Person bearbeitet wird, die es gar nicht gibt
        if (p == null)
            return "FEHLER! Zu bearbeitende Person existiert nicht: " + id;
        if (!p.getRolle(true).equals("Kunde"))
            return "SICHERHEITSWARNUNG! Es wurde versucht, eine Person zu ändern, welche kein Kunde ist.";

        p.getAdresse().setPlz(plz);
        p.getAdresse().setStadt(stadt);
        p.getAdresse().setStrasse(strasse);
        p.getAdresse().setNr(nr);
        p.getAdresse().setZusatz(zusatz);

        p.getUserAccount().setFirstname(vorname);
        p.getUserAccount().setLastname(nachname);
        p.getUserAccount().setEmail(email);
        p.setTelefonnummer(telefon);

	    return "";
    }

    private String bearbeiten(String id, String vorname, String nachname, String passwort, String rolle, String email, String stadt, String plz, String strasse, String nr, String zusatz, String telefon, String stundenlohn, String wochenstunden) {
        Mitarbeiter p = personenManagement.sucheMitarbeiter(Long.valueOf(id));
        //TODO: Fehler abfangen, wenn Person bearbeitet wird, die es gar nicht gibt
        if (p == null)
            return "FEHLER! Zu bearbeitender Mitarbeiter existiert nicht: " + id;

        p.setStundenlohn(Float.valueOf(stundenlohn));
        p.setWochenstunden(Integer.valueOf(wochenstunden));

        p.getAdresse().setPlz(plz);
        p.getAdresse().setStadt(stadt);
        p.getAdresse().setStrasse(strasse);
        p.getAdresse().setNr(nr);
        p.getAdresse().setZusatz(zusatz);

        p.getUserAccount().setFirstname(vorname);
        p.getUserAccount().setLastname(nachname);
        p.getUserAccount().setEmail(email);
        p.setRolle(Role.of(rolle));
        p.setTelefonnummer(telefon);

        if (passwort != null)
            if (!passwort.equals(""))
                personenManagement.aenderePasswort(p.getUserAccount(), passwort);

        return "";
    }

    public String loeschen(long id) {
	    Person p = personenManagement.suchePerson(id);
	    //TODO: müssen userAccount und Adresse auch gelöscht werden? Der Useraccount schon, Adresse?
        personenManagement.loeschen(p);
        personenManagement.loeschen(p.getUserAccount());
        return "";
    }

    private Adresse adresseAnlegen(String stadt, String plz, String strasse, String nr, String zusatz) {
	    Adresse adresse = new Adresse(stadt, plz, strasse, nr, zusatz);

	    //TODO: Fehler abfangen (falsche PLZ)
        if (adresse == null)
            return null;
        return adresse;
    }

    //TODO: Überprüfung auf falsche Parameter
    // ruft speichereUserAccount im PersonenManagement auf
    private UserAccount speichereUserAccount(String vorname, String nachname, String passwort, String rolle, String email) {
        if (!PersonenUtils.istGueltigeRolle(rolle))
            return null;

        // nur Anlegen, wenn userAccount noch nicht existiert
        //if (personenManagement.suchePerson(PersonenUtils.generierebenutzernamen(vorname, nachname)) != null)
        UserAccount ua;
        String benutzername = PersonenUtils.usernameKonvention(vorname, nachname);
        if ((ua = personenManagement.sucheUserAccount(benutzername)) != null) {
            /*if (ua.isEnabled())
                return null;
            // ein userAccount mit gleichem Namen existiert zufällig schon (alter Mitarbeiter)
            else*/
                benutzername = PersonenUtils.generiereNeuenUsernamen(vorname, nachname, personenManagement);
        }

        // neu anlegen
        return personenManagement.speichereUserAccount(benutzername, vorname, nachname, passwort, rolle, email);
	}

    public Streamable<Mitarbeiter> zeigeAlleMitarbeiter() {
	    return Streamable.of(personenManagement.sucheAlleMitarbeiter());
    }

    public Streamable<Person> zeigeAlleKunden() {
        return Streamable.of(personenManagement.sucheAlleKunden());
    }

    private Person zeigePerson(String id) {
	    return personenManagement.suchePerson(Long.valueOf(id));
    }

    public Person zeigePerson(String id, List<Role> role) {
	    Person p = zeigePerson(id);

	    for (Role r : role)
	        if (p.getRolle().equals(r))
	            return p;

	    return null;
    }

    public Mitarbeiter zeigeMitarbeiter(String id) {
        List<Role> r = new LinkedList<>();
        r.add(PersonenUtils.systemfreundlicherRollenName("Lieferant"));
        r.add(PersonenUtils.systemfreundlicherRollenName("Kassierer"));
        r.add(PersonenUtils.systemfreundlicherRollenName("Chef"));

        return (Mitarbeiter) zeigePerson(id, r);
    }

    public Person zeigeKunden(String id) {
	    List<Role> r = new LinkedList<>();
	    r.add(PersonenUtils.systemfreundlicherRollenName("Kunde"));
	    return zeigePerson(id, r);
    }
    
}
