package stdbtt.tracker.util;

public class ChartData {
    private String timestamp;
    private int subsCount;

    public ChartData(String timestamp, int subsCount) {
        this.timestamp = timestamp;
        this.subsCount = subsCount;
    }

    public int getSubsCount() {
        return subsCount;
    }

    public void setSubsCount(int subsCount) {
        this.subsCount = subsCount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
