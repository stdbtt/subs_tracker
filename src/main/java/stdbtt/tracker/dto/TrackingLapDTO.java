package stdbtt.tracker.dto;

import java.time.OffsetDateTime;

public class TrackingLapDTO {

    private TrackingConfigDTO trackingConfigDTO;

    private int subsCount;

    private OffsetDateTime timestamp;

    public TrackingLapDTO() {
    }

    public TrackingLapDTO(TrackingConfigDTO trackingConfigDTO, int subsCount, OffsetDateTime timestamp) {
        this.trackingConfigDTO = trackingConfigDTO;
        this.subsCount = subsCount;
        this.timestamp = timestamp;
    }

    public TrackingConfigDTO getTrackingConfigDTO() {
        return trackingConfigDTO;
    }

    public void setTrackingConfigDTO(TrackingConfigDTO trackingConfigDTO) {
        this.trackingConfigDTO = trackingConfigDTO;
    }

    public int getSubsCount() {
        return subsCount;
    }

    public void setSubsCount(int subsCount) {
        this.subsCount = subsCount;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
