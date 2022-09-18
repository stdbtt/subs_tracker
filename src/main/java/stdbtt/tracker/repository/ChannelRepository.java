package stdbtt.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stdbtt.tracker.model.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    Channel findChannelByName(String name);
}
