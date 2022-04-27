package jetoze.exercise.tracking;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public final class DailyValue implements Comparable<DailyValue> {

    private final LocalDate date;
    private final Number value;
    
    public DailyValue(LocalDate date, Number value) {
        this.date = requireNonNull(date);
        this.value = requireNonNull(value);
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public Number getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return date + ": " + Formats.format(value);
    }

    @Override
    public int compareTo(DailyValue o) {
        return Double.compare(value.doubleValue(), o.value.doubleValue());
    }

}
