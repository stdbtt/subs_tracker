package stdbtt.tracker.util;

import stdbtt.tracker.dto.TrackingLapDTO;

import java.time.OffsetDateTime;

public class ChartDataMapper {

    public static ChartData map(TrackingLapDTO trackingLapDTO) {
        OffsetDateTime timestamp = trackingLapDTO.getTimestamp();
        return new ChartData(timestamp.toLocalDateTime().toString(), trackingLapDTO.getSubsCount());
    }
}
