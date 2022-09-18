package stdbtt.tracker.service;

import stdbtt.tracker.model.TrackingLap;

import java.util.concurrent.BlockingQueue;

public class TrackingLapUpdateListener implements Runnable {

    private final BlockingQueue<TrackingLap> trackingLapQueue;

    private final TrackingLapService trackingLapService;

    public TrackingLapUpdateListener(BlockingQueue<TrackingLap> trackingLapQueue, TrackingLapService trackingLapService) {
        this.trackingLapQueue = trackingLapQueue;
        this.trackingLapService = trackingLapService;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                trackingLapService.addTrackingLap(trackingLapQueue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}
