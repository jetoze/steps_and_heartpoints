package jetoze.exercise.tracking;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public final class DailyValue {

    private final LocalDate date;
    private final Number value;
    
    public DailyValue(LocalDate date, Number value) {
        this.date = requireNonNull(date);
        this.value = requireNonNull(value);
    }
    
    @Override
    public String toString() {
        return date + ": " + Formats.format(value);
    }

}
