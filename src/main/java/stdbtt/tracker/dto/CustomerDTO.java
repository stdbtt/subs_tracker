package stdbtt.tracker.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CustomerDTO {

    @NotEmpty(message = "Имя не должно быть пустым.")
    @Size(max = 32, message = "Длина имени не должна быть больше чем 32 символа.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
