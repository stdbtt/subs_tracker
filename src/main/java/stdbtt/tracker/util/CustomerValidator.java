package stdbtt.tracker.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import stdbtt.tracker.model.Customer;
import stdbtt.tracker.service.CustomerService;
import stdbtt.tracker.dto.CustomerDTO;

@Component
public class CustomerValidator {

    private final CustomerService customerService;

    @Autowired
    public CustomerValidator(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void validate(Object target, Errors errors, boolean isNew) {
        String name = ((CustomerDTO) target).getName();
        Customer customer = customerService.findCustomerByName(name);
        if (isNew) {
            if (customer != null) {
                errors.rejectValue("name", "", "Пользователь с таким именем[" + name + "] уже существует!");
            }
        } else {
            if (customer == null) {
                errors.rejectValue("name", "", "Пользователь с таким именем[" + name + "] не существует! " +
                        "\nВыберите другого пользователя или создайте нового.");
            }
        }

    }
}
