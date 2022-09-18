package stdbtt.tracker.model;


import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tracking_lap")
public class TrackingLap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_lap_id")
    private int trackingLapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tracking_config_id", referencedColumnName = "tracking_config_id")
    private TrackingConfig trackingConfig;

    @Column(name = "subs_count")
    private int subsCount;

    @Column(name = "timestamp")
    private OffsetDateTime timestamp;

    public TrackingLap() {
    }

    public TrackingLap(TrackingConfig trackingConfig, int subsCount, OffsetDateTime timestamp) {
        this.trackingConfig = trackingConfig;
        this.subsCount = subsCount;
        this.timestamp = timestamp;
    }

    public int getTrackingLapId() {
        return trackingLapId;
    }

    public void setTrackingLapId(int trackingLapId) {
        this.trackingLapId = trackingLapId;
    }

    public TrackingConfig getTrackingConfig() {
        return trackingConfig;
    }

    public void setTrackingConfig(TrackingConfig trackingConfig) {
        this.trackingConfig = trackingConfig;
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
