package stdbtt.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stdbtt.tracker.model.TrackingConfig;
import stdbtt.tracker.model.TrackingLap;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TrackingLapRepository extends JpaRepository<TrackingLap, Integer> {
    List<TrackingLap> findByTrackingConfigAndTimestampBetween(TrackingConfig trackingConfig, OffsetDateTime startTime, OffsetDateTime endTime);
}
