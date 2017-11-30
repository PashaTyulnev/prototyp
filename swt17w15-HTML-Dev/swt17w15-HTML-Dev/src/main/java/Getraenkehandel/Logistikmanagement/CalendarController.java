package Getraenkehandel.Logistikmanagement;

import static org.mockito.Matchers.intThat;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.salespointframework.order.Cart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Getraenkehandel.Bestellungsmanagement.Bestellungsmanagement;


@Controller
public class CalendarController{  
	
	private Map<String,String>BestellKalender;
	private Bestellungsmanagement BM;

		public CalendarController(Bestellungsmanagement BM) {
			String Lieferdatum = "20-11-2017";
			String Lieferdatum2 = "21-12-2017";
			String Lieferdatum3 = "02-01-2018";
			String Lieferdatum4 = "21.05.2018";
			String Lieferdatum5 = "15.06.2018";
			String Lieferdatum6 = "25.06.2018"; 
		    boolean haken = false;
		    this.BM = BM;
			
		    
			
			BestellKalender = new TreeMap<String, String>();
			
			
			BestellKalender.put(Lieferdatum ," Lieferung an Person1234 \n" ); 
			BestellKalender.put(Lieferdatum2," Lieferung an Person2354\n");
			BestellKalender.put(Lieferdatum3," Lieferung an Peron3543\n"); 
			BestellKalender.put(Lieferdatum4 ," Lieferung an Person1543 \n" ); 
			BestellKalender.put(Lieferdatum5," Lieferung an Person2534\n");
			BestellKalender.put(Lieferdatum6," Lieferung an Peron7563\n");
		}
	
		@GetMapping("/kalender")
		@PreAuthorize("hasAnyRole('ROLE_Chef', 'ROLE_Kassierer', 'ROLE_Lieferant')")
		public String kalender(@RequestParam Optional<String> name,Model model) throws Exception{
							
			return "calendar";  
		}
		
		public void remove(String key) {
			
			BestellKalender.remove(key);
		}
		
		@RequestMapping(value="/delet", method=RequestMethod.POST)
		@PreAuthorize("hasAnyRole('ROLE_Chef', 'ROLE_Kassierer', 'ROLE_Lieferant')")
		public String deleteAuswahl(@RequestParam("termin") List<String> auswahl) {
			
			for(int i = 0; i<auswahl.size(); i++) {
				this.BestellKalender.remove(auswahl.get(i));
			}
			
			return "redirect:/kalender";
		}
		
		@ModelAttribute("calendar")
		Map initializeCart() {
		return this.BestellKalender;     
	}
		
		
}

