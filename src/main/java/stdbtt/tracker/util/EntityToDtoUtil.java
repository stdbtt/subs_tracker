package stdbtt.tracker.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stdbtt.tracker.dto.ChannelDTO;
import stdbtt.tracker.dto.TrackingConfigDTO;
import stdbtt.tracker.dto.CustomerDTO;
import stdbtt.tracker.dto.TrackingLapDTO;
import stdbtt.tracker.model.Channel;
import stdbtt.tracker.model.Customer;
import stdbtt.tracker.model.TrackingConfig;
import stdbtt.tracker.model.TrackingLap;

@Component
public class EntityToDtoUtil {

    private final ModelMapper modelMapper;

    @Autowired
    public EntityToDtoUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerDTO convert(Customer customer){
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public ChannelDTO convert(Channel channel){
        return modelMapper.map(channel, ChannelDTO.class);
    }

    public TrackingConfigDTO convert(TrackingConfig trackingConfig){
        return modelMapper.map(trackingConfig, TrackingConfigDTO.class);
    }

    public TrackingLapDTO convert(TrackingLap trackingLap){
        return modelMapper.map(trackingLap, TrackingLapDTO.class);
    }

}
