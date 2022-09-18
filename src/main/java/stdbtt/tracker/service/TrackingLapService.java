package stdbtt.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stdbtt.tracker.model.TrackingConfig;
import stdbtt.tracker.model.TrackingLap;
import stdbtt.tracker.repository.TrackingLapRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class TrackingLapService {

    private final TrackingLapRepository trackingLapRepository;
    private final BlockingQueue<TrackingLap> trackingLapQueue;

    private final ExecutorService updateDBExecutorService = Executors.newSingleThreadExecutor();
    private Future<?> listenerTask;

    @PostConstruct
    public void TrackingLapDBUpdate() {
        listenerTask = updateDBExecutorService.submit(new TrackingLapUpdateListener(trackingLapQueue, this));
    }

    @PreDestroy
    public void preDestroy() {
        listenerTask.cancel(true);
        updateDBExecutorService.shutdown();
    }

    @Autowired
    public TrackingLapService(TrackingLapRepository trackingLapRepository, BlockingQueue<TrackingLap> trackingLapQueue) {
        this.trackingLapRepository = trackingLapRepository;
        this.trackingLapQueue = trackingLapQueue;
    }

    public void addTrackingLap(TrackingLap TrackingLap) {
        trackingLapRepository.save(TrackingLap);
    }

    @Transactional
    public List<TrackingLap> findAllByTrackingConfigBetween(TrackingConfig trackingConfig, OffsetDateTime startTime, OffsetDateTime endTime) {
        return trackingLapRepository.findByTrackingConfigAndTimestampBetween(trackingConfig, startTime, endTime);
    }
}
