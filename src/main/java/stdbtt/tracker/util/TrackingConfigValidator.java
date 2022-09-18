package stdbtt.tracker.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import stdbtt.tracker.dto.TrackingConfigDTO;
import stdbtt.tracker.model.Channel;
import stdbtt.tracker.service.ChannelService;
import stdbtt.tracker.dto.CustomerDTO;

import java.util.Optional;

@Component
public class TrackingConfigValidator {

    private final ChannelService channelService;

    @Autowired
    public TrackingConfigValidator(ChannelService channelService) {
        this.channelService = channelService;
    }

    public void validate(TrackingConfigDTO trackingConfigDTO, Errors errorsTrackingConfig, Errors errorsChannel) {
        validateChannel(trackingConfigDTO.getCustomerDTO(), trackingConfigDTO.getChannelDTO().getName(), errorsChannel);

        validateInterval(trackingConfigDTO.getTrackInterval(), trackingConfigDTO.getTimeUnit(), errorsTrackingConfig);
    }

    private void validateChannel(CustomerDTO customerDTO, String channelName, Errors errors) {
        Optional<String> channelFromDB = channelService.findChannelsByCustomerName(customerDTO.getName())
                .stream()
                .map(Channel::getName)
                .filter(name -> name.equals(channelName))
                .findAny();
        if (channelFromDB.isPresent())
            errors.rejectValue("name", "", "Вы уже отслеживаете [" + channelName + "]! " +
                    "\nВыберите другой канал.");
    }

    private void validateInterval(Long trackInterval, String timeUnitName, Errors errors) {
        System.out.println("trackInterval: " + trackInterval);
        TimeUnitWithMonth timeUnit = TimeUnitWithMonth.valueOf(timeUnitName);
        if (trackInterval != null && trackInterval > timeUnit.getMaxValue()) {
            errors.rejectValue("trackInterval", "", "Интервал не должен быть больше" +
                    timeUnit.getMaxValue() + " " + timeUnit.getName());

        }
    }
}
