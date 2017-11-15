package com.getraenk.getraenkesoft;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.salespointframework.order.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class CalendarController{  
	
	@ModelAttribute("calendar")
	Map initializeCart() {
		String Lieferdatum = "20-11-2017 ";
		String Lieferdatum2 = "21-12-2017 ";
		String Lieferdatum3 = "02-01-2018 ";
		String Lieferdatum4 = "21.05.2018";
		String Lieferdatum5 = "15.06.2018";  
		
		
		Map<String,String>BestellKalender = new TreeMap<String, String>();
		
		
//		DateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
//		Date inputDate = format1.parse(Lieferdatum);
		
		
		BestellKalender.put(Lieferdatum ,": Lieferung an Pasha \n" ); 
		BestellKalender.put(Lieferdatum2,": Lieferung an Markus\n");
		BestellKalender.put(Lieferdatum3,": Lieferung an Niemanden\n");  
		return BestellKalender;     
	}
	
		@GetMapping("/calendar")
		public String kalender(@RequestParam Optional<String> name,Model model) {
		
			return "calendar";  
		}
}

