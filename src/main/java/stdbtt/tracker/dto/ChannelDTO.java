package stdbtt.tracker.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ChannelDTO {

    @NotEmpty(message = "Имя не должно быть пустым.")
    @Size(min = 5, max = 32, message = "Имя должно иметь длину от 5 до 32 символов.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
