package stdbtt.tracker.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private int channelId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "channel")
    private List<TrackingConfig> trackingConfigs;

    @ManyToMany
    @JoinTable(
            name = "tracking_config",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customers;

    public Channel() {
    }

    public Channel(int channelId, String name) {
        this.channelId = channelId;
        this.name = name;
    }

    public Channel(String name) {
        this.name = name;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TrackingConfig> getTrackingConfigs() {
        return trackingConfigs;
    }

    public void setTrackingConfigs(List<TrackingConfig> trackingConfigs) {
        this.trackingConfigs = trackingConfigs;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
