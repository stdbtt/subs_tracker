package stdbtt.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stdbtt.tracker.model.TrackingConfig;

@Repository
public interface TrackingConfigRepository extends JpaRepository<TrackingConfig, Integer> {
}
