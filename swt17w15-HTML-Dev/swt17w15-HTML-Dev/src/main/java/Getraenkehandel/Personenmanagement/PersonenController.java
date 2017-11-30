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

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
class PersonenController {

	private final PersonenManager personenManager;

	PersonenController(PersonenManager personenManager) {

		Assert.notNull(personenManager, "CustomerManagement must not be null!");

		this.personenManager = personenManager;
	}

	@GetMapping("/kundenliste")
	@PreAuthorize("hasAnyRole('ROLE_Chef', 'ROLE_Kassierer', 'ROLE_Lieferant')")
	String customers(Model model) {

		model.addAttribute("kundenListe", personenManager.zeigeAlleKunden());

		return "kundenliste";
	}

	@GetMapping("/kundenAnlegen")
	@PreAuthorize("hasAnyRole('ROLE_Chef', 'ROLE_Kassierer')")
	String zeigeKundenAnlegen(Model model, KundenAnlegenForm form) {

		model.addAttribute("form", form);
		/*model.addAttribute("personenid", "");
		model.addAttribute("nachname", "");
		model.addAttribute("vorname", "");
		model.addAttribute("plz", "");
		model.addAttribute("stadt", "");
		model.addAttribute("strasse", "");
		model.addAttribute("nr", "");
		model.addAttribute("zusatz", "");
		model.addAttribute("tel", "");
		model.addAttribute("email","");
		model.addAttribute("stundenlohn", "");
		model.addAttribute("wochenstunden", "");
		model.addAttribute("passwort", "");
		model.addAttribute("rolle", "");
		model.addAttribute("benutzername", "");*/

		return "kundenAnlegen";
	}

	@PostMapping("/kundenAnlegen")
	@PreAuthorize("hasAnyRole('ROLE_Chef', 'ROLE_Kassierer')")
	String kundenAnlegen( KundenAnlegenForm form, Errors result) {
		if(result.hasErrors()) //TODO: redirect besser machen
			return "redirect:/kundenAnlegen";

		System.out.println(form.toString());

		System.out.println(personenManager.anlegen(form));

		return "redirect:/kundenliste";
	}

	@GetMapping("/bearbeiteKunden/{personen_id}")
	@PreAuthorize("hasAnyRole('ROLE_Chef', 'ROLE_Kassierer', 'ROLE_Lieferant')")
	String zeigeKundenbearbeitung(@PathVariable String personen_id, Model model, KundenBearbeitenForm form) {
		Person p = personenManager.zeigeKunden(personen_id);

		model.addAttribute("form", form);
		model.addAttribute("personenid", p.getId());
		model.addAttribute("nachname", p.getUserAccount().getLastname());
		model.addAttribute("vorname", p.getUserAccount().getFirstname());
		model.addAttribute("plz", p.getAdresse().getPlz());
		model.addAttribute("stadt", p.getAdresse().getStadt());
		model.addAttribute("strasse", p.getAdresse().getStrasse());
		model.addAttribute("nr", p.getAdresse().getNr());
		model.addAttribute("zusatz", p.getAdresse().getZusatz());
		model.addAttribute("tel", p.getTelefonnummer());
		model.addAttribute("email", p.getUserAccount().getEmail());

		return "bearbeiteKunden";
	}

	@PostMapping("/speichereKunden")
	@PreAuthorize("hasAnyRole('ROLE_Chef', 'ROLE_Kassierer', 'ROLE_Lieferant')")
	String speichereKunden(@Valid KundenBearbeitenForm form, Errors result) {

		if(result.hasErrors())
			return "redirect:/bearbeiteKunden/"+form.getPersonenid();

		System.out.println(form.toString());

		System.out.println(personenManager.bearbeiten(form));

		return "redirect:/kundenliste";
	}

	@GetMapping("/loescheKunden/{personen_id}")
	@PreAuthorize("hasAnyRole('ROLE_Chef')")
	String loescheKunden(@PathVariable String personen_id) {
		//if(result.hasErrors())
		//	return "redirect:/mitarbeiterliste";

		personenManager.loeschen(Long.valueOf(personen_id));

		return "redirect:/kundenliste";
	}

	@GetMapping("/mitarbeiterliste")
	@PreAuthorize("hasAnyRole('ROLE_Chef')")
	String mitarbeiter(Model model) {

		model.addAttribute("mitarbeiterListe", personenManager.zeigeAlleMitarbeiter());

		return "mitarbeiterliste";
	}

	@GetMapping("/mitarbeiterAnlegen")
	@PreAuthorize("hasAnyRole('ROLE_Chef')")
	String zeigeMitarbeiterAnlegen(Model model, MitarbeiterBearbeitenForm form) {

		model.addAttribute("form", form);
		/*model.addAttribute("personenid", "");
		model.addAttribute("nachname", "");
		model.addAttribute("vorname", "");
		model.addAttribute("plz", "");
		model.addAttribute("stadt", "");
		model.addAttribute("strasse", "");
		model.addAttribute("nr", "");
		model.addAttribute("zusatz", "");
		model.addAttribute("tel", "");
		model.addAttribute("email","");
		model.addAttribute("stundenlohn", "");
		model.addAttribute("wochenstunden", "");
		model.addAttribute("passwort", "");
		model.addAttribute("rolle", "");
		model.addAttribute("benutzername", "");*/

		return "mitarbeiterAnlegen";
	}

	@PostMapping("/mitarbeiterAnlegen")
	@PreAuthorize("hasAnyRole('ROLE_Chef')")
	String mitarbeiterAnlegen( MitarbeiterAnlegenForm form, Errors result) {
		if(result.hasErrors()) //TODO: redirect besser machen
			return "redirect:/mitarbeiterAnlegen";

		System.out.println(form.toString());

		System.out.println(personenManager.anlegen(form));

		return "redirect:/mitarbeiterliste";
	}

	@GetMapping("/bearbeiteMitarbeiter/{personen_id}")
	@PreAuthorize("hasAnyRole('ROLE_Chef')")
	String zeigeMitarbeiterbearbeitung(@PathVariable String personen_id, Model model, MitarbeiterBearbeitenForm form) {
		Mitarbeiter p = personenManager.zeigeMitarbeiter(personen_id);

		System.out.println(p.toString());

		model.addAttribute("form", form);
		model.addAttribute("personenid", p.getId());
		model.addAttribute("nachname", p.getUserAccount().getLastname());
		model.addAttribute("vorname", p.getUserAccount().getFirstname());
		model.addAttribute("plz", p.getAdresse().getPlz());
		model.addAttribute("stadt", p.getAdresse().getStadt());
		model.addAttribute("strasse", p.getAdresse().getStrasse());
		model.addAttribute("nr", p.getAdresse().getNr());
		model.addAttribute("zusatz", p.getAdresse().getZusatz());
		model.addAttribute("tel", p.getTelefonnummer());
		model.addAttribute("email", p.getUserAccount().getEmail());
		model.addAttribute("stundenlohn", p.getStundenlohn());
		model.addAttribute("wochenstunden", p.getWochenstunden());
		model.addAttribute("passwort", "*****");
		model.addAttribute("rolle", p.getRolle().toString());
		model.addAttribute("benutzername", p.getUserAccount().getId());

		return "bearbeiteMitarbeiter";
	}

	@PostMapping("/speichereMitarbeiter")
	@PreAuthorize("hasAnyRole('ROLE_Chef')")
	String speichereMitarbeiter( MitarbeiterBearbeitenForm form, Errors result) {

		if(result.hasErrors())
			return "redirect:/bearbeiteMitarbeiter/"+form.getPersonenid();

		System.out.println(form.toString());

		System.out.println(personenManager.bearbeiten(form));

		return "redirect:/mitarbeiterliste";
	}

	@GetMapping("/loescheMitarbeiter/{personen_id}")
	@PreAuthorize("hasAnyRole('ROLE_Chef')")
	String loescheMitarbeiter(@PathVariable String personen_id) {
		//if(result.hasErrors())
		//	return "redirect:/mitarbeiterliste";

		personenManager.loeschen(Long.valueOf(personen_id));

		return "redirect:/mitarbeiterliste";
	}
}
