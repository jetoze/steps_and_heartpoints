package jetoze.exercise.tracking;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

public final class ConsecutiveDailyValues implements Comparable<ConsecutiveDailyValues> {
    
    private final Stat stat;
    private final ImmutableList<DailyValue> dailyValues;

    public ConsecutiveDailyValues(Stat stat, List<DailyValue> dailyValues) {
        checkArgument(dailyValues.size() >= 2, "Must provide at least two days of values (was %s)", dailyValues.size());
        for (int n = 0; n < dailyValues.size() - 1; ++n) {
            LocalDate firstDate = dailyValues.get(n).getDate();
            LocalDate lastDate = dailyValues.get(n + 1).getDate();
            long daysBetween = ChronoUnit.DAYS.between(firstDate, lastDate);
            checkArgument(daysBetween == 1, "Not consecutive days: %s and %s", firstDate, lastDate);
        }
        this.stat = requireNonNull(stat);
        this.dailyValues = ImmutableList.copyOf(dailyValues);
    }
    
    public LocalDate firstDate() {
        return dailyValues.get(0).getDate();
    }
    
    public LocalDate lastDate() {
        return dailyValues.get(dailyValues.size() - 1).getDate();
    }
    
    public int numberOfDays() {
        return dailyValues.size();
    }
    
    public Stream<Number> values() {
        return dailyValues.stream()
                .map(DailyValue::getValue);
    }
    
    public Number sum() {
        if (stat == Stat.OURA_DISTANCE) {
            return values()
                    .mapToDouble(Number::doubleValue)
                    .sum();
        } else {
            return values()
                    .mapToInt(Number::intValue)
                    .sum();
        }
    }
    
    public Number average() {
        // Interestingly, if we use a ternary operator here the result
        // always comes out as a double.
        if (stat == Stat.OURA_DISTANCE) {
            return sum().doubleValue() / dailyValues.size();
        } else {
            // XXX: This is ugly. Can it be done in a cleaner way?
            return (int) Math.round(1.0 * sum().intValue() / dailyValues.size());
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s to %s: %s (avg: %s)",
                firstDate(),
                lastDate(),
                Formats.format(sum()),
                Formats.format(average()));
    }

    @Override
    public int compareTo(ConsecutiveDailyValues o) {
        return Double.compare(sum().doubleValue(), o.sum().doubleValue());
    }

}
