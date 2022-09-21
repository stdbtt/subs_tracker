package stdbtt.tracker.dto;

import stdbtt.tracker.util.TimeUnitWithMonth;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TrackingConfigDTO {

    private int trackingConfigId;

    private CustomerDTO customerDTO;

    private ChannelDTO channelDTO;

    @NotNull(message = "Интервал не может быть пустым.")
    @Min(value = 1, message = "Интервал должен быть больше нуля.")
    private Long trackInterval;

    private TimeUnitWithMonth timeUnit;

    private List<TrackingLapDTO> trackingLapDTOs;

    public TrackingConfigDTO() {
    }

    public TrackingConfigDTO(ChannelDTO channelDTO) {
        this.channelDTO = channelDTO;
    }

    public int getTrackingConfigId() {
        return trackingConfigId;
    }

    public void setTrackingConfigId(int trackingConfigId) {
        this.trackingConfigId = trackingConfigId;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public ChannelDTO getChannelDTO() {
        return channelDTO;
    }

    public void setChannelDTO(ChannelDTO channelDTO) {
        this.channelDTO = channelDTO;
    }

    public Long getTrackInterval() {
        return trackInterval;
    }

    public void setTrackInterval(Long trackInterval) {
        this.trackInterval = trackInterval;
    }

    public List<TrackingLapDTO> getTrackingLapDTOs() {
        return trackingLapDTOs;
    }

    public void setTrackingLapDTOs(List<TrackingLapDTO> trackingLapDTOs) {
        this.trackingLapDTOs = trackingLapDTOs;
    }

    public TimeUnitWithMonth getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnitWithMonth timeUnit) {
        this.timeUnit = timeUnit;
    }
}
