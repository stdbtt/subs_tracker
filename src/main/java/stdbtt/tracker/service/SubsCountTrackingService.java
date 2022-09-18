package stdbtt.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import stdbtt.tracker.model.TrackingConfig;
import stdbtt.tracker.model.TrackingLap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;


@Component
public class SubsCountTrackingService {


    @Value("${telegram.url.bot_token}")
    private String botToken;

    @Value("${telegram.url.api.address}")
    private String apiAddress;

    @Value("${telegram.url.api.method}")
    private String apiMethod;

    private String requestUrl;

    private final RestTemplate restTemplate;

    private final BlockingQueue<TrackingLap> trackingLapQueue;

    private ScheduledExecutorService trackingExecutorService;

    private List<ScheduledFuture<?>> requestTasks;

    @PostConstruct
    public void init() {
        trackingExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        requestTasks = new ArrayList<>();
        requestUrl = apiAddress + botToken + apiMethod;
    }

    @PreDestroy
    public void preDestroy() {
        requestTasks.forEach((t) -> t.cancel(true));
        trackingExecutorService.shutdown();
    }

    @Autowired
    public SubsCountTrackingService(RestTemplate restTemplate, BlockingQueue<TrackingLap> trackingLapQueue) {
        this.restTemplate = restTemplate;
        this.trackingLapQueue = trackingLapQueue;
    }

    public void startTracking(TrackingConfig trackingConfig) {
        ScheduledFuture<?> requestTask = trackingExecutorService.scheduleAtFixedRate(new SubsCountTrackingRequest(trackingConfig, restTemplate, trackingLapQueue, requestUrl), 0, trackingConfig.getIntervalInSeconds(), SECONDS);
        requestTasks.add(requestTask);
    }

}
