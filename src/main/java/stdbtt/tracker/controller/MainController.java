package stdbtt.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import stdbtt.tracker.util.CustomerValidator;
import stdbtt.tracker.dto.CustomerDTO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class MainController {

    private final HttpSession httpSession;

    private final CustomerValidator customerValidator;

    @Autowired
    public MainController(HttpSession httpSession, CustomerValidator customerValidator) {
        this.httpSession = httpSession;
        this.customerValidator = customerValidator;
    }

    @GetMapping("/helloPage")
    public String inputCustomer(Model model){
        model.addAttribute("customer", new CustomerDTO());
        return "helloPage";
    }

    @PostMapping("/helloPage")
    public String chooseCustomer(@ModelAttribute("customer") @Valid CustomerDTO customerDTO, BindingResult bindingResult){
        customerValidator.validate(customerDTO, bindingResult, false);
        if(bindingResult.hasErrors())
            return "helloPage";
        httpSession.setAttribute("customer", customerDTO);
        return "redirect:/trackingLap/selectStatsParameters";
    }
}
