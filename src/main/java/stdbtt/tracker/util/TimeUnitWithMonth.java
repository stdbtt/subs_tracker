package stdbtt.tracker.util;

public enum TimeUnitWithMonth {
    SECOND("секунда", TimeUnitWithMonth.SECOND_SCALE, TimeUnitWithMonth.SECOND_MAX_VALUE),
    MINUTE("минута", TimeUnitWithMonth.MINUTE_SCALE, TimeUnitWithMonth.MINUTE_MAX_VALUE),
    HOUR("час", TimeUnitWithMonth.HOUR_SCALE, TimeUnitWithMonth.HOUR_MAX_VALUE),
    DAY("день", TimeUnitWithMonth.DAY_SCALE, TimeUnitWithMonth.DAY_MAX_VALUE),
    MONTH("месяц", TimeUnitWithMonth.MONTH_SCALE, TimeUnitWithMonth.MONTH_MAX_VALUE);

    private static final long SECOND_SCALE = 1;
    private static final long MINUTE_SCALE = SECOND_SCALE * 60;
    private static final long HOUR_SCALE = MINUTE_SCALE * 60;
    private static final long DAY_SCALE = HOUR_SCALE * 24;
    private static final long MONTH_SCALE = DAY_SCALE * 30;

    private static final long SECOND_MAX_VALUE = TimeUnitWithMonth.MINUTE_MAX_VALUE * 60;

    private static final long MINUTE_MAX_VALUE = TimeUnitWithMonth.HOUR_MAX_VALUE * 60;

    private static final long HOUR_MAX_VALUE = TimeUnitWithMonth.DAY_MAX_VALUE * 24;

    private static final long DAY_MAX_VALUE = TimeUnitWithMonth.MONTH_MAX_VALUE * 30;

    private static final long MONTH_MAX_VALUE = 120;
    private final String name;
    private final long scale;

    private final long maxValue;

    TimeUnitWithMonth(String name, long scale, long maxValue) {
        this.name = name;
        this.scale = scale;
        this.maxValue = maxValue;
    }

    public String getName() {
        return name;
    }

    public long getScale() {
        return scale;
    }

    public static TimeUnitWithMonth parseFromString(String s) {
        for (TimeUnitWithMonth tu : TimeUnitWithMonth.values()) {
            if (s.equals(tu.getName())) {
                return tu;
            }
        }
        throw new IllegalArgumentException(
                "No enum constant " + TimeUnitWithMonth.class.getCanonicalName() + "." + s);
    }

    public long getMaxValue() {
        return maxValue;
    }
}
