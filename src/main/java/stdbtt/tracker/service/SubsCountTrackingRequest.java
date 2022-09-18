package stdbtt.tracker.service;

import org.springframework.web.client.RestTemplate;
import stdbtt.tracker.model.TrackingConfig;
import stdbtt.tracker.model.TrackingLap;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.BlockingQueue;

public class SubsCountTrackingRequest implements Runnable {

    private final String requestUrl;

    private final TrackingConfig trackingConfig;

    private final RestTemplate restTemplate;

    private final BlockingQueue<TrackingLap> trackingLapQueue;

    public SubsCountTrackingRequest(TrackingConfig trackingConfig, RestTemplate restTemplate, BlockingQueue<TrackingLap> trackingLapQueue, String requestUrl) {
        this.trackingConfig = trackingConfig;
        this.restTemplate = restTemplate;
        this.trackingLapQueue = trackingLapQueue;
        this.requestUrl = requestUrl;
    }

    @Override
    public void run() {
        int subsCount = getSubsCount();
        OffsetDateTime timestamp = OffsetDateTime.now();
        TrackingLap trackingLap = new TrackingLap(trackingConfig, subsCount, timestamp.truncatedTo(ChronoUnit.SECONDS));
        try {
            trackingLapQueue.put(trackingLap);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int getSubsCount() {
        String url = requestUrl + "@" + trackingConfig.getChannel().getName();
        SubsCountResponse resultMapper = restTemplate.getForObject(url, SubsCountResponse.class);
        return Integer.parseInt(resultMapper.getResult());
    }

}
