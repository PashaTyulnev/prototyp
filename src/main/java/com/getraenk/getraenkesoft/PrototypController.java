package com.getraenk.getraenkesoft;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class PrototypController{
	
		@GetMapping("/")
		public String kalender(@RequestParam Optional<String> name,Model model) {
			model.addAttribute("home",new home());
			return "home";
		}
		@GetMapping("/home")
		public String kalenderhome(@RequestParam Optional<String> name,Model model) {
			model.addAttribute("home",new home());
			return "home";
		}
}
