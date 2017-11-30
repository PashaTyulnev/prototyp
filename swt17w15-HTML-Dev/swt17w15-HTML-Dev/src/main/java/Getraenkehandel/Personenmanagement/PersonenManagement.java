package Getraenkehandel.Personenmanagement;

import org.salespointframework.useraccount.Role;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.LinkedList;

public class PersonenManagement {

    private final UserAccountManager userAccountManager;
    private final PersonenRepository personenRepo;
    private final AdressenRepository adressenRepo;

    public PersonenManagement(PersonenRepository personenRepo, AdressenRepository adressenRepo, UserAccountManager userAccountManager) {
        Assert.notNull(personenRepo, "PersonenRepository must not be null!");
        Assert.notNull(userAccountManager, "UserAccountManager must not be null!");

        this.personenRepo = personenRepo;
        this.adressenRepo = adressenRepo;
        this.userAccountManager = userAccountManager;
    }

    /**
     * Speichert eine Adresse in der DB
     * @param adresse Zu speichernde Adresse
     * @return Adressen-Objekt, welches in DB existiert
     */
    public boolean speichereAdresse(Adresse adresse) {
        adressenRepo.save(adresse);
        return true;
    }

    public UserAccount speichereUserAccount(String benutzername, String vorname, String nachname, String passwort, String rolle, String email) {
        UserAccount ua = sucheUserAccount(benutzername);
        if (ua == null) {
            ua = userAccountManager.create(benutzername, passwort, Role.of(rolle));
            ua.setFirstname(vorname);
            ua.setLastname(nachname);
            ua.setEmail(email);
            //userAccountManager.save(ua); //TODO: anscheinend nicht notwendig
        }
        else {
            ua.setFirstname(vorname);
            ua.setLastname(nachname);
            ua.setEmail(email);
            userAccountManager.save(ua);
        }
        //if (userAccountManager.findByUsername(PersonenUtils.generiereUsernamen(vorname, nachname)).isPresent())
        //    System.out.println("EXISTIERT!!!");
        return ua;
    }

    public boolean loeschen(Person p) {
        personenRepo.delete(p);
        return true;
    }

    public boolean loeschen(UserAccount u) {
        //TODO: wie kann er wirklich gelöscht werden??
        userAccountManager.disable(u.getId());
        return true;
    }

    /**
     * Speichert eine neue Person
     * @param person
     * @return
     */
    public boolean speichern(Person person) {
        personenRepo.save(person);
        return true;
    }

    public boolean aenderePasswort(UserAccount userAccount, String passwort) {
        userAccountManager.changePassword(userAccount, passwort);
        return true;
    }

    public Person suchePerson(String benutzername) {
        Iterator it = personenRepo.findAll().iterator();
        while (it.hasNext()) {
            Person p = (Person) it.next();
            if (benutzername.equals(p.getUserAccount().getUsername()))
                return p;
        }

        return null;
    }

    public Mitarbeiter sucheMitarbeiter(String benutzername) {
        Iterator it = personenRepo.findAll().iterator();
        Mitarbeiter m;
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof Mitarbeiter) {
                m = (Mitarbeiter) obj;
                if (benutzername.equals(m.getUserAccount().getUsername()))
                    return m;
            }
        }

        return null;
    }


    public Person suchePerson(long id) {
        Iterator it = personenRepo.findAll().iterator();
        while (it.hasNext()) {
            Person p = (Person) it.next();
            if (id == p.getId())
                return p;
        }

        return null;
    }

    public Mitarbeiter sucheMitarbeiter(long id) {
        Iterator it = personenRepo.findAll().iterator();
        Mitarbeiter m;
        while (it.hasNext()) {
            Object obj =  it.next();
            if (obj instanceof Mitarbeiter) {
                m = (Mitarbeiter) obj;
                if (id == m.getId())
                    return m;
            }
        }

        return null;
    }

    public UserAccount sucheUserAccount(String benutzername) {
        for (Iterator it = userAccountManager.findAll().iterator(); it.hasNext();) {
            UserAccount userAccount = (UserAccount) it.next();
            if (benutzername.equals(userAccount.getUsername()))
                return userAccount;
        }

        return null;
    }

    public Adresse existiertAdresse(Adresse adresse) {
        for (Iterator it = adressenRepo.findAll().iterator(); it.hasNext();) {
            Adresse a = (Adresse) it.next();
            if (adresse.equals(a))
                return adresse;
        }

        return null;
    }

    public LinkedList<Mitarbeiter> sucheAlleMitarbeiter() {
        LinkedList<Mitarbeiter> mitarbeiter = new LinkedList<>();
        for (Iterator it = personenRepo.findAll().iterator(); it.hasNext();) {
            Object o = it.next();
            if (o instanceof Mitarbeiter) {
                Mitarbeiter m = (Mitarbeiter) o;
                if (    PersonenUtils.systemfreundlicherRollenName("Kassierer").equals(m.getRolle()) ||
                        PersonenUtils.systemfreundlicherRollenName("Lieferant").equals(m.getRolle()) ||
                        PersonenUtils.systemfreundlicherRollenName("Chef").equals(m.getRolle()) )
                    mitarbeiter.add(m);
            }
        }

        return mitarbeiter;
    }

    public LinkedList<Person> sucheAlleKunden() {
        LinkedList<Person> personen = new LinkedList<>();
        for (Iterator it = personenRepo.findAll().iterator(); it.hasNext();) {
            Person p = (Person)it.next();
            if (Role.of("ROLE_Kunde").equals(p.getRolle()))
                    personen.add(p);
        }

        return personen;
    }


}
        /*while (it.hasNext()) {
            UserAccount userAccount = ((Person) it.next()).getUserAccount();
            if (person.getUserAccount().getUsername() == userAccount.getId().toString())
                return false;
        }*/