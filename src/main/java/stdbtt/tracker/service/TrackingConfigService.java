package stdbtt.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stdbtt.tracker.model.Channel;
import stdbtt.tracker.model.Customer;
import stdbtt.tracker.model.TrackingConfig;
import stdbtt.tracker.repository.TrackingConfigRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class TrackingConfigService {

    private final SubsCountTrackingService subsCountTrackingService;
    private final TrackingConfigRepository trackingConfigRepository;

    private final ChannelService channelService;

    private final CustomerService customerService;

    @PersistenceContext
    private final EntityManager entityManager;

    @PostConstruct
    private void trackingInitialization() {
        for (TrackingConfig trackingConfig : findAllTrackingConfigsFetchChannel()) {
            subsCountTrackingService.startTracking(trackingConfig);
        }
    }

    @Autowired
    public TrackingConfigService(SubsCountTrackingService subsCountTrackingService, TrackingConfigRepository trackingConfigRepository, ChannelService channelService, CustomerService customerService, EntityManager entityManager) {
        this.subsCountTrackingService = subsCountTrackingService;
        this.trackingConfigRepository = trackingConfigRepository;
        this.channelService = channelService;
        this.customerService = customerService;
        this.entityManager = entityManager;
    }

    public void addTrackingConfig(TrackingConfig trackingConfig) {
        Customer customerWithId = customerService.findCustomerByName(trackingConfig.getCustomer().getName());
        Channel channelWithId = channelService.addChannel(trackingConfig.getChannel());
        trackingConfig.setCustomer(customerWithId);
        trackingConfig.setChannel(channelWithId);
        TrackingConfig trackingConfigWithId = trackingConfigRepository.save(trackingConfig);
        subsCountTrackingService.startTracking(trackingConfigWithId);
    }

    public List<TrackingConfig> findTrackingConfigsFetchChannelByCustomerName(String customerName) {
        TypedQuery<TrackingConfig> query = entityManager.createQuery("SELECT tc FROM TrackingConfig tc" +
                " JOIN FETCH tc.channel ch" +
                " WHERE tc.customer.name = :customer_name", TrackingConfig.class);
        query.setParameter("customer_name", customerName);
        return query.getResultList();
    }

    public List<TrackingConfig> findAllTrackingConfigsFetchChannel() {
        TypedQuery<TrackingConfig> query = entityManager.createQuery("SELECT tc FROM TrackingConfig tc" +
                " JOIN FETCH tc.channel", TrackingConfig.class);
        return query.getResultList();
    }
}
