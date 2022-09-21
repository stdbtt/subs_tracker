package stdbtt.tracker.model;


import stdbtt.tracker.util.TimeUnitWithMonth;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tracking_config")
public class TrackingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_config_id")
    private int trackingConfigId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", referencedColumnName = "channel_id")
    private Channel channel;

    @Column(name = "track_interval")
    private Long trackInterval;

    @OneToMany(mappedBy = "trackingConfig")
    private List<TrackingLap> trackingLaps;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_unit")
    private TimeUnitWithMonth timeUnit;

    public TrackingConfig() {
    }

    public TrackingConfig(int trackingConfigId) {
        this.trackingConfigId = trackingConfigId;
    }

    public TrackingConfig(Customer customer, Channel channel, long trackInterval) {
        this.customer = customer;
        this.channel = channel;
        this.trackInterval = trackInterval;
    }

    public TrackingConfig(Customer customer, Channel channel) {
        new TrackingConfig(customer, channel, 0);
    }

    public int getTrackingConfigId() {
        return trackingConfigId;
    }

    public void setTrackingConfigId(int trackingConfigId) {
        this.trackingConfigId = trackingConfigId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Long getTrackInterval() {
        return trackInterval;
    }

    public void setTrackInterval(Long trackInterval) {
        this.trackInterval = trackInterval;
    }

    public List<TrackingLap> getTrackingLaps() {
        return trackingLaps;
    }

    public void setTrackingLaps(List<TrackingLap> trackingLaps) {
        this.trackingLaps = trackingLaps;
    }

    public TimeUnitWithMonth getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnitWithMonth timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Long getIntervalInSeconds() {
        return trackInterval * timeUnit.getScale();
    }
}
