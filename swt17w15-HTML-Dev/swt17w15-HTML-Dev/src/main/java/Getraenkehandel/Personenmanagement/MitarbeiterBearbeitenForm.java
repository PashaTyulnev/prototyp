/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Getraenkehandel.Personenmanagement;

import org.hibernate.validator.constraints.NotEmpty;

// (｡◕‿◕｡)
// Manuelle Validierung ist mühsam, Spring bietet hierfür auch Support.
// Um die Registrierung auf korrekte Eingaben zu checken, schreiben eine POJO-Klasse, welche den Inputfelder der Registrierung entspricht
// Diese werden annotiert, damit der Validator weiß, worauf geprüft werden soll
// Via message übergeben wir einen Resourcekey um die Fehlermeldungen auch internationalisierbar zu machen.
// Die ValidationMessages.properties Datei befindet sich in /src/main/resources
// Lektüre: 
// http://docs.oracle.com/javaee/6/tutorial/doc/gircz.html
// http://docs.jboss.org/hibernate/validator/4.2/reference/en-US/html/

interface MitarbeiterBearbeitenForm {

    @NotEmpty(message = "Die ID darf nicht leer sein!")
    String getPersonenid();

    @NotEmpty(message = "Der Nachname darf nicht leer sein!")
    String getNachname();

	@NotEmpty(message = "Der Vorname darf nicht leer sein!")
	String getVorname();

	@NotEmpty(message = "Die Postleitzahl darf nicht leer sein!")
	String getPlz();

	@NotEmpty(message = "Die Stadt darf nicht leer sein!")
	String getStadt();

    @NotEmpty(message = "Die Strasse darf nicht leer sein!")
    String getStrasse();

    @NotEmpty(message = "Die Hausnummer darf nicht leer sein!")
    String getNr();

    @NotEmpty(message = "Der Zusatz darf nicht leer sein!")
    String getZusatz();

    @NotEmpty(message = "Die Telefonnummer darf nicht leer sein!")
    String getTel();

    @NotEmpty(message = "Die E-Mail darf nicht leer sein!")
    String getEmail();

    @NotEmpty(message = "Der Stundenlohn darf nicht leer sein!")
    String getStundenlohn();

    @NotEmpty(message = "Die Wochenstunden dürfen nicht leer sein!")
    String getWochenstunden();

    String getPasswort();

    @NotEmpty(message = "Die Rolle darf nicht leer sein!")
    String getRolle();
}
