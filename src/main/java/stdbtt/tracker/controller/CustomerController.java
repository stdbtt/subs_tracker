package stdbtt.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import stdbtt.tracker.service.CustomerService;
import stdbtt.tracker.util.CustomerValidator;
import stdbtt.tracker.util.DtoToEntityUtil;
import stdbtt.tracker.dto.CustomerDTO;

import javax.validation.Valid;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerValidator customerValidator;

    private final DtoToEntityUtil dtoToEntityUtil;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerValidator customerValidator, DtoToEntityUtil dtoToEntityUtil) {
        this.customerService = customerService;
        this.customerValidator = customerValidator;
        this.dtoToEntityUtil = dtoToEntityUtil;
    }

    @GetMapping("/customer/new")
    public String addCustomerForm(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "customer/new";
    }

    @PostMapping("/customer/new")
    public String addCustomer(@ModelAttribute("customer") @Valid CustomerDTO customerDTO, BindingResult bindingResult) {
        customerValidator.validate(customerDTO, bindingResult, true);
        if (bindingResult.hasErrors())
            return "customer/new";
        customerService.addCustomer(dtoToEntityUtil.convert(customerDTO));
        return "redirect:/helloPage";
    }
}
