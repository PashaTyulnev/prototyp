package Getraenkehandel.Personenmanagement;

import org.salespointframework.useraccount.Role;

import javax.validation.constraints.NotNull;

public class PersonenUtils {

    public static String generiereNeuenUsernamen(String vorname, String nachname, @NotNull PersonenManagement pm) {
        String username = usernameKonvention(vorname, nachname);

        int i = 0;
        // Wenn username bereits vergeben ist, hänge int an, bis freier username gefunden wurde
        if (null != pm.sucheUserAccount(username)) {
            do {
                i++;
            } while (null != pm.sucheUserAccount(username+i));
        }
        return username+i;
    }

    /**
     * Definiert den Aufbau eines Username
     * @param vorname
     * @param nachname
     * @return
     */
    public static String usernameKonvention(String vorname, String nachname) {
        return nachname.toLowerCase() + kuerzeString(vorname, 100).toLowerCase();
    }

    private static String kuerzeString(String s, int laenge) {
        if (s.length() <= laenge)
            return s;
        return s.substring(0, laenge-1);
    }

    public static String userfreundlicherRollenName(Role r) {
        switch (r.toString()) {
            case "ROLE_Kunde": return "Kunde";
            case "ROLE_Chef": return "Chef";
            case "ROLE_Kassierer": return "Kassierer";
            case "ROLE_Lieferant": return "Lieferant";
        }

        return null;
    }

    public static Role systemfreundlicherRollenName(String r) {
        switch (r) {
            case "Kunde": return Role.of("ROLE_Kunde");
            case "Chef": return Role.of("ROLE_Chef");
            case "Kassierer": return Role.of("ROLE_Kassierer");
            case "Lieferant": return Role.of("ROLE_Lieferant");
        }

        return null;
    }

    /**
     * Prüft, ob angegebene Rolle eine der Rollen "ROLE_Kunde", "ROLE_Kassierer", "ROLE_Lieferant", "ROLE_Chef" ist
     * @param rolle zu prüfende Rolle
     * @return
     */
    public static boolean istGueltigeRolle(String rolle) {
        if (    Role.of(rolle).equals(PersonenUtils.systemfreundlicherRollenName("Kunde")) ||
                Role.of(rolle).equals(PersonenUtils.systemfreundlicherRollenName("Kassierer")) ||
                Role.of(rolle).equals(PersonenUtils.systemfreundlicherRollenName("Lieferant")) ||
                Role.of(rolle).equals(PersonenUtils.systemfreundlicherRollenName("Chef")) )
            return true;
        else
            return false;
    }
}
