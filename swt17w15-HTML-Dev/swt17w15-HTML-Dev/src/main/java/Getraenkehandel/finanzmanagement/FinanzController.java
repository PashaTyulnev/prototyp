package Getraenkehandel.finanzmanagement;

import org.javamoney.moneta.Money;
import org.salespointframework.accountancy.AccountancyEntry;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManager;
import org.salespointframework.order.OrderStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by An on 14.11.2017.
 */
@Controller
class FinanzController {
    private final Finanzen finanzen;
    private final OrderManager<Order> orderManager;

    FinanzController(Finanzen finanzen,OrderManager<Order> orderManager){
        this.orderManager = orderManager;
        this.finanzen =	finanzen;
    }

    @GetMapping("/finanzen")
    String finanzen(Model model) {

        for (Order k :orderManager.findBy(OrderStatus.PAID))
        {
            orderManager.completeOrder(k);
            System.out.println(k.getTotalPrice());
            System.out.println("HMMM");

        }

        model.addAttribute("einnahmen", finanzen.monatlicheEinnahmenBerechnen());
        model.addAttribute("gesamtGehalt", finanzen.gesamtGehaltberechnen());
        model.addAttribute("ausgaben", finanzen.ausgabenBerechnen());
        model.addAttribute("gesamtAusgaben", finanzen.gesamtAusgabenBerechnen());
        model.addAttribute("jhrlicheeinnahmen", finanzen.jährlicheEinnahmenBerechnen());
        model.addAttribute("monatlicherUmsatz", finanzen.monatlichenUmsatzBerechnen());

        return "finanzen";
    }

    @PostMapping("/finanzbutton")
    String addTime(@RequestParam(value = "addTime") int time){

        finanzen.addTime(time);

        return "redirect:/finanzen";
    }

    @PostMapping("/ausgabeneintragen")
    String ausgaben(@RequestParam(value = "ausgabeneintragen") float money){

        finanzen.ausgabenHinzufügen(money);

        return "redirect:/finanzen";
    }

}
