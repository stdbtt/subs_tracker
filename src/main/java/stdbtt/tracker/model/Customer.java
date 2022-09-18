package stdbtt.tracker.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "customer")
    private List<TrackingConfig> trackingConfigs;

    @ManyToMany(mappedBy = "customers")
    private List<Channel> channels;

    public Customer() {
    }

    public Customer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
