package stdbtt.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stdbtt.tracker.model.Channel;
import stdbtt.tracker.repository.ChannelRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public ChannelService(ChannelRepository channelRepository, EntityManager entityManager) {
        this.channelRepository = channelRepository;
        this.entityManager = entityManager;
    }

    public List<Channel> findChannelsByCustomerName(String customerName) {
        TypedQuery<Channel> query = entityManager.createQuery("SELECT ch FROM Customer c" +
                " JOIN c.channels ch" +
                " WHERE c.name = :customer_name", Channel.class);
        query.setParameter("customer_name", customerName);
        return query.getResultList();
    }

    public Channel addChannel(Channel channel) {
        Channel channelWithId = channelRepository.findChannelByName(channel.getName());
        if (channelWithId == null) {
            channelWithId = channelRepository.save(channel);
        }
        return channelWithId;
    }

    public Channel findChannelByTrackingConfigId(int id) {
        TypedQuery<Channel> query = entityManager.createQuery("SELECT ch FROM TrackingConfig tc" +
                " JOIN tc.channel ch" +
                " WHERE tc.trackingConfigId = :tc_id", Channel.class);
        query.setParameter("tc_id", id);
        return query.getSingleResult();
    }

}
