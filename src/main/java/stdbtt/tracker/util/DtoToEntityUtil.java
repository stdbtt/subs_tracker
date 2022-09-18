package stdbtt.tracker.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stdbtt.tracker.dto.ChannelDTO;
import stdbtt.tracker.dto.TrackingConfigDTO;
import stdbtt.tracker.dto.CustomerDTO;
import stdbtt.tracker.model.Channel;
import stdbtt.tracker.model.Customer;
import stdbtt.tracker.model.TrackingConfig;
import stdbtt.tracker.model.TrackingLap;
import stdbtt.tracker.dto.TrackingLapDTO;

@Component
public class DtoToEntityUtil {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoToEntityUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Customer convert(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }

    public Channel convert(ChannelDTO channelDTO) {
        return modelMapper.map(channelDTO, Channel.class);
    }

    public TrackingConfig convert(TrackingConfigDTO trackingConfigDTO) {
        return modelMapper.map(trackingConfigDTO, TrackingConfig.class);
    }

    public TrackingLap convert(TrackingLapDTO trackingLapDTO) {
        return modelMapper.map(trackingLapDTO, TrackingLap.class);
    }
}
